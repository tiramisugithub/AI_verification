package com.sparta.aiverification.user.controller;

import com.sparta.aiverification.user.dto.AuthResponse;
import com.sparta.aiverification.user.dto.LoginRequestDto;
import com.sparta.aiverification.user.dto.SignupRequestDto;
import com.sparta.aiverification.user.dto.UserInfoDto;
import com.sparta.aiverification.user.jwt.JwtUtil;
import com.sparta.aiverification.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/sign-up")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        try {
            userService.signup(signupRequestDto);
            return ResponseEntity.ok("회원 가입 완료 :)");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> signIn(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            AuthResponse authResponse = userService.signIn(loginRequestDto);
            return ResponseEntity.ok(authResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<UserInfoDto> verifyUser(
            @RequestHeader(value = "Authorization") String token) {
        UserInfoDto userInfo = userService.verifyUser(token);
        if (userInfo == null) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(userInfo);
    }


}
