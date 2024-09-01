package com.sparta.aiverification.review.controller;

import com.sparta.aiverification.common.RestApiResponse;
import com.sparta.aiverification.review.dto.ReviewRequestDto;
import com.sparta.aiverification.review.dto.ReviewResponseDto;
import com.sparta.aiverification.review.service.ReviewService;
import com.sparta.aiverification.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    // StoreId를 통한 조회
    @GetMapping("/{storeId}")
    public RestApiResponse<List<ReviewResponseDto.Get>> getReviewByStoreId(
            @PathVariable UUID storeId) {
        return RestApiResponse.success(reviewService.getReviewByStoreId(storeId));
    }

    @GetMapping
    public RestApiResponse<List<ReviewResponseDto.Get>> getReview(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return RestApiResponse.success(reviewService.getReview(userDetails.getUser()));
    }

    @PostMapping("/report")
    public RestApiResponse<ReviewResponseDto.CreateReport> createReport(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody ReviewRequestDto.CreateReport requestDto) {
        return RestApiResponse.success(reviewService.createReport(userDetails.getUser(), requestDto));
    }

}
