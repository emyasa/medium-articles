package com.emyasa.config;

import com.emyasa.domain.AuthToken;
import com.emyasa.repository.TokenReadOnlyRepository;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class CheckAuthTokenFilter extends GenericFilterBean {

    private final TokenReadOnlyRepository tokenReadOnlyRepository;

    public CheckAuthTokenFilter(TokenReadOnlyRepository tokenReadOnlyRepository) {
        this.tokenReadOnlyRepository = tokenReadOnlyRepository;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = new DefaultBearerTokenResolver().resolve((HttpServletRequest) servletRequest);
        Optional<AuthToken> authToken = tokenReadOnlyRepository.findByTokenAndTokenType(token, OAuth2AccessToken.TokenType.BEARER.getValue());
        if (!authToken.isPresent()) {
            throw new InvalidBearerTokenException("token not found.");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

}
