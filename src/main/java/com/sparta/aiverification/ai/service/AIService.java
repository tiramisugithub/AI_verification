package com.sparta.aiverification.ai.service;

import com.sparta.aiverification.ai.dto.AIRequestDto;
import com.sparta.aiverification.ai.dto.AIResponseDto;
import com.sparta.aiverification.ai.entity.AI;
import com.sparta.aiverification.ai.repository.AIRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Slf4j
@Service
public class AIService {
  private final AIRepository aiRepository;

  @Transactional
  public AIResponseDto createAI(AIRequestDto aiRequestDto) {
    AI ai = AI.builder()
        .request(aiRequestDto.getRequest())
        .response("").build();
    aiRepository.save(ai);
    return new AIResponseDto(ai);
  }

  public List<AIResponseDto> createAIList() {
    List<AIResponseDto> aiResponseDtoList = new ArrayList<>();

    List<AI> aiList = aiRepository.findAll();
    for(AI ai : aiList){
      aiResponseDtoList.add(new AIResponseDto(ai));
    }
    return aiResponseDtoList;
  }

  public AIResponseDto getAI(Long aiId) {
    AI ai = aiRepository.findById(aiId).orElseThrow(NoSuchElementException::new);
    return new AIResponseDto(ai);
  }

  @Transactional
  public AIResponseDto updateAI(Long aiId, AIRequestDto aiRequestDto) {
    AI ai = aiRepository.findById(aiId).orElseThrow(NoSuchElementException::new);
    ai.update(aiRequestDto);
    return new AIResponseDto(ai);
  }
  @Transactional
  public void deleteAI(Long aiId) {
    AI ai = aiRepository.findById(aiId).orElseThrow(NoSuchElementException::new);
    // ai.delete();
  }
}
