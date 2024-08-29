package com.sparta.aiverification.store.repository;

import com.sparta.aiverification.store.entity.Store;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, UUID> {
  List<Store> findAllByCategoryId(Long categoryId);
  List<Store> findAllByRegionId(Long regionId);
}
