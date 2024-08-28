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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

  @Builder
  public Menu(String id, String storeId, String name, int price, String description, Boolean status, Store store) {
    this.id = id;
    this.storeId = storeId;
    this.name = name;
    this.price = price;
    this.description = description;
    this.status = status;
    this.store = store;
  }
}