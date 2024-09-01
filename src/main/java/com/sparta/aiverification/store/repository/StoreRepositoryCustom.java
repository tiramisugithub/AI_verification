package com.sparta.aiverification.store.repository;

import com.sparta.aiverification.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoreRepositoryCustom {
  Page<Store> searchStores(Long regionId, Long categoryId, String keyword, Pageable pageable);
}