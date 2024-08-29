package com.sparta.aiverification.store.dto;

import com.sparta.aiverification.category.entity.Category;
import com.sparta.aiverification.region.entity.Region;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StoreRequestDto {
  private Category category;
  private Region region;
  private String name;
  private String phone;
  private String address;
  private String description;
  private boolean status = true;
}