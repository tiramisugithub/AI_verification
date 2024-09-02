package com.sparta.aiverification.store.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.aiverification.store.dto.StoreResponseDto;
import com.sparta.aiverification.store.entity.QStore;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class StoreRepositoryImpl implements StoreRepositoryCustom {

  @Autowired
  private JPAQueryFactory queryFactory;

  @Override
  public Page<StoreResponseDto> searchStores(Long regionId, Long categoryId, String keyword, Pageable pageable) {
    QStore store = QStore.store;

    List<StoreResponseDto> results = queryFactory
        .selectFrom(store)
        .where(store.region.id.eq(regionId)
            .and(store.category.id.eq(categoryId))
            .and(keyword != null ? store.name.containsIgnoreCase(keyword)
                .or(store.description.containsIgnoreCase(keyword)) : null))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch()
        .stream()
        .map(StoreResponseDto::new)
        .collect(Collectors.toList());

    long total = queryFactory
        .selectFrom(store)
        .where(store.region.id.eq(regionId)
            .and(store.category.id.eq(categoryId))
            .and(keyword != null ? store.name.containsIgnoreCase(keyword)
                .or(store.description.containsIgnoreCase(keyword)) : null))
        .fetchCount();

    return new PageImpl<>(results, pageable, total);
  }
}