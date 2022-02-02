package com.emyasa.repository;

import com.emyasa.domain.AuthorizationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationModelRepository extends JpaRepository<AuthorizationModel, String> {
}
