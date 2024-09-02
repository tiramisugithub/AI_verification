package com.sparta.aiverification.order.repository;

import com.sparta.aiverification.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Orders, UUID>, OrderRepositoryCustom {

}
