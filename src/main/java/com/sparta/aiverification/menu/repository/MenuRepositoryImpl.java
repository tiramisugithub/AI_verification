package com.sparta.aiverification.menu.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.aiverification.menu.entity.Menu;
import com.sparta.aiverification.menu.entity.QMenu;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class MenuRepositoryImpl implements MenuRepositoryCustom {
  @Autowired
  private JPAQueryFactory queryFactory;

  @Override
  public Page<Menu> searchMenus(String keyword, Pageable pageable) {

    QMenu menu = QMenu.menu;

    List<Menu> results = queryFactory
        .selectFrom(menu)
        .where(keyword != null ? menu.name.containsIgnoreCase(keyword)
                .or(menu.description.containsIgnoreCase(keyword)) : null)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    long total = queryFactory
        .selectFrom(menu)
        .where(keyword != null ? menu.name.containsIgnoreCase(keyword)
                .or(menu.description.containsIgnoreCase(keyword)) : null)
        .fetchCount();

    return new PageImpl<>(results, pageable, total);
  }
}
