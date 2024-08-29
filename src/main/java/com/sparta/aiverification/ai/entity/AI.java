package com.sparta.aiverification.ai.entity;

import com.sparta.aiverification.Timestamped;
import com.sparta.aiverification.ai.dto.AIRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name="p_ai")
public class AI extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="ai_id", nullable = false, updatable = false)
  private Long id;

//  @OneToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "menu_id", nullable = false)
//  private Menu menuId;

  @Column(nullable = false)
  private String request;

  @Column()
  private String response;


  public void update(AIRequestDto aiRequestDto) {
    this.request = aiRequestDto.getRequest();
    this.response = aiRequestDto.getResponse();
  }
}
