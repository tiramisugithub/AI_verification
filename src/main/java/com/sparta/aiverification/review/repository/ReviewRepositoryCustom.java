package com.sparta.aiverification.review.repository;

import com.sparta.aiverification.review.dto.ReviewResponseDto;
import com.sparta.aiverification.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ReviewRepositoryCustom {

    Page<ReviewResponseDto.Get> findAllByCondition(Long userId, UUID storeId, Pageable pageable);

    Review findByReviewId(UUID reviewId);
}
