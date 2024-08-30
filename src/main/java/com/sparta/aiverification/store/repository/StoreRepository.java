package com.sparta.aiverification.store.repository;

import com.sparta.aiverification.store.entity.Store;
import com.sparta.aiverification.user.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, UUID> {
  Page<Store> findAllByCategoryId(Long categoryId, Pageable pageable);
  Page<Store> findAllByRegionId(Long regionId, Pageable pageable);

  Page<Store> findAllByUser(User user, Pageable pageable);
  Page<Store> findAllByUserAAndCategory(User user, Long categoryId, Pageable pageable);
  Page<Store> findAllByUserAAndRegion(User user, Long regionId, Pageable pageable);

  Page<Store> findAllByStatus(Boolean status, Pageable pageable);
  Page<Store> findAllByCategoryAndStatus(Long categoryId, boolean b, Pageable pageable);
  Page<Store> findAllByRegionAndStatus(Long regionId, boolean b, Pageable pageable);

  Optional<Store> findByMenuId(UUID menuId);
}
