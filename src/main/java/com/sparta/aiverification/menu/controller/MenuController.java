package com.sparta.aiverification.menu.controller;

import com.sparta.aiverification.menu.dto.MenuRequestDto;
import com.sparta.aiverification.menu.dto.MenuResponseDto;
import com.sparta.aiverification.menu.service.MenuService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
  public MenuResponseDto createMenu(@RequestBody MenuRequestDto menuRequestDto) {
    return menuService.createMenu(menuRequestDto);
  }

  // 2. 메뉴 목록 조회
  @GetMapping
  public List<MenuResponseDto> getAllMenus() {
    return menuService.getAllMenus();
  }

  // 3. 메뉴 정보 조회
  @GetMapping("/{menuId}")
  public MenuResponseDto getMenuById(@PathVariable UUID menuId) {
    return menuService.getMenuById(menuId);
  }

  // 4. 메뉴 수정
  @PutMapping("/{menuId}")
  public MenuResponseDto updateMenu(@PathVariable UUID menuId, @RequestBody MenuRequestDto menuRequestDto) {
    return menuService.updateMenu(menuId, menuRequestDto);
  }

  // 5. 메뉴 삭제
  @DeleteMapping("/{menuId}")
  public ResponseEntity<String> deleteMenu(@PathVariable UUID menuId) {
    menuService.deleteMenu(menuId);
    return ResponseEntity.ok("Menu deleted successfully.");
  }
}