package com.emyasa.repository;

import com.emyasa.domain.AuthorizationModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationRepository extends JpaRepository<AuthorizationModel, String> {

    Optional<AuthorizationModel> findByTokensTokenAndTokensTokenType(String token, String tokenType);

}
