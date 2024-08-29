package com.sparta.aiverification.menu.repository;

import com.sparta.aiverification.menu.entity.Menu;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, UUID> {
//  List<Menu> findByName(String menuName);
  List<Menu> findByStoreId(UUID storeId);

}
