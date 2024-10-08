package com.sparta.aiverification.menu.repository;

import com.sparta.aiverification.menu.entity.Menu;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, UUID>, MenuRepositoryCustom {
  //  List<Menu> findByName(String menuName);
  Page<Menu> findMenusByStoreAndStatus(UUID storeId, Boolean status, Pageable pageable);
  Page<Menu> findMenusByStore(UUID storeId, Pageable pageable);
}
