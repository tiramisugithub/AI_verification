package com.sparta.aiverification.menu.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.aiverification.ai.dto.AIRequestDto;
import com.sparta.aiverification.ai.entity.AI;
import com.sparta.aiverification.ai.repository.AIRepository;
import com.sparta.aiverification.ai.service.AIService;
import com.sparta.aiverification.common.RestApiException;
import com.sparta.aiverification.menu.dto.MenuErrorCode;
import com.sparta.aiverification.menu.dto.MenuRequestDto;
import com.sparta.aiverification.menu.dto.MenuResponseDto;
import com.sparta.aiverification.menu.entity.Menu;
import com.sparta.aiverification.menu.repository.MenuRepository;
import com.sparta.aiverification.store.entity.Store;
import com.sparta.aiverification.store.repository.StoreRepository;
import com.sparta.aiverification.user.entity.User;
import com.sparta.aiverification.user.enums.UserRoleEnum;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@AllArgsConstructor
@Service
public class MenuService {

  private final MenuRepository menuRepository;
  private final StoreRepository storeRepository;
  private final AIRepository aiRepository;

  @Autowired
  private final AIService aiService;

//  @Autowired
  private RestTemplate restTemplate;

  private static void isNotCustomer(User user) {
    // validation
    if (user.getRole() == UserRoleEnum.CUSTOMER) {
      log.info("접근자 권한 : " + user.getRole());
      throw new IllegalArgumentException("UNAUTHORIZED ACCESS");
    }
  }

  // 1. 메뉴 생성 - OWNER, MANAGER, MASTER
  @Transactional
  public MenuResponseDto createMenu(MenuRequestDto menuRequestDto, User user) {
    // validation
    isNotCustomer(user);

    Store store = storeRepository.findById(menuRequestDto.getStoreId())
        .orElseThrow(() -> new RuntimeException("Store not found"));

    log.info("접근자 권한 : " + user.getRole());
    log.info("접근 유저 id : " + user.getId() + " 가게 주인 id " + store.getUserId());

    // 헤딩 가게 주인인지 확인
    if(user.getRole() != UserRoleEnum.OWNER || !user.getId().equals(store.getUserId())){
      throw new IllegalArgumentException("UNAUTHORIZED ACCESS");
    }

    Menu menu = Menu.builder()
        .name(menuRequestDto.getName())
        .price(menuRequestDto.getPrice())
        .description(menuRequestDto.getDescription())
        .status(true)
        .store(store)
        .build();

    log.info("userId" + store.getUserId());
    log.info("storeId" + store.getId());

    menuRepository.save(menu);
    return new MenuResponseDto(menu);
  }

  // 2. 메뉴 목록 조회 -  MANAGER, MASTER
  public Page<MenuResponseDto> getAllMenus(int page, int size, String sortBy, boolean isAsc, User user) {

    Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
    Sort sort = Sort.by(direction, sortBy);

    // 페이징 처리
    Pageable pageable = PageRequest.of(page, size, sort);
    Page<Menu> menuList = menuRepository.findAll(pageable);

    return menuList.map(MenuResponseDto::new);
  }

  // 2.1 가게 별 메뉴 정보 조회 - CUSTOMER, OWNER, MANAGER, MASTER
  public Page<MenuResponseDto> getMenusByStoreId(UUID storeId, int page, int size, String sortBy,
      boolean isAsc, User user) {
    // 페이징 처리
    Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
    Sort sort = Sort.by(direction, sortBy);
    Pageable pageable =  PageRequest.of(page, size, sort);

    // 모두 조회 가능
    Page<Menu> menuList;
    if(user.getRole() == UserRoleEnum.CUSTOMER){
      log.info("접근자 권한 : " + user.getRole());

      menuList = menuRepository.findMenusByStoreAndStatus(storeId, true, pageable);
    }
    else{
      menuList = menuRepository.findMenusByStore(storeId, pageable);
    }

    return menuList.map(MenuResponseDto::new);
  }

