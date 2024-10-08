package com.sparta.aiverification.ordermenu.entity;

import com.sparta.aiverification.Timestamped;
import com.sparta.aiverification.menu.entity.Menu;
import com.sparta.aiverification.order.entity.Orders;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_order_menu")
public class OrderMenu extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_menu_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @NotNull
    private Integer quantity;

    @Builder
    public OrderMenu(Orders orders, Menu menu, Integer quantity) {
        this.orders = orders;
        this.menu = menu;
        this.quantity = quantity;
    }
}
