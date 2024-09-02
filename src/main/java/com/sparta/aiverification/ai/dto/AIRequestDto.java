package com.sparta.aiverification.ai.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AIRequestDto {
  private UUID menuId;
  private String request;
  private String response;

}
