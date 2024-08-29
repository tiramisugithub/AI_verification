package com.sparta.aiverification.user.service;

import com.sparta.aiverification.user.dto.SignupRequestDto;
import com.sparta.aiverification.user.entity.User;
import com.sparta.aiverification.user.enums.UserRoleEnum;
import com.sparta.aiverification.user.jwt.JwtUtil;
import com.sparta.aiverification.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // 매니저 또는 마스터 생성
    public void createAdminUser(UserDetails userDetails, SignupRequestDto requestDto, UserRoleEnum role) {

        String roleName = userDetails.getAuthorities().toString();
        log.info("@@@AdminService : {}" ,roleName); // 인증 객체의 권한 확인

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
}
