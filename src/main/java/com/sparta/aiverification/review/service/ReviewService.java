package com.sparta.aiverification.review.service;

import com.sparta.aiverification.common.RestApiException;
import com.sparta.aiverification.order.dto.OrderErrorCode;
import com.sparta.aiverification.order.entity.Orders;
import com.sparta.aiverification.order.service.OrderService;
import com.sparta.aiverification.review.dto.ReviewErrorCode;
import com.sparta.aiverification.review.dto.ReviewRequestDto;
import com.sparta.aiverification.review.dto.ReviewResponseDto;
import com.sparta.aiverification.review.entity.Review;
import com.sparta.aiverification.review.repository.ReviewRepository;
import com.sparta.aiverification.store.entity.Store;
import com.sparta.aiverification.user.entity.User;
import com.sparta.aiverification.user.enums.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderService orderService;

    @Transactional
    public ReviewResponseDto.Create createReview(User user, ReviewRequestDto.Create requestDto) {
        Orders orders = orderService.findById(requestDto.getOrderId());
        Store store = orders.getStore();

        if (user.getRole() == UserRoleEnum.OWNER) {
            throw new RestApiException(ReviewErrorCode.UNAUTHORIZED_USER);
        }
        if (!orders.getUser().getId().equals(user.getId())) {
            throw new RestApiException(OrderErrorCode.BAD_REQUEST_ORDER);
        }
        return ReviewResponseDto.Create.of(reviewRepository.save(
                Review.builder()
                        .score(requestDto.getScore())
                        .user(user)
                        .orders(orders)
                        .store(store)
                        .isDeleted(false)
                        .reviewDesc(requestDto.getReviewDesc())
                        .isReported(false)
                        .build()
        ));
    }

    public Page<ReviewResponseDto.Get> getReviewByStoreId(UUID storeId, Pageable pageable) {
        return reviewRepository.findAllByCondition(null, storeId, pageable);
    }

    public Page<ReviewResponseDto.Get> getReview(User user, Pageable pageable) {
        if(user.getRole() == UserRoleEnum.CUSTOMER) {
            throw new RestApiException(ReviewErrorCode.UNAUTHORIZED_USER);
        }
        return reviewRepository.findAllByCondition(null, null, pageable);
    }

    public ReviewResponseDto.CreateReport createReport(User user, ReviewRequestDto.CreateReport requestDto) {
        if (user.getRole() != UserRoleEnum.CUSTOMER) {
            throw new RestApiException(OrderErrorCode.UNAUTHORIZED_USER);
        }
        Orders orders = orderService.findById(requestDto.getOrderId());
        return ReviewResponseDto.CreateReport.of(reviewRepository.save(
                Review.builder()
                        .orders(orders)
                        .user(user)
                        .store(orders.getStore())
                        .isReported(true)
                        .report(requestDto.getReport())
                        .build()));
    }

    @Transactional
    public ReviewResponseDto.Delete deleteReview(User user, UUID reviewId) {
        if(user.getRole() == UserRoleEnum.OWNER){
            throw new RestApiException(ReviewErrorCode.UNAUTHORIZED_USER);
        }
        Review review = findById(reviewId);
        if(!review.getUser().getId().equals(user.getId())){
            throw new RestApiException(ReviewErrorCode.BAD_REQUEST_REVIEW);
        }
        review.updateDelete(user.getId());
        return ReviewResponseDto.Delete.of(review);
    }

    @Transactional
    public ReviewResponseDto.Update updateReview(User user, ReviewRequestDto.Update requestDto) {
        if (user.getRole() == UserRoleEnum.OWNER) {
            throw new RestApiException(ReviewErrorCode.UNAUTHORIZED_USER);
        }
        Review review = findById(requestDto.getReviewId());
        if(!review.getUser().getId().equals(user.getId())){
            throw new RestApiException(ReviewErrorCode.BAD_REQUEST_REVIEW);
        }
        review.updateReview(requestDto);
        return ReviewResponseDto.Update.of(review);
    }
    public Review findById(UUID reviewId){
        return reviewRepository.findByReviewId(reviewId);
    }

}
