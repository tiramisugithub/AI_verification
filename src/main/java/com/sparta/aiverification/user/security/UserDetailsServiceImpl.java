package com.sparta.aiverification.user.security;


import com.sparta.aiverification.user.entity.User;
import com.sparta.aiverification.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
/**
 * UserDetailsServiceImpl은 Spring Security의 `UserDetailsService`를 구현한 클래스입니다.
 * 이 클래스는 사용자 이름을 기반으로 사용자 정보를 로드하는 기능을 제공합니다.
 */
@Service
@RequiredArgsConstructor
@Slf4j(topic = "UserDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Cacheable(value = "users", key = "args[0]")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("called loadUserByUsername");
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found " + username));

        return new UserDetailsImpl(user);
    }
}