package com.sparta.aiverification.ordermenu.entity;

import com.sparta.aiverification.Timestamped;
import com.sparta.aiverification.order.entity.Order;
import com.sparta.aiverification.tmp.entity.Menu;
import com.sparta.aiverification.tmp.entity.Store;
import com.sparta.aiverification.tmp.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_order_menu")
public class OrderMenu extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_menu_id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    private Integer quantity;

    @Builder
    public OrderMenu(Order order, Store store, Menu menu, User user, Integer quantity) {
        this.order = order;
        this.store = store;
        this.menu = menu;
        this.user = user;
        this.quantity = quantity;
    }
}
