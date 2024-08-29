package com.sparta.aiverification.user.dto;

import com.sparta.aiverification.user.entity.User;
import com.sparta.aiverification.user.enums.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String address;
    private UserRoleEnum role;
    private String message;

    public UserResponseDto of(final User user){
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .role(user.getRole())
                .build();
    }
}




