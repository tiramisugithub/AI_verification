package com.sparta.aiverification.store.controller;

import com.sparta.aiverification.menu.dto.MenuResponseDto;
import com.sparta.aiverification.menu.service.MenuService;
import com.sparta.aiverification.store.dto.StoreRequestDto;
import com.sparta.aiverification.store.dto.StoreResponseDto;
import com.sparta.aiverification.store.service.StoreService;
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
@RequestMapping("/stores")
public class StoreController {

  private final StoreService storeService;
  private final MenuService menuService;

  @Autowired
  public StoreController(StoreService storeService, MenuService menuService) {
    this.storeService = storeService;
    this.menuService = menuService;
  }
  // 0. 가게 생성
  @PostMapping
  public StoreResponseDto createStore(@RequestBody StoreRequestDto storeRequestDto) {
    return storeService.createStore(storeRequestDto);
  }

  // 1.1 가게 목록 조회
  @GetMapping
  public List<StoreResponseDto> getAllStores() {
    return storeService.getAllStores();
  }

  // 1.2 카테고리별 가게 목록 조회
  @GetMapping("/categories/{categoryId}}")
  public List<StoreResponseDto> getAllStoresByCategoryId(@PathVariable("categoryId") Long categoryId) {
    return storeService.getAllStoresByCategoryId(categoryId);
  }

  // 1.3 지역 별 가게 목록 조회
  @GetMapping("/regions/{regionId}")
  public List<StoreResponseDto> getAllStoresByRegionId(@PathVariable("regionId") Long regionId) {
    return storeService.getAllStoresByRegionId(regionId);
  }

  // 2. 가게 정보 조회
  @GetMapping("/{storeId}")
  public StoreResponseDto getStoreById(@PathVariable UUID storeId) {
    return storeService.getStoreById(storeId);
  }

  // 3. 가게의 상품 목록 조회
  @GetMapping("/{storeId}/menus")
  public List<MenuResponseDto> getMenusByStore(@PathVariable UUID storeId) {
    return menuService.getMenusByStoreId(storeId);
  }

  // 4. 가게 정보 수정
  @PutMapping("/{storeId}")
  public StoreResponseDto updateStore(@PathVariable UUID storeId, @RequestBody StoreRequestDto storeRequestDto) {
    return storeService.updateStore(storeId, storeRequestDto);
  }

  // 5. 가게 정보 삭제
  @DeleteMapping("/{storeId}")
  public ResponseEntity<String> deleteStore(@PathVariable UUID storeId) {
    storeService.deleteStoreAndMenus(storeId);
    return ResponseEntity.ok("Store and its menus deleted successfully.");
  }
}