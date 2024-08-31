package com.sparta.aiverification.user.service;

import com.sparta.aiverification.common.CommonErrorCode;
import com.sparta.aiverification.common.RestApiException;
import com.sparta.aiverification.order.dto.OrderErrorCode;
import com.sparta.aiverification.user.dto.SignupRequestDto;
import com.sparta.aiverification.user.dto.UserInfoDto;
import com.sparta.aiverification.user.dto.UserRequestDto;
import com.sparta.aiverification.user.dto.UserResponseDto;
import com.sparta.aiverification.user.entity.User;
import com.sparta.aiverification.user.enums.UserRoleEnum;
import com.sparta.aiverification.user.jwt.JwtUtil;
import com.sparta.aiverification.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CacheManager cacheManager;


    private final JwtUtil jwtUtil;

    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String address = requestDto.getAddress();
        String phone = requestDto.getPhone();
        UserRoleEnum role = requestDto.getRole();
        String email = requestDto.getEmail();

        //validation
        validateUser(username,email);
        validateRole(role);

        // 사용자 등록
        User user = User.builder()
                .username(username)
                .password(password)
                .email(email)
                .phone(phone)
                .address(address)
                .role(role)
                .build();

        userRepository.save(user);
    }

    //회원 존재 여부 검증
    public UserInfoDto verifyUser(String token) {
        // JWT 토큰에서 "Bearer " 부분을 제거
        token = token.substring(7);

        // 토큰 검증
        if (jwtUtil.validateToken(token)) {
            String username = jwtUtil.getUserInfoFromToken(token).getSubject();
            User user = userRepository.findByUsername(username).orElse(null);

            if (user != null) {
                boolean isAdmin = user.getRole().equals(UserRoleEnum.MASTER) || user.getRole().equals(UserRoleEnum.MANAGER);
                return new UserInfoDto(user.getUsername(), isAdmin);
            }
        }
        throw new RestApiException(CommonErrorCode.INVALID_PARAMETER);
    }

    public UserResponseDto updateUser(Long userId, UserRequestDto userRequestDto) {
        String username = userRequestDto.getUsername();
        String email = userRequestDto.getEmail();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 비밀번호가 있는 경우만 인코딩
        if (userRequestDto.getPassword() != null && !userRequestDto.getPassword().isBlank()) {
            String encodedPassword = passwordEncoder.encode(userRequestDto.getPassword());
            user.setPassword(encodedPassword);
        }

        // 기존 권한 저장
        UserRoleEnum oldRole = user.getRole();

        //validation
        validateRole(userRequestDto.getRole());

        // 사용자 정보 업데이트
        user.updateInfo(userRequestDto);

        // 권한 변경 시 캐시 삭제
        if (userRequestDto.getRole() != null && !userRequestDto.getRole().equals(oldRole)) {
            Cache cache = cacheManager.getCache("users");
            if (cache != null) {
                cache.evict(user.getUsername());
            }
        }

        // 변경된 사용자 정보를 저장
        User updatedUser = userRepository.save(user);

        // 응답 DTO 생성
        return new UserResponseDto().of(updatedUser);
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));

        userRepository.delete(user);
        log.info("User with ID {} has been deleted", userId);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(OrderErrorCode.NOT_FOUND_ORDER));
    }

    // 사용자 존재 여부 검증
    private void validateUser(String username, String email) {
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }
    }

    // 권한 확인
    private void validateRole(UserRoleEnum role) {
        if (UserRoleEnum.MASTER.equals(role) || UserRoleEnum.MANAGER.equals(role)) {
            throw new IllegalArgumentException("관리자 및 매니저는 등록이 불가능합니다.");
        }
    }




}
