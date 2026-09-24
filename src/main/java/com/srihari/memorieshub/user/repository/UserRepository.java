package com.srihari.memorieshub.user.repository;

import com.srihari.memorieshub.user.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    // =========================================================
    // SEARCH USER BY USERNAME OR EMAIL
    // Case-insensitive partial search
    // =========================================================
    List<User> findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String username,
            String email
    );
}