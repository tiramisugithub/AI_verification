package com.sparta.aiverification.review.service;

import com.sparta.aiverification.common.RestApiException;
import com.sparta.aiverification.order.dto.OrderErrorCode;
import com.sparta.aiverification.order.entity.Order;
import com.sparta.aiverification.order.service.OrderService;
import com.sparta.aiverification.review.dto.ReviewErrorCode;
import com.sparta.aiverification.review.dto.ReviewRequestDto;
import com.sparta.aiverification.review.dto.ReviewResponseDto;
import com.sparta.aiverification.review.entity.Review;
import com.sparta.aiverification.review.repository.ReviewRepository;
import com.sparta.aiverification.user.entity.User;
import com.sparta.aiverification.user.enums.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderService orderService;

    public ReviewResponseDto.Create createReview(User user, ReviewRequestDto.Create requestDto) {
        Order order = orderService.findById(requestDto.getOrderId());
        if(user.getRole() == UserRoleEnum.OWNER){
            throw new RestApiException(ReviewErrorCode.UNAUTHORIZED_USER);
        }
        if (!order.getUser().getId().equals(user.getId())) {
            throw new RestApiException(OrderErrorCode.BAD_REQUEST_ORDER);
        }
        return ReviewResponseDto.Create.of(reviewRepository.save(
                Review.builder()
                        .score(requestDto.getScore())
                        .reviewDesc(requestDto.getReviewDesc())
                        .isReported(false)
                        .build()
        ));
    }

}
