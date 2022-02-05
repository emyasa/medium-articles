package com.emyasa;

import com.emyasa.domain.UserAccount;
import com.emyasa.repository.UserAccountRepository;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Autowired
    private UserAccountRepository userAccountRepository;

    @PostConstruct
    public void setupTestData() {
        UserAccount account = new UserAccount.Builder()
                .withUsername("username")
                .withPassword(new BCryptPasswordEncoder().encode("password"))
                .build();

        userAccountRepository.save(account);
    }
}
