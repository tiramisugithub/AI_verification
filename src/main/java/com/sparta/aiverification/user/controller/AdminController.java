package com.sparta.aiverification.user.controller;

import com.sparta.aiverification.user.dto.AuthResponse;
import com.sparta.aiverification.user.dto.LoginRequestDto;
import com.sparta.aiverification.user.dto.SignupRequestDto;
import com.sparta.aiverification.user.dto.UserResponseDto;
import com.sparta.aiverification.user.entity.User;
import com.sparta.aiverification.user.enums.UserRoleEnum;
import com.sparta.aiverification.user.security.UserDetailsImpl;
import com.sparta.aiverification.user.service.AdminService;
import com.sparta.aiverification.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

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

//    @GetMapping("/{userId}")
//    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long userId) {
//        User user = adminService.findById(userId)
//                .orElseThrow(() -> new NoSuchElementException("No user found with ID : " + userId));
//
//        if (user != null) {
//            UserResponseDto responseDto = new UserResponseDto().of(user);
//            return ResponseEntity.ok(responseDto);
//        } else {
//            return ResponseEntity.status(404).body(null);
//        }
//    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto.UserDetailResponseDto> getUserById(@PathVariable Long userId) {
        // 사용자가 존재하지 않는 경우 NoSuchElementException을 던집니다.
        User user = adminService.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("No user found with ID : " + userId));
        return ResponseEntity.ok(UserResponseDto.UserDetailResponseDto.of(user));
    }

    //전체 사용자 목록 조회 (페이지네이션 및 정렬 포함)
    @GetMapping("/users")
    public ResponseEntity<Page<UserResponseDto>> getAllUsers(
            @PageableDefault(size = 10, sort = "id") Pageable pageable,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "isAsc", defaultValue = "true") boolean isAsc
    ) {
        // 유효한 페이지 크기 확인 및 기본값 설정 (10, 30, 50 중 하나만 허용)
        int validSize = (size == 10 || size == 30 || size == 50) ? size : 10;

        Page<UserResponseDto> users = adminService.getAllUsers(page, validSize, sortBy, isAsc);

        // 조회된 사용자 목록을 ResponseEntity에 담아 HTTP 200 OK 응답으로 반환
        return ResponseEntity.ok(users);
    }

}
