package com.perseus.task.repository;

import com.perseus.task.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE lower(u.firstName) like %?1%"+
           "OR lower(u.lastName) like %?1%" )
    Page<User> searchUserByName(String name, Pageable pageable);
}
