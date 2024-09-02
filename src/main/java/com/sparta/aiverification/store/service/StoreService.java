package com.sparta.aiverification.store.service;

import com.sparta.aiverification.category.entity.Category;
import com.sparta.aiverification.category.repository.CategoryRepository;
import com.sparta.aiverification.common.CommonErrorCode;
import com.sparta.aiverification.common.RestApiException;
import com.sparta.aiverification.menu.repository.MenuRepository;
import com.sparta.aiverification.region.entity.Region;
import com.sparta.aiverification.region.repository.RegionRepository;
import com.sparta.aiverification.store.dto.StoreErrorCode;
import com.sparta.aiverification.store.dto.StoreRequestDto;
import com.sparta.aiverification.store.dto.StoreResponseDto;
import com.sparta.aiverification.store.entity.Store;
import com.sparta.aiverification.store.repository.StoreRepository;
import com.sparta.aiverification.user.entity.User;
import com.sparta.aiverification.user.enums.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class StoreService {

  private final StoreRepository storeRepository;
  private final MenuRepository menuRepository;
  private final RegionRepository regionRepository;
  private final CategoryRepository categoryRepository;

  private Pageable getPageable(boolean isAsc, int page, int size, String sortBy) {
    Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
    Sort sort = Sort.by(direction, sortBy);
    return PageRequest.of(page, size, sort);
  }

  private static void isNotAuthorized(User user) {
    // validation
    if (user.getRole() != UserRoleEnum.OWNER) {
      throw new IllegalArgumentException("UNAUTHORIZED ACCESS");
    }
  }

  // 0. 가게 생성 - OWNER, MANAGER, MASTER
  @Transactional
  public StoreResponseDto.Default createStore(User user, StoreRequestDto storeRequestDto) {
    // validation
    isNotAuthorized(user);

    Region region = regionRepository.findById(storeRequestDto.getRegionId())
        .orElseThrow(() -> new NoSuchElementException("Region with ID " + storeRequestDto.getRegionId() + " not found"));

    Category category = categoryRepository.findById(storeRequestDto.getCategoryId())
        .orElseThrow(() -> new NoSuchElementException("Category with ID " + storeRequestDto.getCategoryId() + " not found"));

    // execute
    Store store = Store.builder()
        .userId(user.getId())
        .region(region)
        .category(category)
        .address(storeRequestDto.getAddress())
        .name(storeRequestDto.getName())
        .phone(storeRequestDto.getPhone())
        .description(storeRequestDto.getDescription())
        .status(true)
        .build();

    log.info("userId" + store.getUserId());

    storeRepository.save(store);
    return new StoreResponseDto.Default(store);
  }

  // 1.1 가게 목록 조회 - CUSTOMER, OWNER, MANAGER, MASTER
  public Page<StoreResponseDto.Get> getAllStores(int page, int size, String sortBy, boolean isAsc,
      User user) {

    // 페이징 처리
    Pageable pageable = getPageable(isAsc, page,size, sortBy);
    Page<Store> storeList;

    // OWNER : 본인 가게 목록만 조회
    if(user.getRole() == UserRoleEnum.OWNER){
      return storeRepository.searchStores(user.getId(), null, null, null, null, pageable);
    }
    // CUSTOMER :  status가 true인 값만 조회
    else if(user.getRole() == UserRoleEnum.CUSTOMER) {
      return storeRepository.searchStores(null, null, null, null, true, pageable);
    }else {
      return storeRepository.searchStores(null,null,null,null,null,pageable);
    }
  }

  // 1.2 카테고리 별 가게 목록 조회 - CUSTOMER, OWNER, MANAGER, MASTER
  public Page<StoreResponseDto.Get> getAllStoresByCategoryId(Long categoryId, int page, int size,
      String sortBy, boolean isAsc, User user) {
    // 페이징 처리
    Pageable pageable = getPageable(isAsc, page,size, sortBy);
    Page<Store> storeList;

    // OWNER : 본인 가게 목록만 조회
    if(user.getRole() == UserRoleEnum.OWNER){
//      storeList = storeRepository.findAllByUserIdAndCategory(user.getId(), categoryId,pageable);
      return storeRepository.searchStores(user.getId(), null, categoryId, null, null, pageable);
    }
    // CUSTOMER :  status가 true인 값만 조회
    else if(user.getRole() == UserRoleEnum.CUSTOMER) {
      return storeRepository.searchStores(null, null, categoryId, null, true, pageable);
    }
    else{
      return storeRepository.searchStores(null, null, categoryId, null, null, pageable);
    }
  }

  // 1.3 지역별 별 가게 목록 조회 - CUSTOMER, OWNER, MANAGER, MASTER
  public Page<StoreResponseDto.Get> getAllStoresByRegionId(Long regionId, int page, int size,
      String sortBy, boolean isAsc, User user) {
    // 페이징 처리
    Pageable pageable = getPageable(isAsc, page,size, sortBy);
    Page<Store> storeList;

    // 사용자 권한이 OWNER 면 본인 가게 목록만 조회
    if (user.getRole() == UserRoleEnum.OWNER) {
      return storeRepository.searchStores(user.getId(), regionId, null, null,null, pageable);
    }
    // CUSTOMER :  status가 true인 값만 조회
    else if(user.getRole() == UserRoleEnum.CUSTOMER) {
      return storeRepository.searchStores(null, regionId, null, null, true, pageable);
    }
    else {
      return storeRepository.searchStores(null, regionId, null, null, null, pageable);
    }
  }

  // 2. 가게 정보 조회 - CUSTOMER, OWNER, MANAGER, MASTER
  public StoreResponseDto.Default getStoreById(UUID storeId, User user) {
    Store store = storeRepository.findById(storeId)
        .orElseThrow(() -> new RuntimeException("Store not found"));

    if(user.getRole() == UserRoleEnum.CUSTOMER && store.getStatus() == false){
      throw new IllegalArgumentException("UNAUTHORIZED ACCESS");
    }

    return new StoreResponseDto.Default(store);
  }

  // 3. 가게 정보 수정
  @Transactional
  public StoreResponseDto.Default updateStore(UUID storeId, User user, StoreRequestDto storeRequestDto) {
    // validation
    if(user.getRole() == UserRoleEnum.CUSTOMER){
      throw new RestApiException(CommonErrorCode.INVALID_PARAMETER);
    }

    Region region = regionRepository.findById(storeRequestDto.getRegionId())
        .orElseThrow(() -> new NoSuchElementException("Region with ID " + storeRequestDto.getRegionId() + " not found"));

    Category category = categoryRepository.findById(storeRequestDto.getCategoryId())
        .orElseThrow(() -> new NoSuchElementException("Category with ID " + storeRequestDto.getCategoryId() + " not found"));

    // execute
    Store store = storeRepository.findById(storeId)
        .orElseThrow(() -> new NoSuchElementException("Store with ID " + storeId + " not found"));

    store.update(region, category, storeRequestDto);
    return new StoreResponseDto.Default(store);
  }

  // 4. 가게 정보 삭제 및 연관된 메뉴 삭제
  @Transactional
  public void deleteStoreAndMenus(UUID storeId, User user) {
    // validation
    isNotAuthorized(user);

    // execute
    Store store = storeRepository.findById(storeId)
        .orElseThrow(() -> new RuntimeException("Store not found"));

      store.delete(user.getId());
     store.setStatusFalse();;
  }


  public Store findById(UUID storeId){
    return storeRepository.findById(storeId).orElseThrow(()
        -> new RestApiException(StoreErrorCode.NOT_FOUND_STORE));
  }


  public Page<StoreResponseDto.Get> searchStores(Long regionId, Long categoryId, String keyword, int page, int size, String sortBy, boolean isAsc) {
    log.info(sortBy);
    Pageable pageable = getPageable(isAsc, page, size, sortBy);
    return storeRepository.searchStores(null, regionId, categoryId, keyword, true, pageable);
  }
}