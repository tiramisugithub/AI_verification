package com.sparta.aiverification.category.service;

import com.sparta.aiverification.category.dto.CategoryRequestDto;
import com.sparta.aiverification.category.dto.CategoryResponseDto;
import com.sparta.aiverification.category.entity.Category;
import com.sparta.aiverification.category.repository.CategoryRepository;
import com.sparta.aiverification.user.entity.User;
import com.sparta.aiverification.user.enums.UserRoleEnum;
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

  private static void userValidate(User user) {
    // validation
    if (user.getRole() != UserRoleEnum.MASTER && user.getRole() != UserRoleEnum.MANAGER) {
      throw new IllegalArgumentException("UNAUTHORIZED ACCESS");
    }
  }

  private final CategoryRepository categoryRepository;

  @Transactional
  public CategoryResponseDto createCategory(User user, CategoryRequestDto categoryRequestDto) {
    // validation
    userValidate(user);

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
  public CategoryResponseDto updateCategory(Long categoryId, User user, CategoryRequestDto categoryRequestDto) {
    // validation
    userValidate(user);

    Category category = categoryRepository.findById(categoryId).orElseThrow(NoSuchElementException::new);
    category.update(categoryRequestDto.getCategoryName());
    return new CategoryResponseDto(category);
  }

  @Transactional
  public void deleteCategory(Long categoryId, User user) {
    // validation
    userValidate(user);

    Category category = categoryRepository.findById(categoryId).orElseThrow(NoSuchElementException::new);
    categoryRepository.deleteById(categoryId);
    category.delete(user.getId());
  }
}
