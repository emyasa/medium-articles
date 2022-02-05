package com.emyasa.repository;

import com.emyasa.domain.RegisteredClientModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisteredClientModelRepository extends JpaRepository<RegisteredClientModel, String> {

    Optional<RegisteredClientModel> findByClientId(String clientId);

}
