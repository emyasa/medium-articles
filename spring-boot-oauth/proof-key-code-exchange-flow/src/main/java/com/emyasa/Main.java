package com.emyasa;

import com.emyasa.domain.UserAccount;
import com.emyasa.repository.UserAccountRepository;
import java.util.Base64;
import java.util.UUID;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.keygen.Base64StringKeyGenerator;
import org.springframework.security.crypto.keygen.StringKeyGenerator;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private RegisteredClientRepository registeredClientRepository;

    @Transactional
    @PostConstruct
    public void setupTestData() {
        UserAccount account = new UserAccount.Builder()
                .withUsername("username")
                .withPassword(new BCryptPasswordEncoder().encode("password"))
                .build();

        userAccountRepository.save(account);

        StringKeyGenerator stringKeyGenerator = new Base64StringKeyGenerator(
                Base64.getUrlEncoder().withoutPadding(), 48);

        String password = stringKeyGenerator.generateKey();
        System.out.println("password: " + password);

        RegisteredClient registrationClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("registration-client")
                .clientSecret(password)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope("client.create")
                .build();

        registeredClientRepository.save(registrationClient);

    }
}
