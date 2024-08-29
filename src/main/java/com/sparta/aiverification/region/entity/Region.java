package com.sparta.aiverification.region.entity;

import com.sparta.aiverification.Timestamped;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name="p_regions")
public class Region extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="region_id", nullable = false, updatable = false)
  private Long id;

  @Column(nullable = false)
  private String name;

  public void update(String regionName){
    this.name = regionName;
  }


}
