package com.sparta.aiverification.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

public class ReviewRequestDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Create{

        private UUID orderId;

        private String reviewDesc;

        private Integer score;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateReport{

        private UUID orderId;

        private String report;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Update {

        private UUID reviewId;

        private String reviewDesc;

        private Integer score;
    }
}
