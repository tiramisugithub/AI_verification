package com.sparta.aiverification.category.service;

import com.sparta.aiverification.category.dto.CategoryRequestDto;
import com.sparta.aiverification.category.dto.CategoryResponseDto;
import com.sparta.aiverification.category.entity.Category;
import com.sparta.aiverification.category.repository.CategoryRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  @Transactional
  public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {
    Category category = Category.builder().name(categoryRequestDto.getCategoryName()).build();
    categoryRepository.save(category);
    return new CategoryResponseDto(category);
  }

  public List<CategoryResponseDto> getCategoryList() {
    List<CategoryResponseDto> categoryList = new ArrayList<>();
    List<Category> categoryResposneList = categoryRepository.findAll();
    for(Category category : categoryResposneList){
      categoryList.add(new CategoryResponseDto(category));
    }
    return categoryList;
  }

  public CategoryResponseDto getCategory(Long categoryId) {
    Category category = categoryRepository.findById(categoryId).orElseThrow(NoSuchElementException::new);
    return new CategoryResponseDto(category);
  }

  @Transactional
  public CategoryResponseDto updateCategory(Long categoryId, String categoryName) {
    Category category = categoryRepository.findById(categoryId).orElseThrow(NoSuchElementException::new);
    category.update(categoryName);
    return new CategoryResponseDto(category);
  }

  @Transactional
  public void deleteCategory(Long categoryId) {
    Category category = categoryRepository.findById(categoryId).orElseThrow(NoSuchElementException::new);
    categoryRepository.deleteById(categoryId);
    // category.delete("username");
  }
}
