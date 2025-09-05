package com.jp.dbbasedsession.security.repository;

import com.jp.dbbasedsession.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUseremail(String useremail);
}
