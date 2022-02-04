package com.emyasa.repository;

import com.emyasa.domain.AuthorizationToken;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface AuthTokenReadOnlyRepository extends Repository<AuthorizationToken, String> {

    Optional<AuthorizationToken> findByTokenAndTokenType(String token, String tokenType);

}
