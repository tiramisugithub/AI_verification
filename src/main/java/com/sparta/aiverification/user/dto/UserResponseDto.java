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
    //private String message;
    //private String createdAt;

    public static UserResponseDto of(final User user){
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .role(user.getRole())
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserDetailResponseDto {
        private Long id;
        private String username;
        private String email;
        private String phone;
        private String address;
        private UserRoleEnum role;
        private String createdAt;
        private String createdBy;
        private String deletedAt;
        private String deletedBy;
        private String updatedAt;
        private String updatedBy;

        public static UserDetailResponseDto of(final User user) {
            return UserDetailResponseDto.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .phone(user.getPhone())
                    .address(user.getAddress())
                    .role(user.getRole())
                    .createdAt(user.getCreatedAt() != null ? user.getCreatedAt().toString() : null)
                    .createdBy(user.getCreatedBy() != null ? String.valueOf(user.getCreatedBy()) : null)
                    .deletedAt(user.getDeletedAt() != null ? user.getDeletedAt().toString() : null)
                    .deletedBy(user.getDeletedBy() != null ? String.valueOf(user.getDeletedBy()) : null)
                    .updatedAt(user.getUpdatedAt() != null ? user.getUpdatedAt().toString() : null)
                    .updatedBy(user.getUpdatedBy() != null ? String.valueOf(user.getUpdatedBy()) : null)
                    .build();
        }

    }

}




