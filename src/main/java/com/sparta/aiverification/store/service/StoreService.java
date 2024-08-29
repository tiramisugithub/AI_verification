package com.sparta.aiverification.store.service;

import com.sparta.aiverification.category.entity.Category;
import com.sparta.aiverification.category.repository.CategoryRepository;
import com.sparta.aiverification.menu.repository.MenuRepository;
import com.sparta.aiverification.region.entity.Region;
import com.sparta.aiverification.region.repository.RegionRepository;
import com.sparta.aiverification.store.dto.StoreRequestDto;
import com.sparta.aiverification.store.dto.StoreResponseDto;
import com.sparta.aiverification.store.entity.Store;
import com.sparta.aiverification.store.repository.StoreRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Service
public class StoreService {

  private final StoreRepository storeRepository;
  private final MenuRepository menuRepository;
  private final RegionRepository regionRepository;
  private final CategoryRepository categoryRepository;

  @Transactional
  public StoreResponseDto createStore(StoreRequestDto storeRequestDto) {

    Region region = regionRepository.findById(storeRequestDto.getRegionId())
        .orElseThrow(() -> new NoSuchElementException("Region with ID " + storeRequestDto.getRegionId() + " not found"));

    Category category = categoryRepository.findById(storeRequestDto.getCategoryId())
        .orElseThrow(() -> new NoSuchElementException("Category with ID " + storeRequestDto.getCategoryId() + " not found"));

    Store store = Store.builder()
        .userId(Long.valueOf(1))
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
    return new StoreResponseDto(store);
  }

  // 1.1 가게 목록 조회
  public List<StoreResponseDto> getAllStores() {
    List<Store> stores = storeRepository.findAll();

    List<StoreResponseDto> storeResponseDtoList = new ArrayList<>();
    for (Store store : stores) {
      storeResponseDtoList.add(new StoreResponseDto(store));
    }
    return storeResponseDtoList;
  }

  // 1.2 카테고리 별 가게 목록 조회
  public List<StoreResponseDto> getAllStoresByCategoryId(Long categoryId) {
    List<Store> stores = storeRepository.findAllByCategoryId(categoryId);

    List<StoreResponseDto> storeResponseDtoList = new ArrayList<>();
    for (Store store : stores) {
      storeResponseDtoList.add(new StoreResponseDto(store));
    }
    return storeResponseDtoList;
  }

  // 1.3 지역별 별 가게 목록 조회
  public List<StoreResponseDto> getAllStoresByRegionId(Long regionId) {
    List<Store> stores = storeRepository.findAllByRegionId(regionId);

    List<StoreResponseDto> storeResponseDtoList = new ArrayList<>();
    for (Store store : stores) {
      storeResponseDtoList.add(new StoreResponseDto(store));
    }
    return storeResponseDtoList;
  }

  // 2. 가게 정보 조회
  public StoreResponseDto getStoreById(UUID storeId) {
    Store store = storeRepository.findById(storeId)
        .orElseThrow(() -> new RuntimeException("Store not found"));
    return new StoreResponseDto(store);
  }

  // 3. 가게 정보 수정
  @Transactional
  public StoreResponseDto updateStore(UUID storeId, StoreRequestDto storeRequestDto) {
    Region region = regionRepository.findById(storeRequestDto.getRegionId())
        .orElseThrow(() -> new NoSuchElementException("Region with ID " + storeRequestDto.getRegionId() + " not found"));

    Category category = categoryRepository.findById(storeRequestDto.getCategoryId())
        .orElseThrow(() -> new NoSuchElementException("Category with ID " + storeRequestDto.getCategoryId() + " not found"));

    Store store = storeRepository.findById(storeId)
        .orElseThrow(() -> new NoSuchElementException("Store with ID " + storeId + " not found"));

    store.update(region, category, storeRequestDto);
    return new StoreResponseDto(store);
  }

  // 4. 가게 정보 삭제 및 연관된 메뉴 삭제
  @Transactional
  public void deleteStoreAndMenus(UUID storeId) {
    Store store = storeRepository.findById(storeId)
        .orElseThrow(() -> new RuntimeException("Store not found"));
     // store.delete("current_user");
     store.setStatusFalse();;

  }
}