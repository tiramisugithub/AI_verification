package com.sparta.aiverification.ai.controller;

import com.sparta.aiverification.ai.dto.AIRequestDto;
import com.sparta.aiverification.ai.dto.AIResponseDto;
import com.sparta.aiverification.ai.service.AIService;
import com.sparta.aiverification.user.security.UserDetailsImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
  public AIResponseDto createAI(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestBody AIRequestDto aiRequestDto) {
    return aiService.createAI(userDetailsImpl.getUser(), aiRequestDto);
  }

  // 2. AI 목록 조회
  @GetMapping
  public List<AIResponseDto> createAIList() {
    return aiService.getAIList();
  }

  // 3. AI 정보 조회
  @GetMapping("/{aiId}")
  public AIResponseDto getAI(@PathVariable("aiId") Long aiId) {
    return aiService.getAI(aiId);
  }

  // 4. AI 수정
  @PutMapping("/{aiId}")
  public AIResponseDto updateAI(@PathVariable("aiId") Long aiId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,  @RequestBody AIRequestDto aiRequestDto) {
    return aiService.updateAI(aiId, userDetailsImpl.getUser(), aiRequestDto);
  }

  // 5. AI 삭제
  @DeleteMapping("/{aiId}")
  public ResponseEntity<String> deleteAI(@PathVariable("aiId") Long aiId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
    aiService.deleteAI(aiId,  userDetailsImpl.getUser());
    return ResponseEntity.ok("ai 질문내역이 삭제되었습니다.");
  }

}
