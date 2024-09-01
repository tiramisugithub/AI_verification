package com.sparta.aiverification.review.entity;

import com.sparta.aiverification.Timestamped;
import com.sparta.aiverification.order.entity.Order;
import com.sparta.aiverification.store.entity.Store;
import com.sparta.aiverification.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "p_review")
public class Review extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "review_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    private String reviewDesc;

    @Max(5)
    @Min(1)
    private Integer score;

    private Boolean isReported;

    private String report;

    private Boolean isDeleted;
}
