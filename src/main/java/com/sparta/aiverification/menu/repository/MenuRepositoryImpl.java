package com.sparta.aiverification.menu.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.aiverification.menu.dto.MenuResponseDto;
import com.sparta.aiverification.menu.entity.QMenu;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class MenuRepositoryImpl implements MenuRepositoryCustom {
  @Autowired
  private JPAQueryFactory queryFactory;

  @Override
  public Page<MenuResponseDto> searchMenus(String keyword, Pageable pageable) {

    QMenu menu = QMenu.menu;

    List<MenuResponseDto> results = queryFactory
        .selectFrom(menu)
        .where(keyword != null ? menu.name.containsIgnoreCase(keyword)
            .or(menu.description.containsIgnoreCase(keyword)) : null)
        .limit(pageable.getPageSize())
        .fetch()
        .stream()
        .map(MenuResponseDto::new)
        .collect(Collectors.toList());

    long total = queryFactory
        .selectFrom(menu)
        .where(keyword != null ? menu.name.containsIgnoreCase(keyword)
                .or(menu.description.containsIgnoreCase(keyword)) : null)
        .fetchCount();

    return new PageImpl<>(results, pageable, total);
  }
}
