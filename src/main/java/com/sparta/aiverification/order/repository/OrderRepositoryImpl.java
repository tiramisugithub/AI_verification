package com.sparta.aiverification.order.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.aiverification.order.dto.OrderResponseDto;
import com.sparta.aiverification.order.entity.Orders;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.sparta.aiverification.order.entity.QOrders.orders;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    // 확장성을 고려하면 추후 수정할 예정
    @Override
    public Page<OrderResponseDto.Get> findAllByCondition(Long userId, UUID storeId, Pageable pageable) {
        List<Orders> result = queryFactory
                .selectFrom(orders)
                .where(userIdEq(userId), storeIdEq(storeId), orders.isDeleted.eq(false))
                .orderBy(orderSort(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = queryFactory.select(orders.count())
                .from(orders)
                .where(userIdEq(userId), storeIdEq(storeId), orders.isDeleted.eq(false));

        return PageableExecutionUtils.getPage(result.stream()
                .map(OrderResponseDto.Get::of)
                .toList(),
                pageable,
                count::fetchOne);
    }
    private BooleanExpression storeIdEq(UUID storeId) {
        return storeId != null ? orders.store.id.eq(storeId) : null;
    }

    private BooleanExpression userIdEq(Long userId) {
        return userId != null ? orders.user.id.eq(userId) : null;
    }

    private OrderSpecifier<?> orderSort(Pageable pageable) {
        if(!pageable.getSort().isEmpty()){
            for(Sort.Order order : pageable.getSort()){
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                switch (order.getProperty()){
                    case "createdAt":
                        return new OrderSpecifier<>(direction, orders.createdAt);
                    case "updatedAt":
                        return new OrderSpecifier<>(direction, orders.updatedAt);
                }
            }
        }
        return null;
    }
}
