package com.sparta.aiverification.ai.service;

import com.sparta.aiverification.ai.dto.AIRequestDto;
import com.sparta.aiverification.ai.dto.AIResponseDto;
import com.sparta.aiverification.ai.entity.AI;
import com.sparta.aiverification.ai.repository.AIRepository;
import com.sparta.aiverification.menu.entity.Menu;
import com.sparta.aiverification.menu.repository.MenuRepository;
import com.sparta.aiverification.user.entity.User;
import com.sparta.aiverification.user.enums.UserRoleEnum;
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
  private final MenuRepository menuRepository;

  private static void userValidate(User user) {
    // validation
    if (user.getRole() == UserRoleEnum.CUSTOMER) {
      throw new IllegalArgumentException("UNAUTHORIZED ACCESS");
    }
  }

  @Transactional
  public AIResponseDto createAI(User user, AIRequestDto aiRequestDto) {
    // validation
    userValidate(user);

    Menu menu = menuRepository.findById(aiRequestDto.getMenuId())
        .orElseThrow(() -> new NoSuchElementException("Menu with ID " + aiRequestDto.getMenuId() + " not found"));

    AI ai = AI.builder()
        .menu(menu)
        .request(aiRequestDto.getRequest())
        .response("").build();

    aiRepository.save(ai);
    return new AIResponseDto(ai);
  }

  public List<AIResponseDto> getAIList() {
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
  public AIResponseDto updateAI(Long aiId, User user, AIRequestDto aiRequestDto) {
    // validation
    userValidate(user);

    AI ai = aiRepository.findById(aiId).orElseThrow(NoSuchElementException::new);
    ai.update(aiRequestDto);
    return new AIResponseDto(ai);
  }
  @Transactional
  public void deleteAI(Long aiId, User user) {
    // validation
    userValidate(user);

    AI ai = aiRepository.findById(aiId).orElseThrow(NoSuchElementException::new);
     ai.delete(user.getId());
  }
}
