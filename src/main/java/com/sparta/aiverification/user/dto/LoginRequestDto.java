package com.sparta.aiverification.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * LoginRequestDto는 사용자가 로그인할 때 제공하는 데이터를 담는 DTO 클래스입니다.
 * 이 클래스는 사용자 이름과 비밀번호를 포함하며, 두 필드 모두 필수로 입력되어야 합니다.
 */
@Getter
@Setter
public class LoginRequestDto {
    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Password is mandatory")
    private String password;
}
