package com.sparta.aiverification.user.service;

import com.sparta.aiverification.user.dto.SignupRequestDto;
import com.sparta.aiverification.user.dto.UserResponseDto;
import com.sparta.aiverification.user.entity.User;
import com.sparta.aiverification.user.enums.UserRoleEnum;
import com.sparta.aiverification.user.jwt.JwtUtil;
import com.sparta.aiverification.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
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
            throw new IllegalArgumentException("MASTER 권한이 있어야 MASTER 또는 MANAGER 계정을 생성할 수 있습니다.");
        }

        createAdminAccount(requestDto, role);
    }

    private void createAdminAccount(SignupRequestDto requestDto, UserRoleEnum role) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();

        // 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
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

}
