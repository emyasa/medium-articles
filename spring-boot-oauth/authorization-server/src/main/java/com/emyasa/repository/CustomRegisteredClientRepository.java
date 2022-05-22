package com.emyasa.repository;

import org.apache.commons.lang3.Validate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

public class CustomRegisteredClientRepository implements RegisteredClientRepository {

    private final RegisteredClientRepository delegate;
    private final PasswordEncoder passwordEncoder;

    public CustomRegisteredClientRepository(RegisteredClientRepository delegate, PasswordEncoder passwordEncoder) {
        Validate.notNull(delegate, "delegate must not be null");
        Validate.notNull(passwordEncoder, "passwordEncoder must not be null");

        this.delegate = delegate;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void save(RegisteredClient registeredClient) {
        Validate.notNull(registeredClient, "registeredClient must not be null");
        final RegisteredClient encodedRegisteredClient = RegisteredClient.from(registeredClient)
                .clientSecret(passwordEncoder.encode(registeredClient.getClientSecret()))
                .build();

        delegate.save(encodedRegisteredClient);
    }

    @Override
    public RegisteredClient findById(String s) {
        return delegate.findById(s);
    }

    @Override
    public RegisteredClient findByClientId(String s) {
        return delegate.findByClientId(s);
    }
}
