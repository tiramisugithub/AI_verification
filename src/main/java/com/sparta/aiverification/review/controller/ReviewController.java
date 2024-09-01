package com.sparta.aiverification.review.controller;

import com.sparta.aiverification.common.RestApiResponse;
import com.sparta.aiverification.review.dto.ReviewRequestDto;
import com.sparta.aiverification.review.dto.ReviewResponseDto;
import com.sparta.aiverification.review.service.ReviewService;
import com.sparta.aiverification.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public RestApiResponse<ReviewResponseDto.Create> createReview(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody ReviewRequestDto.Create requestDto) {
        return RestApiResponse.success(reviewService.createReview(userDetails.getUser(), requestDto));
    }
}
