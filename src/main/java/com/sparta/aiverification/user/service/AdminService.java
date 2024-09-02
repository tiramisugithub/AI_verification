package com.sparta.aiverification.user.service;

import com.sparta.aiverification.common.CommonErrorCode;
import com.sparta.aiverification.common.RestApiException;
import com.sparta.aiverification.user.dto.SignupRequestDto;
import com.sparta.aiverification.user.dto.UserErrorCode;
import com.sparta.aiverification.user.dto.UserResponseDto;
import com.sparta.aiverification.user.entity.User;
import com.sparta.aiverification.user.enums.UserRoleEnum;
import com.sparta.aiverification.user.jwt.JwtUtil;
import com.sparta.aiverification.user.repository.UserRepository;
import com.sparta.aiverification.user.security.UserDetailsImpl;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // 매니저 또는 마스터 생성
    public void createAdminUser(UserDetails userDetails, SignupRequestDto requestDto, UserRoleEnum role) {
        boolean hasRoleMaster = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                        .allMatch("ROLE_MASTER"::equals);

        if(!hasRoleMaster){
            throw new RestApiException(UserErrorCode.UNAUTHORIZED_USER);
        }

        createAdminAccount(requestDto, role);
    }

    private void createAdminAccount(SignupRequestDto requestDto, UserRoleEnum role) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();

        // 중복 확인
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RestApiException(UserErrorCode.DUPLICATE_USERNAME);
        }

        if (userRepository.findByEmail(email).isPresent()) {
            throw new RestApiException(UserErrorCode.DUPLICATE_EMAIL);
        }

        User user = User.builder()
                .username(username)
                .password(password)
                .email(email)
                .phone(requestDto.getPhone())
                .address(requestDto.getAddress())
                .role(role)
                .build();

        userRepository.save(user);
    }

    public Optional<User> findById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        userOptional.ifPresent(user -> log.info("Found user: {}", user));
        // 조회된 사용자 정보를 담은 Optional을 반환
        return userOptional;
    }

    public Page<UserResponseDto> getAllUsers(int page, int size, String sortBy, boolean isAsc) {
        // 정렬 기준과 방향에 따라 Sort 객체를 생성
        Sort sort = isAsc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        // 페이지 번호와 페이지 크기, 정렬 기준을 기반으로 Pageable 객체를 생성
        Pageable pageable = PageRequest.of(page, size, sort);
        return userRepository.findAll(pageable).map(UserResponseDto::of);
    }

    public UserResponseDto.UserDetailResponseDto deleteUser(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));

        // 사용자 소프트 삭제
        user.softDelete(userDetailsImpl.getUser().getId());
        userRepository.save(user);

        // UserDetailResponseDto로 변환하여 반환
        return UserResponseDto.UserDetailResponseDto.of(user);
    }

}
