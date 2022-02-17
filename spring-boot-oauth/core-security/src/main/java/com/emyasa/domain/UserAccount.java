package com.emyasa.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import org.apache.commons.lang3.Validate;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class UserAccount {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private final Set<UserAccountRole> roles;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Set<UserAccountRole> getRoles() {
        return new HashSet<>(roles);
    }

    protected UserAccount() {
        this.roles = new HashSet<>();
    }

    public static class Builder {

        private final UserAccount userAccount = new UserAccount();

        public Builder withUsername(String username) {
            Validate.notEmpty(username, "username must not be empty");

            userAccount.username = username;
            return this;
        }

        public Builder withPassword(String password) {
            Validate.notEmpty(password, "password must not be empty");

            userAccount.password = password;
            return this;
        }

        public Builder withRoles(Set<String> roles) {
            if (Objects.nonNull(roles)) {
                Set<UserAccountRole> userAccountRoles = roles.stream()
                        .map(UserAccountRole::new)
                        .collect(Collectors.toSet());

                userAccount.roles.addAll(userAccountRoles);
            }

            return this;
        }

        public UserAccount build() {
            return userAccount;
        }
    }
}
