package com.sparta.aiverification.order.entity;


import com.sparta.aiverification.Timestamped;
import com.sparta.aiverification.ordermenu.entity.OrderMenu;
import com.sparta.aiverification.tmp.entity.Store;
import com.sparta.aiverification.tmp.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor
@Table(name = "p_order")
public class Order extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderMenu> orderMenuList = new ArrayList<>();


    private String detail;

    @NotNull
    private Integer totalPrice;

    private Boolean status = true;

    @Builder
    public Order(User user, Store store, List<OrderMenu> orderMenuList,
                 String detail, Integer totalPrice){
        this.user = user;
        this.store = store;
        this.orderMenuList = orderMenuList;
        this.detail = detail;
        this.totalPrice = totalPrice;
        this.status = true;
    }
}
