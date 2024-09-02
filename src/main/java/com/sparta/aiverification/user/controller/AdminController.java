package com.sparta.aiverification.user.controller;

import com.sparta.aiverification.common.CommonErrorCode;
import com.sparta.aiverification.common.RestApiException;
import com.sparta.aiverification.common.RestApiResponse;
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
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
//@Secured({UserRoleEnum.Authority.MASTER})
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/create-admin")
    public RestApiResponse<String> createAdmin(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody SignupRequestDto signupRequestDto) {

        if (signupRequestDto.getRole() == null) {
            throw new RestApiException(CommonErrorCode.INVALID_PARAMETER);
        }

        adminService.createAdminUser(userDetails, signupRequestDto, signupRequestDto.getRole());

        String message = switch (signupRequestDto.getRole()) {
            case MANAGER -> "매니저 생성 완료 :)";
            case MASTER -> "마스터 생성 완료 :)";
            default -> "계정 생성 완료 :)";
        };

        return RestApiResponse.success(message);
    }

    @GetMapping("/{userId}")
    public RestApiResponse<UserResponseDto.UserDetailResponseDto> getUserById(
            @PathVariable Long userId) {

        User user = adminService.findById(userId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));

        UserResponseDto.UserDetailResponseDto responseDto = UserResponseDto.UserDetailResponseDto.of(user);
        return RestApiResponse.success(responseDto);
    }

    @DeleteMapping("/delete/{userId}")
    public RestApiResponse<UserResponseDto.UserDetailResponseDto> deleteUserById(
            @PathVariable Long userId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        // AdminService에서 삭제 처리 후 UserDetailResponseDto 반환
        UserResponseDto.UserDetailResponseDto responseDto = adminService.deleteUser(userDetails,userId);

        return RestApiResponse.success(responseDto);
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
        Page<UserResponseDto> users = adminService.getAllUsers(page,size, sortBy, isAsc);

        // 조회된 사용자 목록을 ResponseEntity에 담아 HTTP 200 OK 응답으로 반환
        return ResponseEntity.ok(users);
    }

}
