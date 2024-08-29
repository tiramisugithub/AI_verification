package com.sparta.aiverification.store.entity;

import com.sparta.aiverification.Timestamped;
import com.sparta.aiverification.menu.entity.Menu;
import com.sparta.aiverification.store.dto.StoreRequestDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder()
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name="p_stores")
public class Store extends Timestamped {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="store_id", nullable = false, updatable = false)
  private UUID id;

  @Column(name="user_id", nullable = false, updatable = false)
  private Long userId;

  @Column(name="region_id", nullable = false)
  private Long regionId;

  @Column(name="category_id", nullable = false)
  private Long categoryId;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String address;

  @Column(nullable = false)
  private String phone;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private Boolean status = false;


  // Cascade.PERSIST : 영속성 전이 : 영속 상태의 작업들이 연관된 엔티티들까지 전파
  @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<Menu> menus = new ArrayList<>();


  public void update(StoreRequestDto storeRequestDto) {
    this.regionId = storeRequestDto.getRegionId();
    this.categoryId = storeRequestDto.getCategoryId();
    this.name = storeRequestDto.getName();
    this.phone = storeRequestDto.getPhone();
    this.description = storeRequestDto.getDescription();
    this.address = storeRequestDto.getAddress();
  }

  public void setStatusFalse() {
    this.status = false;
  }
}