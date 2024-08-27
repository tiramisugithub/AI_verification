package com.sparta.aiverification.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuResponseDto {
  private String menuId;
  private String name;
  private int price;
  private String description;
  private boolean status;
}