package com.sparta.aiverification.region.dto;

import com.sparta.aiverification.region.entity.Region;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegionResponseDto {
  private Long regionId;
  private String regionName;

  public RegionResponseDto(Region region){
    this.regionId = region.getId();
    this.regionName = region.getName();
  }

}
