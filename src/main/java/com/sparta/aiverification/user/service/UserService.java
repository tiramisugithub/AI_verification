package com.sparta.aiverification.user.service;

import com.sparta.aiverification.user.dto.AuthResponse;
import com.sparta.aiverification.user.dto.LoginRequestDto;
import com.sparta.aiverification.user.dto.SignupRequestDto;
import com.sparta.aiverification.user.dto.UserInfoDto;
import com.sparta.aiverification.user.entity.User;
import com.sparta.aiverification.user.enums.UserRoleEnum;
import com.sparta.aiverification.user.jwt.JwtUtil;
import com.sparta.aiverification.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 권한 토큰
    //TODO : TOKEN 명 수정
    private final String MANAGER_TOKEN = "MANAGER_SECRET_TOKEN";
    private final String MASTER_TOKEN = "MASTER_SECRET_TOKEN";
    private final JwtUtil jwtUtil;

    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String address = requestDto.getAddress();
        String phone = requestDto.getPhone();
        UserRoleEnum role = requestDto.getRole();

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // email 중복확인
        String email = requestDto.getEmail();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        // MASTER 권한 확인
        if (UserRoleEnum.MASTER.equals(role)) {
            if (!MASTER_TOKEN.equals(requestDto.getMasterToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.MASTER;
        }

        // MANAGER 권한 확인
        if (UserRoleEnum.MANAGER.equals(role)) {
            if (!MANAGER_TOKEN.equals(requestDto.getManagerToken())) {
                throw new IllegalArgumentException("매니저 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.MANAGER;
        }

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

    public AuthResponse signIn(LoginRequestDto loginRequestDto) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        // 비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        // JWT 토큰 생성
        String token = jwtUtil.createToken(username, user.getRole());

        // AuthResponse 반환
        return AuthResponse.of(token);
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
                boolean isAdmin = user.getRole().equals(UserRoleEnum.MASTER);
                return new UserInfoDto(user.getUsername(), isAdmin);
            }
        }
        return null;
    }
}