  // 3. 메뉴 정보 조회 - CUSTOMER, OWNER, MANAGER, MASTER
  public MenuResponseDto getMenuById(UUID menuId, User user) {
    Menu menu = menuRepository.findById(menuId)
        .orElseThrow(() -> new RuntimeException("Menu not found"));

    if(user.getRole() == UserRoleEnum.CUSTOMER && menu.getStatus() == false){
      log.info("접근자 권한 : " + user.getRole());

      throw new IllegalArgumentException("UNAUTHORIZED ACCESS");
    }

    return new MenuResponseDto(menu);
  }

  // 4. 메뉴 수정 - OWNER, MANAGER, MASTER
  @Transactional
  public MenuResponseDto updateMenu(UUID menuId, User user, MenuRequestDto menuRequestDto) {
    // validation
    isNotCustomer(user);

    Store store = storeRepository.findById(menuRequestDto.getStoreId())
        .orElseThrow(() -> new RuntimeException("Update menu : Store not found"));


    log.info("접근자 권한 : " + user.getRole());
    log.info("접근 유저 id : " + user.getId() + " 가게 주인 id " + store.getUserId());

    // 헤딩 가게 주인인지 확인
    if(user.getRole() != UserRoleEnum.OWNER || !user.getId().equals(store.getUserId())){
      throw new IllegalArgumentException("UNAUTHORIZED ACCESS");
    }

    Menu menu = menuRepository.findById(menuId)
        .orElseThrow(() -> new RuntimeException("Menu not found"));

    menu.update(menuRequestDto);
    return new MenuResponseDto(menu);
  }

  // 5. 메뉴 삭제 - OWNER, MANAGER, MASTER
  @Transactional
  public void deleteMenu(UUID menuId, User user) {
    // validation
    isNotCustomer(user);


    Menu menu = menuRepository.findById(menuId)
        .orElseThrow(() -> new RuntimeException("Menu not found"));

    menu.delete(user.getId());
    menu.setStatusFalse();
  }


  public void generatMenuDescription(UUID menuId, User user) {
    String aiApiKey = "";

    Optional<Menu> menu = menuRepository.findById(menuId);

    log.info(menu.toString());

    // 1. AI 요청 로그 생성
    if(!menu.isPresent()){
      throw new IllegalArgumentException("NOT FOUND");
    }

    // 2. API 호출을 위한 URL 설정 (실제 사용 시 aiApiKey 값이 필요)
    String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=" + aiApiKey;

    // 3. HTTP 요청 헤더 설정
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON); // JSON 형식으로 요청

    // 4. 요청 본문 작성
    String prompt = menu.get().getName() +"에 대한 설명을 10자 내외로 작성해줘.";
    Map<String, Object> requestBody = new HashMap<>();
    requestBody.put("contents", Collections.singletonList(
        Collections.singletonMap("parts", Collections.singletonList(
            Collections.singletonMap("text", prompt)
        ))
    ));

    // 5. HTTP 요청 엔터티 생성 (요청 본문과 헤더 포함)
    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

    try {
      // 6. API 호출 (POST 요청)
      ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);

      String new_description = "";
      try {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode textNode = objectMapper.readTree(response.getBody())
            .path("candidates")
            .get(0)
            .path("content")
            .path("parts")
            .get(0)
            .path("text");

        new_description = textNode.asText();
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }


      // 7. 응답 로그 업데이트 &  DB에 저장
      Optional<AI> ai = aiRepository.findByMenuId(menuId);
      AIRequestDto aiRequestDto = new AIRequestDto(menuId,
          prompt,
          new_description);

      if(ai.isPresent()){
        aiService.updateAI(ai.get().getId(), user, aiRequestDto);
      }
      else{
        aiService.createAI(user,aiRequestDto);
      }

      updateMenu(menuId, user, new MenuRequestDto(
          menu.get().getStore().getId(),
          menu.get().getName(),
          menu.get().getPrice(),
          new_description,
          menu.get().getStatus()
      ));
    } catch (HttpClientErrorException | HttpServerErrorException ex) {

      // API 호출 실패 시 예외 처리
      throw new RuntimeException("AI API 호출에 실패했습니다: " + ex.getMessage(), ex);
    }

  }

  public Menu findById(UUID menuId) {
    return menuRepository.findById(menuId).orElseThrow(()
        -> new RestApiException(MenuErrorCode.NOT_FOUND_MENU));
  }

}