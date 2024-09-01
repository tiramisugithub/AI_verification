package com.sparta.aiverification.menu.repository;

import com.sparta.aiverification.menu.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface MenuRepositoryCustom {
  Page<Menu> searchMenus(String keyword, Pageable pageable);
}