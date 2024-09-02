package com.sparta.aiverification.region.service;

import com.sparta.aiverification.region.dto.RegionRequestDto;
import com.sparta.aiverification.region.dto.RegionResponseDto;
import com.sparta.aiverification.region.entity.Region;
import com.sparta.aiverification.region.repository.RegionRepository;
import com.sparta.aiverification.user.entity.User;
import com.sparta.aiverification.user.enums.UserRoleEnum;
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

  private static void isAdmin(User user) {
    // validation
    if (user.getRole() != UserRoleEnum.MASTER && user.getRole() != UserRoleEnum.MANAGER) {
      throw new IllegalArgumentException("UNAUTHORIZED ACCESS");
    }
  }

  @Transactional
  public RegionResponseDto createRegion(User user, RegionRequestDto regionRequestDto) {
    // validation
    isAdmin(user);

    Region region = Region.builder().name(regionRequestDto.getRegionName()).build();
    regionRepository.save(region);
    return new RegionResponseDto(region);
  }

  public List<RegionResponseDto> getRegionList() {
    List<RegionResponseDto> regionList = new ArrayList<>();

    List<Region> regionResponseList = regionRepository.findAll();
    for(Region region : regionResponseList){
      regionList.add(new RegionResponseDto(region));
    }
    return regionList;
  }

  public RegionResponseDto getRegion(Long regionId) {
    Region region = regionRepository.findById(regionId).orElseThrow(NoSuchElementException::new);
    return new RegionResponseDto(region);
  }

  @Transactional
  public RegionResponseDto updateRegion(Long regionId, User user, RegionRequestDto regionRequestDto) {
    // validation
    isAdmin(user);

    Region region = regionRepository.findById(regionId).orElseThrow(NoSuchElementException::new);
    region.update(regionRequestDto.getRegionName());
    return new RegionResponseDto(region);
  }

  @Transactional
  public void deleteRegion(Long regionId, User user) {
    // validation
    isAdmin(user);

    Region region = regionRepository.findById(regionId).orElseThrow(NoSuchElementException::new);

    region.delete(user.getId());
  }
}
