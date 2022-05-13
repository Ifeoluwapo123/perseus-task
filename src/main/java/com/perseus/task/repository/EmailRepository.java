package com.perseus.task.repository;

import com.perseus.task.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface EmailRepository extends JpaRepository<Email, Long> {
    boolean existsByEmail(String email);
}
