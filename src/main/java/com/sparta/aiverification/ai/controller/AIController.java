package com.sparta.aiverification.ai.controller;

import com.sparta.aiverification.ai.dto.AIRequestDto;
import com.sparta.aiverification.ai.dto.AIResponseDto;
import com.sparta.aiverification.ai.service.AIService;
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
@RequestMapping("/ai")
public class AIController {

  private  final AIService aiService;

  @Autowired
  public AIController(AIService aiService){
    this.aiService = aiService;
  }
  // 1. AI 생성
  @PostMapping
  public AIResponseDto createAI(@RequestBody AIRequestDto aiRequestDto) {
    return aiService.createAI(aiRequestDto);
  }

  // 2. AI 목록 조회
  @GetMapping
  public List<AIResponseDto> createAIList() {
    return aiService.createAIList();
  }

  // 3. AI 정보 조회
  @GetMapping("/{aiId}")
  public AIResponseDto getAI(@PathVariable Long aiId) {
    return aiService.getAI(aiId);
  }

  // 4. AI 수정
  @PutMapping("/{aiId}")
  public AIResponseDto updateAI(@PathVariable Long aiId, @RequestBody AIRequestDto aiRequestDto) {
    return aiService.updateAI(aiId, aiRequestDto);
  }

  // 5. AI 삭제
  @DeleteMapping("/{aiId}")
  public ResponseEntity<String> deleteAI(@PathVariable Long aiId) {
    aiService.deleteAI(aiId);
    return ResponseEntity.ok("지역이 삭제되었습니다.");
  }

}
