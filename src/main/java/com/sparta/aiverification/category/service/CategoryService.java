package com.sparta.aiverification.category.service;

import com.sparta.aiverification.category.entity.Category;
import com.sparta.aiverification.category.repository.CategoryRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
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
  public String createCategory(String categoryName) {
    Category category = Category.builder().name(categoryName).build();
    categoryRepository.save(category);
    return category.getId().toString();
  }

  public List<String> getCategoryList() {
    List<String> categoryList = new ArrayList<>();
    List<Category> categoryResposneList = categoryRepository.findAll();
    for(Category category : categoryResposneList){
      categoryList.add(category.getName());
    }
    return categoryList;
  }

  public String getCategory(Long categoryId) {
    Category category = categoryRepository.findById(categoryId).orElseThrow(NoSuchElementException::new);
    return category.getName();
  }

  @Transactional
  public String updateCategory(Long categoryId, String categoryName) {
    Category category = categoryRepository.findById(categoryId).orElseThrow(NoSuchElementException::new);
    category.update(categoryName);
    return category.getId().toString();
  }

  @Transactional
  public void deleteCategory(Long categoryId) {
    Category category = categoryRepository.findById(categoryId).orElseThrow(NoSuchElementException::new);
    // category.delete("username");
  }
}
