package com.sparta.aiverification.review.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.aiverification.review.dto.ReviewResponseDto;
import com.sparta.aiverification.review.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.UUID;

import static com.sparta.aiverification.order.entity.QOrders.orders;
import static com.sparta.aiverification.review.entity.QReview.review;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public Page<ReviewResponseDto.Get> findAllByCondition(Long userId, UUID storeId, Pageable pageable) {
        List<Review> result = queryFactory
                .selectFrom(review)
                .where(userEq(userId),
                        storeEq(storeId),
                        review.isDeleted.eq(false),
                        review.isReported.eq(false))
                .orderBy(orderSort(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = queryFactory.select(review.count())
                .from(review)
                .where(userEq(userId), storeEq(storeId), orders.isDeleted.eq(false));

        return PageableExecutionUtils.getPage(result.stream()
                        .map(ReviewResponseDto.Get::of)
                        .toList(),
                pageable,
                count::fetchOne);
    }

    private OrderSpecifier<?> orderSort(Pageable pageable) {
        if(!pageable.getSort().isEmpty()){
            for(Sort.Order order : pageable.getSort()){
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                switch (order.getProperty()){
                    case "createdAt":
                        return new OrderSpecifier<>(direction, review.createdAt);
                    case "updatedAt":
                        return new OrderSpecifier<>(direction, review.updatedAt);
                }
            }
        }
        return null;
    }

    @Override
    public Review findByReviewId(UUID reviewId) {
        return queryFactory
                .selectFrom(review)
                .where(review.id.eq(reviewId),
                        review.isDeleted.eq(false),
                        review.isReported.eq(false))
                .fetchOne();
    }

    private BooleanExpression storeEq(UUID storeId) {
        return storeId != null ? review.store.id.eq(storeId) : null;
    }

    private BooleanExpression userEq(Long userId) {
        return userId != null ? review.user.id.eq(userId) : null;
    }



}
