package com.sparta.aiverification.review.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.aiverification.review.entity.Review;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import static com.sparta.aiverification.review.entity.QReview.review;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    // StoreId 로 조회
    // 신고가 아니고, isDeleted가 false인 Review만 조회
    @Override
    public List<Review> findAllByStoreId(UUID storeId) {
        return queryFactory
                .selectFrom(review)
                .where(review.store.id.eq(storeId),
                        review.isDeleted.eq(false),
                        review.isReported.eq(false))
                .fetch();
    }

    @Override
    public List<Review> findAll() {
        return queryFactory
                .selectFrom(review)
                .where(review.isDeleted.eq(false),
                        review.isReported.eq(false))
                .fetch();
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

}
