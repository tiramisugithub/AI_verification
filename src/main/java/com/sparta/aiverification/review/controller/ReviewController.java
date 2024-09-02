package com.sparta.aiverification.review.controller;

import com.sparta.aiverification.common.RestApiResponse;
import com.sparta.aiverification.review.dto.ReviewRequestDto;
import com.sparta.aiverification.review.dto.ReviewResponseDto;
import com.sparta.aiverification.review.service.ReviewService;
import com.sparta.aiverification.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public RestApiResponse<Page<ReviewResponseDto.Get>> getReviewByStoreId(
            @PathVariable UUID storeId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable) {
        return RestApiResponse.success(reviewService.getReviewByStoreId(storeId, pageable));
    }

    @GetMapping
    public RestApiResponse<Page<ReviewResponseDto.Get>> getReview(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable) {
        return RestApiResponse.success(reviewService.getReview(userDetails.getUser(), pageable));
    }

    @PostMapping("/report")
    public RestApiResponse<ReviewResponseDto.CreateReport> createReport(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody ReviewRequestDto.CreateReport requestDto) {
        return RestApiResponse.success(reviewService.createReport(userDetails.getUser(), requestDto));
    }

    @DeleteMapping("/{reviewId}")
    public RestApiResponse<ReviewResponseDto.Delete> deleteReview(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable UUID reviewId) {
        return RestApiResponse.success(reviewService.deleteReview(userDetails.getUser(), reviewId));
    }

    @PatchMapping
    public RestApiResponse<ReviewResponseDto.Update> updateReview(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody ReviewRequestDto.Update requestDto) {
        return RestApiResponse.success(reviewService.updateReview(userDetails.getUser(), requestDto));
    }

}
