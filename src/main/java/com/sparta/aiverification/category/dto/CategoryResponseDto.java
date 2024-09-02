package com.sparta.aiverification.category.dto;

import com.sparta.aiverification.category.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDto {
  private Long categoryId;
  private String categoryName;

  public CategoryResponseDto(Category category){
    this.categoryId = category.getId();
    this.categoryName = category.getName();
  }

}
