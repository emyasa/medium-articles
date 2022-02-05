package com.emyasa.domain;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.Validate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {

    private String username;
    private String password;
    private final Set<SimpleGrantedAuthority> authorities;

    private UserDetailsImpl() {
        authorities = new HashSet<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static class Builder {

        private UserDetailsImpl userDetails = new UserDetailsImpl();

        public Builder withUsername(String username) {
            Validate.notNull(username, "username must not be null");

            userDetails.username = username;
            return this;
        }

        public Builder withPassword(String password) {
            Validate.notNull(password, "password must not be null");

            userDetails.password = password;
            return this;
        }

        public Builder withAuthorities(List<String> roles) {
            if (Objects.nonNull(roles)) {
                Set<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toSet());

                userDetails.authorities.addAll(authorities);
            }

            return this;
        }

        public UserDetails build() {
            return userDetails;
        }
    }
}
