package com.sparta.aiverification.review.repository;

import com.sparta.aiverification.review.entity.Review;

import java.util.List;
import java.util.UUID;

public interface ReviewRepositoryCustom {

    List<Review> findAllByStoreId(UUID storeId);

    List<Review> findAll();
}
