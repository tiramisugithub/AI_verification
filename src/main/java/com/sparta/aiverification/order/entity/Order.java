package com.sparta.aiverification.order.entity;


import com.sparta.aiverification.Timestamped;
import com.sparta.aiverification.ordermenu.entity.OrderMenu;
import com.sparta.aiverification.tmp.entity.Store;
import com.sparta.aiverification.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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


    private String detail;

    private Integer totalPrice;

    private Boolean status;

    @Builder
    public Order(User user, Store store, List<OrderMenu> orderMenuList,
                 String detail, Integer totalPrice, Boolean status){
        this.user = user;
        this.store = store;
        this.orderMenuList = orderMenuList;
        this.detail = detail;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public void addOrderMenu(OrderMenu orderMenu){
        this.orderMenuList.add(orderMenu);
    }

    public void updateTotalPrice(Integer totalPrice){
        this.totalPrice = totalPrice;
    }
}
