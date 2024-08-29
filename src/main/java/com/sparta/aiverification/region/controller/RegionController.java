package com.sparta.aiverification.region.controller;

import com.sparta.aiverification.region.dto.RegionRequestDto;
import com.sparta.aiverification.region.dto.RegionResponseDto;
import com.sparta.aiverification.region.service.RegionService;
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
@RequestMapping("/regions")
public class RegionController {

  private  final RegionService regionService;

  @Autowired
  public RegionController(RegionService RegionService){
    this.regionService = RegionService;
  }
  // 1. 지역 생성
  @PostMapping
  public RegionResponseDto createRegion(@RequestBody RegionRequestDto regionRequestDto) {
    return regionService.createRegion(regionRequestDto);
  }

  // 2. 지역 목록 조회
  @GetMapping
  public List<RegionResponseDto> getRegionList() {
    return regionService.getRegionList();
  }

  // 3. 지역 정보 조회
  @GetMapping("/{regionId}")
  public RegionResponseDto getRegion(@PathVariable("regionId") Long regionId) {
    return regionService.getRegion(regionId);
  }

  // 4. 지역 수정
  @PutMapping("/{regionId}")
  public RegionResponseDto updateRegion(@PathVariable("regionId") Long regionId, @RequestBody RegionRequestDto regionRequestDto) {
    return regionService.updateRegion(regionId, regionRequestDto);
  }

  // 5. 지역 삭제
  @DeleteMapping("/{regionId}")
  public ResponseEntity<String> deleteRegion(@PathVariable("regionId") Long regionId) {
    regionService.deleteRegion(regionId);
    return ResponseEntity.ok("지역이 삭제되었습니다.");
  }

}
