package com.emyasa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

@Entity
public class RegisteredClientModel {

    @Id
    private String id;

    @Column(nullable = false, unique = true)
    private String clientId;

    @Column(nullable = false)
    @Lob
    private byte[] serializedDomain;

    public RegisteredClient getRegisteredClient() {
        return SerializationUtils.deserialize(serializedDomain);
    }

    public RegisteredClientModel(RegisteredClient registeredClient) {
        Validate.notNull(registeredClient, "registeredClient must not be null");

        this.id = registeredClient.getId();
        this.clientId = registeredClient.getClientId();
        this.serializedDomain = SerializationUtils.serialize(registeredClient);
    }

    public void update(RegisteredClient registeredClient) {
        Validate.notNull(registeredClient, "registeredClient");

        this.serializedDomain = SerializationUtils.serialize(registeredClient);
    }

    protected RegisteredClientModel() {}
}
