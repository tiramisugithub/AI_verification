package com.sparta.aiverification.store.repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.aiverification.store.dto.QStoreResponseDto_Get;
import com.sparta.aiverification.store.dto.StoreResponseDto;
import com.sparta.aiverification.store.entity.QStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.sparta.aiverification.review.entity.QReview.review;
import static com.sparta.aiverification.store.entity.QStore.store;

@Repository
public class StoreRepositoryImpl implements StoreRepositoryCustom {

  @Autowired
  private JPAQueryFactory queryFactory;

  @Override
  public Page<StoreResponseDto.Get> searchStores(Long userId
          , Long regionId, Long categoryId, String keyword
          , Boolean status, Pageable pageable) {
    QStore store = QStore.store;

    List<StoreResponseDto.Get> result = queryFactory
        .select(new QStoreResponseDto_Get(
                store.id, store.category.id, store.region.id, store.name,
                store.phone, store.address, store.description, store.status, ExpressionUtils.as(
                JPAExpressions.select(review.score.avg())
                        .from(review)
                        .where(review.store.id.eq(store.id), review.isDeleted.eq(false)), "avgScore")))
            .from(store)
            .where(regionIdEq(regionId), userIdEq(userId), categoryIdEq(categoryId), statusEq(status),
                    StringUtils.hasText(keyword) ? store.name.containsIgnoreCase(keyword)
                            .or(store.description.containsIgnoreCase(keyword)) : null)
            .orderBy(orderSort(pageable))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

    JPAQuery<Long> total = queryFactory
        .select(store.count())
            .from(store)
            .where(regionIdEq(regionId), userIdEq(userId), categoryIdEq(categoryId), statusEq(status),
                    StringUtils.hasText(keyword) ? store.name.contains(keyword)
                            .or(store.description.contains(keyword)) : null);
    return PageableExecutionUtils.getPage(result, pageable, total::fetchOne);
  }

  private BooleanExpression regionIdEq(Long regionId) {
    return regionId != null ? store.region.id.eq(regionId) : null;
  }

  private BooleanExpression categoryIdEq(Long categoryId) {
    return categoryId != null ? store.category.id.eq(categoryId) : null;
  }

  private BooleanExpression userIdEq(Long userId) {
    return userId != null ? store.userId.eq(userId) : null;
  }

  private BooleanExpression statusEq(Boolean status){
    return store.status.eq(true);
  }


  private OrderSpecifier<?> orderSort(Pageable pageable) {
    if(!pageable.getSort().isEmpty()){
      for(Sort.Order order : pageable.getSort()){
        Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
        switch (order.getProperty()){
          case "createdAt":
            return new OrderSpecifier<>(direction, store.createdAt);
          case "updatedAt":
            return new OrderSpecifier<>(direction, store.updatedAt);
        }
      }
    }
    return null;
  }

}