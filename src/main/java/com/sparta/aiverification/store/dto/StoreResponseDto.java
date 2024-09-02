package com.sparta.aiverification.store.dto;


import com.querydsl.core.annotations.QueryProjection;
import com.sparta.aiverification.store.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

public class StoreResponseDto {


  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Default{
    private UUID storeId;
    private Long categoryId;
    private Long regionId;
    private String name;
    private String phone;
    private String address;
    private String description;
    private boolean status;

    public Default(Store store) {
      this.storeId = store.getId();
      this.categoryId = store.getCategory().getId();
      this.regionId = store.getRegion().getId();
      this.name = store.getName();
      this.phone = store.getPhone();
      this.address = store.getAddress();
      this.description = store.getDescription();
      this.status = store.getStatus();
    }
  }

  @Data
  public static class Get{
    private UUID storeId;
    private Long categoryId;
    private Long regionId;
    private String name;
    private String phone;
    private String address;
    private String description;
    private Boolean status;
    private Double avgScore;

    @QueryProjection
    public Get(UUID storeId, Long categoryId, Long regionId, String name, String phone, String address, String description, Boolean status, Double avgScore) {
      this.storeId = storeId;
      this.categoryId = categoryId;
      this.regionId = regionId;
      this.name = name;
      this.phone = phone;
      this.address = address;
      this.description = description;
      this.status = status;
      this.avgScore = avgScore;
    }

  }
}