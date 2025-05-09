package com.sud.userservice.repositories;

import com.sud.userservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByValueAndIsDeletedAndExpiresAtGreaterThan(
            String token, boolean isDeleted, LocalDateTime expiresAt);

    Optional<Token> findByValue(String token);
}
