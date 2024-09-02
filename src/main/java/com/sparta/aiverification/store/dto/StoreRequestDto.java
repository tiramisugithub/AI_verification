package com.sparta.aiverification.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StoreRequestDto {
  private Long categoryId;
  private Long regionId;
  private String name;
  private String phone;
  private String address;
  private String description;
  private boolean status = true;
}