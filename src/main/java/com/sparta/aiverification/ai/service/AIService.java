package com.sparta.aiverification.ai.service;

import com.sparta.aiverification.ai.dto.AIRequestDto;
import com.sparta.aiverification.ai.dto.AIResponseDto;
import com.sparta.aiverification.ai.entity.AI;
import com.sparta.aiverification.ai.repository.AIRepository;
import com.sparta.aiverification.menu.entity.Menu;
import com.sparta.aiverification.menu.repository.MenuRepository;
import com.sparta.aiverification.store.entity.Store;
import com.sparta.aiverification.store.repository.StoreRepository;
import com.sparta.aiverification.user.entity.User;
import com.sparta.aiverification.user.enums.UserRoleEnum;
import jakarta.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Slf4j
@Service
public class AIService {
  private final AIRepository aiRepository;
  private final MenuRepository menuRepository;
  private final StoreRepository storeRepository;

  private static void isNotCustomer(User user) {
    // validation
    if (user.getRole() == UserRoleEnum.CUSTOMER) {
      throw new IllegalArgumentException("UNAUTHORIZED ACCESS : CUSTOMER X");
    }
  }

  private void isCorrectOWNER(User user, UUID menuId){

    if(user.getRole() == UserRoleEnum.OWNER){
      Store store = storeRepository.findByMenuId(menuId).get();

      if(!user.getId().equals(store.getUserId())){
        throw new IllegalArgumentException("UNAUTHORIZED ACCESS : WRONG OWNER");
      }
    }
  }
  @Transactional
  public AIResponseDto createAI(User user, AIRequestDto aiRequestDto) {
    // validation
    isNotCustomer(user);
    // OWNER : 본인 가게 메뉴에 대해서만 가능
    isCorrectOWNER(user, aiRequestDto.getMenuId());

    Menu menu = menuRepository.findById(aiRequestDto.getMenuId())
        .orElseThrow(() -> new NoSuchElementException("Menu with ID " + aiRequestDto.getMenuId() + " not found"));

    AI ai = AI.builder()
        .menu(menu)
        .request(aiRequestDto.getRequest())
        .response("").build();

    aiRepository.save(ai);
    return new AIResponseDto(ai);
  }

  public Page<AIResponseDto> getAIList(UUID menuId,int page, int size, String sortBy, boolean isAsc,User user) {

    // validation
    isNotCustomer(user);

    // 페이징 처리
    Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
    Sort sort = Sort.by(direction, sortBy);
    Pageable pageable =  PageRequest.of(page, size, sort);

    Page<AI> ai;

    // OWNER : 특정 가게 목록만 조회
    if(user.getRole() == UserRoleEnum.OWNER){
      ai = aiRepository.findAllByMenuId(menuId, pageable);
    }
    else{
      ai = aiRepository.findAll(pageable);
    }
    return ai.map(AIResponseDto::new);
  }

  public AIResponseDto getAI(Long aiId, User user) {
    isNotCustomer(user);

    AI ai = aiRepository.findById(aiId).orElseThrow(NoSuchElementException::new);
    isCorrectOWNER(user, ai.getMenu().getId());

    return new AIResponseDto(ai);
  }

  @Transactional
  public AIResponseDto updateAI(Long aiId, User user, AIRequestDto aiRequestDto) {
    // validation
    isNotCustomer(user);

    AI ai = aiRepository.findById(aiId).orElseThrow(NoSuchElementException::new);
    isCorrectOWNER(user, ai.getMenu().getId());

    ai.update(aiRequestDto);
    return new AIResponseDto(ai);
  }

  @Transactional
  public void deleteAI(Long aiId, User user) {
    // validation
    isNotCustomer(user);

    AI ai = aiRepository.findById(aiId).orElseThrow(NoSuchElementException::new);
    isCorrectOWNER(user, ai.getMenu().getId());

    ai.delete(user.getId());
  }
}
