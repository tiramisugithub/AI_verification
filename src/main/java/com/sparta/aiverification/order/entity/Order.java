package com.sparta.aiverification.order.entity;


import com.sparta.aiverification.Timestamped;
import com.sparta.aiverification.ordermenu.entity.OrderMenu;
import com.sparta.aiverification.payment.entity.Payment;
import com.sparta.aiverification.store.entity.Store;
import com.sparta.aiverification.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "p_order")
public class Order extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderMenu> orderMenuList = new ArrayList<>();

    @OneToOne(mappedBy = "order", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Payment payment;

    private String detail;

    private Integer totalPrice;

    private String deliveryAddress;

    private Boolean isDeleted = false;

    @Enumerated(EnumType.STRING)
    private OrderPaymentState orderPaymentState;

    @Enumerated(EnumType.STRING)
    private OrderType orderType;


    public void addOrderMenu(OrderMenu orderMenu) {
        this.orderMenuList.add(orderMenu);
    }

    public void updateTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void updateDetail(String detail) {
        this.detail = detail;
    }


    public void updateOrderPaymentState(OrderPaymentState orderPaymentState){
        this.orderPaymentState = orderPaymentState;
    }
    public void deleteOrder(Long userId) {
        this.delete(userId);
        this.isDeleted = true;
    }

}
