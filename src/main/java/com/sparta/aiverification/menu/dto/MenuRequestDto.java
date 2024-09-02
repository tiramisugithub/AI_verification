package com.sparta.aiverification.menu.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuRequestDto {
  private UUID storeId;
  private String name;
  private int price;
  private String description;
  private boolean status;
}