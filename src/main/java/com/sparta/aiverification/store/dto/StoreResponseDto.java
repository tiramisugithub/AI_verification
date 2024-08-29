package com.sparta.aiverification.store.dto;


import com.sparta.aiverification.store.entity.Store;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StoreResponseDto {
  private UUID storeId;
  private Long categoryId;
  private Long regionId;

  private String name;
  private String phone;
  private String address;
  private String description;
  private boolean status;


  public StoreResponseDto(Store store) {
    this.storeId = store.getId();
    this.categoryId = store.getCategoryId();
    this.regionId = store.getRegionId();
    this.name = store.getName();
    this.phone = store.getPhone();
    this.address = store.getAddress();
    this.description = store.getDescription();
    this.status = store.getStatus();
  }
}