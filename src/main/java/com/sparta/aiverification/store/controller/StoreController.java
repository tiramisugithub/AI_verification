package com.sparta.aiverification.store.controller;

import com.sparta.aiverification.menu.dto.MenuResponseDto;
import com.sparta.aiverification.menu.service.MenuService;
import com.sparta.aiverification.store.dto.StoreRequestDto;
import com.sparta.aiverification.store.dto.StoreResponseDto;
import com.sparta.aiverification.store.service.StoreService;
import com.sparta.aiverification.user.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
  public StoreResponseDto.Default createStore(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestBody StoreRequestDto storeRequestDto) {
    return storeService.createStore(userDetailsImpl.getUser(), storeRequestDto);
  }

  // 1.1 가게 목록 조회
  @GetMapping
  public Page<StoreResponseDto.Get> getAllStores(
      @RequestParam(value = "page", defaultValue = "1") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
      @RequestParam(value = "isAsc", defaultValue = "false") boolean isAsc,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    return storeService.getAllStores(page-1, size, sortBy, isAsc, userDetails.getUser());
  }

  // 1.2 카테고리별 가게 목록 조회
  @GetMapping("/categories/{categoryId}")
  public Page<StoreResponseDto.Get> getAllStoresByCategoryId(
      @PathVariable("categoryId") Long categoryId,
      @RequestParam(value = "page", defaultValue = "1") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
      @RequestParam(value = "isAsc", defaultValue = "false") boolean isAsc,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return storeService.getAllStoresByCategoryId(categoryId, page-1, size, sortBy, isAsc, userDetails.getUser());
  }

  // 1.3 지역 별 가게 목록 조회
  @GetMapping("/regions/{regionId}")
  public Page<StoreResponseDto.Get> getAllStoresByRegionId(
      @PathVariable("regionId") Long regionId,
      @RequestParam(value = "page", defaultValue = "1") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
      @RequestParam(value = "isAsc", defaultValue = "false") boolean isAsc,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    return storeService.getAllStoresByRegionId(regionId, page-1, size, sortBy, isAsc, userDetails.getUser());
  }

  // 2. 가게 정보 조회
  @GetMapping("/{storeId}")
  public StoreResponseDto.Default getStoreById(@PathVariable("storeId") UUID storeId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return storeService.getStoreById(storeId, userDetails.getUser());
  }

  // 3. 가게의 상품 목록 조회
  @GetMapping("/{storeId}/menus")
  public Page<MenuResponseDto> getMenusByStore(
      @PathVariable("storeId") UUID storeId,
      @RequestParam(value = "page", defaultValue = "1") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
      @RequestParam(value = "isAsc", defaultValue = "false") boolean isAsc,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return menuService.getMenusByStoreId(storeId, page-1, size, sortBy, isAsc, userDetails.getUser());
  }

  // 4. 가게 정보 수정
  @PutMapping("/{storeId}")
  public StoreResponseDto.Default updateStore(@PathVariable("storeId") UUID storeId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestBody StoreRequestDto storeRequestDto) {
    return storeService.updateStore(storeId, userDetailsImpl.getUser(), storeRequestDto);
  }

  // 5. 가게 정보 삭제
  @DeleteMapping("/{storeId}")
  public ResponseEntity<String> deleteStore(@PathVariable("storeId") UUID storeId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
    storeService.deleteStoreAndMenus(storeId, userDetailsImpl.getUser());
    return ResponseEntity.ok("Store and its menus deleted successfully.");
  }

  // 6. 가게 검색

  @GetMapping("/search")
  public Page<StoreResponseDto.Get> searchStores(
      @RequestParam(value = "region") Long regionId,
      @RequestParam(value = "category") Long categoryId,
      @RequestParam(value = "keyword", required = false) String keyword,
      @RequestParam(value = "page", defaultValue = "1") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
      @RequestParam(value = "isAsc", defaultValue = "false") boolean isAsc) {
    return storeService.searchStores(regionId, categoryId, keyword, page - 1, size, sortBy, isAsc);
  }
}