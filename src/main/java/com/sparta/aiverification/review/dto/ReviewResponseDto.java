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


}
