package com.sparta.aiverification.store.controller;

import com.sparta.aiverification.menu.dto.MenuResponseDto;
import com.sparta.aiverification.menu.service.MenuService;
import com.sparta.aiverification.store.dto.StoreRequestDto;
import com.sparta.aiverification.store.dto.StoreResponseDto;
import com.sparta.aiverification.store.service.StoreService;
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
  public StoreResponseDto createStore(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestBody StoreRequestDto storeRequestDto) {
    return storeService.createStore(userDetailsImpl.getUser(), storeRequestDto);
  }

  // 1.1 가게 목록 조회
  @GetMapping
  public Page<StoreResponseDto> getAllStores(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
      @RequestParam(value = "isAsc", defaultValue = "false") boolean isAsc,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    return storeService.getAllStores(page-1, size, sortBy, isAsc, userDetails.getUser());
  }

  // 1.2 카테고리별 가게 목록 조회
  @GetMapping("/categories/{categoryId}")
  public Page<StoreResponseDto> getAllStoresByCategoryId(
      @PathVariable("categoryId") Long categoryId,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
      @RequestParam(value = "isAsc", defaultValue = "false") boolean isAsc,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return storeService.getAllStoresByCategoryId(categoryId, page-1, size, sortBy, isAsc, userDetails.getUser());
  }

  // 1.3 지역 별 가게 목록 조회
  @GetMapping("/regions/{regionId}")
  public Page<StoreResponseDto> getAllStoresByRegionId(
      @PathVariable("regionId") Long regionId,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
      @RequestParam(value = "isAsc", defaultValue = "false") boolean isAsc,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    return storeService.getAllStoresByRegionId(regionId, page-1, size, sortBy, isAsc, userDetails.getUser());
  }

  // 2. 가게 정보 조회
  @GetMapping("/{storeId}")
  public StoreResponseDto getStoreById(@PathVariable("storeId") UUID storeId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return storeService.getStoreById(storeId, userDetails.getUser());
  }

  // 3. 가게의 상품 목록 조회
  @GetMapping("/{storeId}/menus")
  public Page<MenuResponseDto> getMenusByStore(
      @PathVariable("storeId") UUID storeId,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
      @RequestParam(value = "isAsc", defaultValue = "false") boolean isAsc,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return menuService.getMenusByStoreId(storeId, page-1, size, sortBy, isAsc, userDetails.getUser());
  }

  // 4. 가게 정보 수정
  @PutMapping("/{storeId}")
  public StoreResponseDto updateStore(@PathVariable("storeId") UUID storeId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestBody StoreRequestDto storeRequestDto) {
    return storeService.updateStore(storeId, userDetailsImpl.getUser(), storeRequestDto);
  }

  // 5. 가게 정보 삭제
  @DeleteMapping("/{storeId}")
  public ResponseEntity<String> deleteStore(@PathVariable("storeId") UUID storeId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
    storeService.deleteStoreAndMenus(storeId, userDetailsImpl.getUser());
    return ResponseEntity.ok("Store and its menus deleted successfully.");
  }
}