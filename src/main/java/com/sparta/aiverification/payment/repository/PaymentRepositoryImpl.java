package com.sparta.aiverification.payment.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.aiverification.order.dto.OrderResponseDto;
import com.sparta.aiverification.order.entity.Orders;
import com.sparta.aiverification.payment.dto.PaymentResponseDto;
import com.sparta.aiverification.payment.entity.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.UUID;

import static com.sparta.aiverification.order.entity.QOrders.orders;
import static com.sparta.aiverification.payment.entity.QPayment.payment;

@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<PaymentResponseDto.Get> findByCondition(Long userId, Pageable pageable) {
        List<Payment> result = queryFactory
                .selectFrom(payment)
                .where(userIdEq(userId), payment.isDeleted.eq(false))
                .orderBy(orderSort(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = queryFactory.select(payment.count())
                .from(payment)
                .where(userIdEq(userId), payment.isDeleted.eq(false));

        return PageableExecutionUtils.getPage(
                result.stream().map(PaymentResponseDto.Get::of).toList(),
                pageable,
                count::fetchOne
        );
    }

    private BooleanExpression userIdEq(Long userId) {
        return userId != null ? payment.user.id.eq(userId) : null;
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
