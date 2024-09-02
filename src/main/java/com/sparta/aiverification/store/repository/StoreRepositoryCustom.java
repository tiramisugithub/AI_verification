package com.sparta.aiverification.store.repository;

import com.sparta.aiverification.store.dto.StoreResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoreRepositoryCustom {
  Page<StoreResponseDto.Get> searchStores(Long userId, Long regionId, Long categoryId, String keyword, Boolean status, Pageable pageable);
}