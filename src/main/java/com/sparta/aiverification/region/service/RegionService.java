package com.sparta.aiverification.region.service;

import com.sparta.aiverification.region.entity.Region;
import com.sparta.aiverification.region.repository.RegionRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Slf4j
@Service
public class RegionService {
  private final RegionRepository regionRepository;

  @Transactional
  public String createRegion(String regionName) {
    Region region = Region.builder().name(regionName).build();
    regionRepository.save(region);
    return region.getId().toString();
  }

  public List<String> getRegionList() {
    List<String> regionList = new ArrayList<>();

    List<Region> regionResponseList = regionRepository.findAll();
    for(Region region : regionResponseList){
      regionList.add(region.getName());
    }
    return regionList;
  }

  public String getRegion(Long regionId) {
    Region region = regionRepository.findById(regionId).orElseThrow(NoSuchElementException::new);
    return region.getName();
  }

  @Transactional
  public String updateRegion(Long regionId, String regionName) {
    Region region = regionRepository.findById(regionId).orElseThrow(NoSuchElementException::new);
    region.update(regionName);
    return region.getId().toString();
  }

  @Transactional
  public void deleteRegion(Long regionId) {
    Region region = regionRepository.findById(regionId).orElseThrow(NoSuchElementException::new);
    //region.delete("");
  }
}
