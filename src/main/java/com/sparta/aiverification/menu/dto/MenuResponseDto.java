package com.sparta.aiverification.menu.dto;

import com.sparta.aiverification.menu.entity.Menu;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuResponseDto {
  private UUID menuId;
  private String name;
  private int price;
  private String description;
  private boolean status;

  public MenuResponseDto(Menu menu) {
    this.menuId = menu.getId();
    this.name = menu.getName();
    this.price = menu.getPrice();
    this.description = menu.getDescription();
    this.status = menu.getStatus();
  }
}