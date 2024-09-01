package com.sparta.aiverification.menu.controller;

import com.sparta.aiverification.menu.dto.MenuRequestDto;
import com.sparta.aiverification.menu.dto.MenuResponseDto;
import com.sparta.aiverification.menu.service.MenuService;
import com.sparta.aiverification.store.entity.Store;
import com.sparta.aiverification.user.security.UserDetailsImpl;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menus")
public class MenuController{
  private final MenuService menuService;
  
  @Autowired
  public MenuController(MenuService menuService) {
    this.menuService = menuService;
  }

  // 1. 메뉴 생성
  @PostMapping
  public MenuResponseDto createMenu(@RequestBody MenuRequestDto menuRequestDto,  @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
    return menuService.createMenu(menuRequestDto, userDetailsImpl.getUser());
  }

  // 2. 메뉴 목록 조회
  @GetMapping
  public Page<MenuResponseDto> getAllMenus(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
      @RequestParam(value = "isAsc", defaultValue = "false") boolean isAsc,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    return menuService.getAllMenus(page-1, size, sortBy, isAsc, userDetails.getUser());
  }

  // 3. 메뉴 정보 조회
  @GetMapping("/{menuId}")
  public MenuResponseDto getMenuById(@PathVariable("menuId") UUID menuId,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return menuService.getMenuById(menuId, userDetails.getUser());
  }

  // 4. 메뉴 수정
  @PutMapping("/{menuId}")
  public MenuResponseDto updateMenu(@PathVariable("menuId") UUID menuId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestBody MenuRequestDto menuRequestDto) {
    return menuService.updateMenu(menuId, userDetailsImpl.getUser(), menuRequestDto);
  }

  // 5. 메뉴 삭제
  @DeleteMapping("/{menuId}")
  public ResponseEntity<String> deleteMenu(@PathVariable("menuId") UUID menuId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
    menuService.deleteMenu(menuId, userDetailsImpl.getUser());
    return ResponseEntity.ok("Menu deleted successfully.");
  }

  // 6. 메뉴-설명 인공지능 요청
  @PostMapping("/generate-description/{menuId}")
  public ResponseEntity<String> generatMenuDescription(@PathVariable("menuId") UUID menuId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
    // AI 설명 생성 및 DB 저장
//    menuService.generatMenuDescription(menuId, userDetailsImpl.getUser());

    // 응답 반환
    return ResponseEntity.ok("Description generated and saved successfully.");
  }

  @GetMapping("/")
  public Page<Store> searchMenus(
      @RequestParam(value = "q", required = false) String keyword,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
      @RequestParam(value = "isAsc", defaultValue = "false") boolean isAsc) {
    return menuService.searchMenus(keyword, page, size, sortBy, isAsc);
  }

}