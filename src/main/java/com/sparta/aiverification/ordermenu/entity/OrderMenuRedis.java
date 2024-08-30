package com.sparta.aiverification.ordermenu.entity;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.UUID;

@Getter
@RedisHash("orderMenu")
@NoArgsConstructor
public class OrderMenuRedis implements Serializable {

    @Id
    private String id;

    private UUID menuId;

    private Long userId;

    private Integer quantity;


    @Builder
    public OrderMenuRedis(UUID menuId, Long userId, Integer quantity) {
        this.menuId = menuId;
        this.userId = userId;
        this.quantity = quantity;
    }

}
