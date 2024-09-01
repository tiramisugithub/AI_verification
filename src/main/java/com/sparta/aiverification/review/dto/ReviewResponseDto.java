package com.sparta.aiverification.review.dto;

import com.sparta.aiverification.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

public class ReviewResponseDto {


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Create{

        private String desc;

        private Integer score;

        public static Create of(Review review) {
            return Create.builder()
                    .desc(review.getReviewDesc())
                    .score(review.getScore())
                    .build();
        }
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Get {

        private UUID reviewId;

        private String reviewDesc;

        private Integer score;

        public static Get of(Review review){
            return Get.builder()
                    .reviewId(review.getId())
                    .reviewDesc(review.getReviewDesc())
                    .score(review.getScore())
                    .build();
        }
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateReport {

        private UUID orderId;

        private String report;

        public static CreateReport of(Review review){
            return CreateReport.builder()
                    .orderId(review.getOrder().getId())
                    .report(review.getReport())
                    .build();
        }
    }
}
