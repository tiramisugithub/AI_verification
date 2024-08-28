package com.sparta.aiverification.user.controller;

import com.sparta.aiverification.user.dto.AuthResponse;
import com.sparta.aiverification.user.dto.LoginRequestDto;
import com.sparta.aiverification.user.dto.SignupRequestDto;
import com.sparta.aiverification.user.enums.UserRoleEnum;
import com.sparta.aiverification.user.security.UserDetailsImpl;
import com.sparta.aiverification.user.service.AdminService;
import com.sparta.aiverification.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/create-admin")
    public ResponseEntity<String> createManager(@AuthenticationPrincipal UserDetailsImpl userDetails, @Valid @RequestBody SignupRequestDto signupRequestDto) {
        try {
            if(signupRequestDto.getRole() == null){
                throw new IllegalArgumentException("Role cannot be null");
            }
            adminService.createAdminUser(userDetails, signupRequestDto, signupRequestDto.getRole());
            // 역할에 따른 메시지 선택
            String message;
            if (signupRequestDto.getRole() == UserRoleEnum.MANAGER) {
                message = "매니저 생성 완료 :)";
            } else if (signupRequestDto.getRole() == UserRoleEnum.MASTER) {
                message = "마스터 생성 완료 :)";
            } else {
                message = "계정 생성 완료 :)";
            }
            return ResponseEntity.ok(message);


        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
