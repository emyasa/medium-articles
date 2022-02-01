package com.emyasa.repository;

import com.emyasa.domain.AuthorizationToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationTokenRepository extends JpaRepository<AuthorizationToken, String> {

    Optional<AuthorizationToken> findByTokenAndTokenType(String token, String tokenType);

}
