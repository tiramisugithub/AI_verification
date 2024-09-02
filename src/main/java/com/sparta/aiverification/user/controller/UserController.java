package com.sparta.aiverification.user.controller;

import com.sparta.aiverification.common.RestApiResponse;
import com.sparta.aiverification.user.dto.*;
import com.sparta.aiverification.user.jwt.JwtUtil;
import com.sparta.aiverification.user.security.UserDetailsImpl;
import com.sparta.aiverification.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/sign-up")
    public RestApiResponse<String> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return RestApiResponse.success("회원 가입 완료 :)");
    }

    //FIXME
    @GetMapping("/verify")
    public ResponseEntity<UserInfoDto> verifyUser(
            @RequestHeader(value = "Authorization") String token) {
        UserInfoDto userInfo = userService.verifyUser(token);
        if (userInfo == null) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(userInfo);
    }

    // 유저 정보 수정
    @PatchMapping("/update")
    public RestApiResponse<UserResponseDto> updateUser(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @Valid @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto responseDto = userService.updateUser(userDetailsImpl.getUser().getId(), userRequestDto);
        return RestApiResponse.success(responseDto);
    }

    // 사용자 탈퇴
    @DeleteMapping("/delete")
    public RestApiResponse<UserResponseDto.UserDetailResponseDto> deleteUser(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        UserResponseDto.UserDetailResponseDto responseDto = userService.deleteUser(userDetailsImpl);
        log.info("탈퇴 요청 사용자 ID : {}", userDetailsImpl.getUser().getId());
        return RestApiResponse.success(responseDto);
    }


}
