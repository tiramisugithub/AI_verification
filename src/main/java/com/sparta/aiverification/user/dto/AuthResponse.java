package com.sparta.aiverification.user.dto;


import lombok.*;

/**
 * AuthResponse는 유저 로그인 시 반환되는 응답 객체를 나타냅니다.
 * 이 객체는 클라이언트에게 JWT 액세스 토큰을 전달하는 데 사용됩니다.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class AuthResponse {
    private String accessToken;

    public static AuthResponse of(String accessToken) {
        return AuthResponse.builder().accessToken(accessToken).build();
    }
}
