package com.sparta.aiverification.menu.repository;

import com.sparta.aiverification.menu.dto.MenuResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface MenuRepositoryCustom {
  Page<MenuResponseDto> searchMenus(String keyword, Pageable pageable);
}