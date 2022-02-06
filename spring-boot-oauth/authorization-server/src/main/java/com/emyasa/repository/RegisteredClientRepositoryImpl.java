package com.emyasa.repository;

import com.emyasa.domain.RegisteredClientModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RegisteredClientRepositoryImpl implements RegisteredClientRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisteredClientRepositoryImpl.class);

    private final RegisteredClientModelRepository registeredClientModelRepository;

    public RegisteredClientRepositoryImpl(RegisteredClientModelRepository registeredClientModelRepository) {
        this.registeredClientModelRepository = registeredClientModelRepository;
    }

    @Transactional
    @Override
    public void save(RegisteredClient registeredClient) {
        LOGGER.info("[RegisteredClientRepositoryImpl][save]");
        RegisteredClientModel registeredClientModel = new RegisteredClientModel(registeredClient);
        registeredClientModelRepository.save(registeredClientModel);
    }

    @Override
    public RegisteredClient findById(String s) {
        LOGGER.info("[RegisteredClientRepositoryImpl][findById]");
        RegisteredClientModel registeredClientModel = registeredClientModelRepository.findById(s)
                .orElseThrow(() -> new IllegalArgumentException("id not found"));

        return registeredClientModel.getRegisteredClient();
    }

    @Override
    public RegisteredClient findByClientId(String s) {
        LOGGER.info("[RegisteredClientRepositoryImpl][findByClientId]");
        RegisteredClientModel registeredClientModel = registeredClientModelRepository.findByClientId(s)
                .orElseThrow(() -> new IllegalArgumentException("id not found"));

        return registeredClientModel.getRegisteredClient();
    }
}
