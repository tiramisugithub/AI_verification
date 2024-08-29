package com.sparta.aiverification.menu.service;

import com.sparta.aiverification.menu.dto.MenuRequestDto;
import com.sparta.aiverification.menu.dto.MenuResponseDto;
import com.sparta.aiverification.menu.entity.Menu;
import com.sparta.aiverification.menu.repository.MenuRepository;
import com.sparta.aiverification.store.entity.Store;
import com.sparta.aiverification.store.repository.StoreRepository;
import com.sparta.aiverification.user.entity.User;
import com.sparta.aiverification.user.enums.UserRoleEnum;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Service
public class MenuService {

  private final MenuRepository menuRepository;
  private final StoreRepository storeRepository;

  private static void userValidate(User user) {
    // validation
    if (user.getRole() == UserRoleEnum.CUSTOMER) {
      throw new IllegalArgumentException("UNAUTHORIZED ACCESS");
    }
  }

  // 1. 메뉴 생성
  @Transactional
  public MenuResponseDto createMenu(MenuRequestDto menuRequestDto, User user) {
    // validation
    userValidate(user);

    Store store = storeRepository.findById(menuRequestDto.getStoreId())
        .orElseThrow(() -> new RuntimeException("Store not found"));

    // 헤딩 가게 주인인지 확인
    if(user.getRole() == UserRoleEnum.OWNER && user.getId() != store.getUserId() ){
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

  // 2. 메뉴 목록 조회
  public List<MenuResponseDto> getAllMenus() {
    List<Menu> menus = menuRepository.findAll();

    List<MenuResponseDto> menuResponseDtoList = new ArrayList<>();
    for (Menu menu : menus) {
      menuResponseDtoList.add(new MenuResponseDto(menu));
    }
    return menuResponseDtoList;
  }

  public List<MenuResponseDto> getMenusByStoreId(UUID storeId) {
    List<Menu> menus = menuRepository.findByStoreId(storeId);

    List<MenuResponseDto> menuResponseDtoList = new ArrayList<>();
    for (Menu menu : menus) {
      menuResponseDtoList.add(new MenuResponseDto(menu));
    }

    return menuResponseDtoList;
  }

  // 3. 메뉴 정보 조회
  public MenuResponseDto getMenuById(UUID menuId) {
    Menu menu = menuRepository.findById(menuId)
        .orElseThrow(() -> new RuntimeException("Menu not found"));
    return new MenuResponseDto(menu);
  }

  // 4. 메뉴 수정
  @Transactional
  public MenuResponseDto updateMenu(UUID menuId, User user, MenuRequestDto menuRequestDto) {
    // validation
    userValidate(user);

    Store store = storeRepository.findById(menuRequestDto.getStoreId())
        .orElseThrow(() -> new RuntimeException("Store not found"));

    // 헤딩 가게 주인인지 확인
    if(user.getRole() == UserRoleEnum.OWNER && user.getId() != store.getUserId() ){
      throw new IllegalArgumentException("UNAUTHORIZED ACCESS");
    }

    Menu menu = menuRepository.findById(menuId)
        .orElseThrow(() -> new RuntimeException("Menu not found"));

    menu.update(menuRequestDto);
    return new MenuResponseDto(menu);
  }

  // 5. 메뉴 삭제
  @Transactional
  public void deleteMenu(UUID menuId, User user) {
    // validation
    userValidate(user);

    Menu menu = menuRepository.findById(menuId)
        .orElseThrow(() -> new RuntimeException("Menu not found"));

    menu.delete(user.getId());
    menu.setStatusFalse();
  }
}