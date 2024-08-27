package com.sparta.aiverification.menu.entity;

import com.sparta.aiverification.Timestamped;
import com.sparta.aiverification.store.entity.Store;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
@Entity
@Table(name="p_menus")
public class Menu extends Timestamped {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="menu_id", nullable = false, updatable = false)
  private String id;

  @Column(name="store_id", nullable = false, updatable = false)
  private String storeId;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private int price;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private Boolean status = false;

  @ManyToOne
  @JoinColumn(name = "store_id")
  private Store store;
}