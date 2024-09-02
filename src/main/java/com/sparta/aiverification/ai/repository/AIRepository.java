package com.sparta.aiverification.ai.repository;

import com.sparta.aiverification.ai.entity.AI;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AIRepository extends JpaRepository<AI, Long> {

  Optional<AI> findByMenuId(UUID menuId);
  Page<AI> findAllByMenuId(UUID menuId, Pageable pageable);

}
