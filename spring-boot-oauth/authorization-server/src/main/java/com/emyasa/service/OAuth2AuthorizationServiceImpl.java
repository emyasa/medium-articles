package com.emyasa.service;

import com.emyasa.domain.AuthorizationModel;
import com.emyasa.repository.AuthorizationRepository;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OAuth2AuthorizationServiceImpl implements OAuth2AuthorizationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2AuthorizationServiceImpl.class);

    private final AuthorizationRepository authorizationRepository;

    public OAuth2AuthorizationServiceImpl(AuthorizationRepository authorizationRepository) {
        this.authorizationRepository = authorizationRepository;
    }

    @Transactional
    @Override
    public void save(OAuth2Authorization oAuth2Authorization) {
        LOGGER.info("[OAuth2AuthorizationServiceImpl][save]");
        AuthorizationModel authorization = new AuthorizationModel(oAuth2Authorization);
        authorizationRepository.save(authorization);
    }

    @Override
    public void remove(OAuth2Authorization oAuth2Authorization) {
        LOGGER.info("[OAuth2AuthorizationServiceImpl][remove]");
    }

    @Override
    public OAuth2Authorization findById(String s) {
        LOGGER.info("[OAuth2AuthorizationServiceImpl][findById]");
        return null;
    }

    @Transactional
    @Override
    public OAuth2Authorization findByToken(String s, OAuth2TokenType oAuth2TokenType) {
        LOGGER.info("[OAuth2AuthorizationServiceImpl][findByToken]: " + s);
        LOGGER.info("[OAuth2AuthorizationServiceImpl][findByToken]: " + oAuth2TokenType.getValue());
        String tokenType = Objects.nonNull(oAuth2TokenType) ? oAuth2TokenType.getValue() : null;
        AuthorizationModel authorization = authorizationRepository.findByTokensTokenAndTokensTokenType(s, tokenType)
                .orElseThrow(() -> new IllegalArgumentException("not found"));

        return authorization.getOAuth2Authorization();
    }
}
