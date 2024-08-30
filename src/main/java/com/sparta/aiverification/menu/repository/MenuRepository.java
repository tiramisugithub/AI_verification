package com.sparta.aiverification.menu.repository;

import com.sparta.aiverification.menu.entity.Menu;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, UUID> {
//  List<Menu> findByName(String menuName);
  Page<Menu> findByStoreId(UUID storeId, Pageable pageable);

}
