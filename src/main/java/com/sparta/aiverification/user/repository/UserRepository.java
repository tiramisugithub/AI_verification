package com.sparta.aiverification.user.repository;

import com.sparta.aiverification.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String name);
    Optional<User> findByEmail(String email);

    // Pageable을 이용한 검색
    Page<User> findAll(Pageable pageable);
}
