package com.sparta.aiverification.order.repository;

import com.sparta.aiverification.order.dto.OrderResponseDto;
import com.sparta.aiverification.order.entity.Order;
import com.sparta.aiverification.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findAllByUserId(Long userId);

    List<Order> findAllByStoreId(UUID storeId);
}
