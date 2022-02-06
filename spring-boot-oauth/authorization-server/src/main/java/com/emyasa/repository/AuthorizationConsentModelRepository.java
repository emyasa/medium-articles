package com.emyasa.repository;

import com.emyasa.domain.AuthorizationConsentId;
import com.emyasa.domain.AuthorizationConsentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationConsentModelRepository extends JpaRepository<AuthorizationConsentModel, AuthorizationConsentId> {
}
