package com.sparta.aiverification.ai.repository;

import com.sparta.aiverification.ai.entity.AI;
import com.sparta.aiverification.user.entity.User;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AIRepository extends JpaRepository<AI, Long> {

  Page<AI> findAllByUser(User user, Pageable pageable);
  AI findByMenu(UUID menuId);

}
