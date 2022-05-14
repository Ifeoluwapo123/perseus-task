package com.perseus.task.repository;

import com.perseus.task.model.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
}
