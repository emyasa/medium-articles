package com.emyasa.repository;

import com.emyasa.domain.AuthToken;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface TokenReadOnlyRepository extends Repository<AuthToken, String> {

    Optional<AuthToken> findByTokenAndTokenType(String token, String tokenType);

}
