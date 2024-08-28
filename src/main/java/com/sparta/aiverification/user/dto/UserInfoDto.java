package com.sparta.aiverification.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * UserInfoDto는 사용자 정보를 담는 DTO 클래스입니다.
 * 이 클래스는 사용자 이름과 사용자의 관리자 여부를 포함합니다.
 */
@Getter
@AllArgsConstructor
public class UserInfoDto {
    private String username;
    private boolean isAdmin;
}
