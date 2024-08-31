package com.sparta.aiverification.ordermenu.repository;

import com.sparta.aiverification.ordermenu.entity.OrderMenuRedis;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface OrderMenuRedisRepository extends CrudRepository<OrderMenuRedis, String> {

    List<OrderMenuRedis> findAllByUserId(Long id);

    List<OrderMenuRedis> findAllByMenuId(UUID menuId);
}
