package com.sparta.aiverification.ai.dto;

import com.sparta.aiverification.ai.entity.AI;
import com.sparta.aiverification.menu.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AIResponseDto {
  private Menu menuId;
  private String request;
  private String response;

  public AIResponseDto(AI ai) {
    this.menuId = ai.getMenuId();
    this.request = ai.getRequest();
    this.response = ai.getResponse();
  }
}
