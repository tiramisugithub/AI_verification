package com.sparta.aiverification.region.controller;

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
  public String createRegion(@RequestBody String regionName) {
    return regionService.createRegion(regionName);
  }

  // 2. 지역 목록 조회
  @GetMapping
  public List<String> getRegionList() {
    return regionService.getRegionList();
  }

  // 3. 지역 정보 조회
  @GetMapping("/{regionId}")
  public String getRegion(@PathVariable Long regionId) {
    return regionService.getRegion(regionId);
  }

  // 4. 지역 수정
  @PutMapping("/{regionId}")
  public String updateRegion(@PathVariable Long regionId, @RequestBody String regionName) {
    return regionService.updateRegion(regionId, regionName);
  }

  // 5. 지역 삭제
  @DeleteMapping("/{regionId}")
  public ResponseEntity<String> deleteRegion(@PathVariable Long regionId) {
    regionService.deleteRegion(regionId);
    return ResponseEntity.ok("지역이 삭제되었습니다.");
  }

}
