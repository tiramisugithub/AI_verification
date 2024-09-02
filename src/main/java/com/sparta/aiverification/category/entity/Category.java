package com.sparta.aiverification.category.entity;

import com.sparta.aiverification.Timestamped;
import com.sparta.aiverification.store.entity.Store;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name="p_categories")
public class Category extends Timestamped {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="category_id", nullable = false, updatable = false)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Builder.Default
  @OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST)
  private List<Store> storeList = new ArrayList<>();

  public void update(String categoryName){
    this.name = categoryName;
  }
}
