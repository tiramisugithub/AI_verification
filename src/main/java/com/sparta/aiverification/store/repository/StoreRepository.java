package com.sparta.aiverification.store.repository;

import com.sparta.aiverification.store.entity.Store;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoreRepository extends JpaRepository<Store, UUID>, StoreRepositoryCustom {
  Page<Store> findAllByCategoryId(Long categoryId, Pageable pageable);
  Page<Store> findAllByRegionId(Long regionId, Pageable pageable);

  Page<Store> findAllByUserId(Long userId, Pageable pageable);
  Page<Store> findAllByUserIdAndCategory(Long userId, Long categoryId, Pageable pageable);
  Page<Store> findAllByUserIdAndRegion(Long userId, Long regionId, Pageable pageable);

  Page<Store> findAllByStatus(Boolean status, Pageable pageable);
  Page<Store> findAllByCategoryAndStatus(Long categoryId, boolean b, Pageable pageable);
  Page<Store> findAllByRegionAndStatus(Long regionId, boolean b, Pageable pageable);

  @Query("SELECT s FROM Store s JOIN s.menus m WHERE m.id = :menuId")
  Optional<Store> findByMenuId(@Param("menuId") UUID menuId);
}
