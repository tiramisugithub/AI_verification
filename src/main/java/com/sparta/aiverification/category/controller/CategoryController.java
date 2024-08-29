package com.sparta.aiverification.category.controller;

import com.sparta.aiverification.category.dto.CategoryResponseDto;
import com.sparta.aiverification.category.service.CategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryController {

  private final CategoryService categoryService;

  @Autowired
  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  // 1. 카테고리 생성
  @PostMapping
  public CategoryResponseDto createCategory(@RequestBody String categoryName) {
    return categoryService.createCategory(categoryName);
  }

  // 2. 카테고리 목록 조회
  @GetMapping
  public List<CategoryResponseDto> getCategoryList() {
    return categoryService.getCategoryList();
  }

  // 3. 카테고리 정보 조회
  @GetMapping("/{categoryId}")
  public CategoryResponseDto getCategory(@PathVariable Long categoryId) {
    return categoryService.getCategory(categoryId);
  }

  // 4. 카테고리 수정
  @PutMapping("/{categoryId}")
  public CategoryResponseDto updateCategory(@PathVariable Long categoryId, @RequestBody String categoryName) {
    return categoryService.updateCategory(categoryId, categoryName);
  }

  // 5. 카테고리 삭제
  @DeleteMapping("/{categoryId}")
  public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
    categoryService.deleteCategory(categoryId);
    return ResponseEntity.ok("카테고리가 삭제되었습니다");
  }

}
