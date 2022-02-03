package com.emyasa.service;

import com.emyasa.domain.AuthorizationModel;
import com.emyasa.domain.AuthorizationToken;
import com.emyasa.repository.AuthorizationModelRepository;
import com.emyasa.repository.AuthorizationTokenRepository;
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

    private final AuthorizationModelRepository authorizationModelRepository;
    private final AuthorizationTokenRepository authorizationTokenRepository;

    public OAuth2AuthorizationServiceImpl(AuthorizationModelRepository authorizationRepository, AuthorizationTokenRepository authorizationTokenRepository) {
        this.authorizationModelRepository = authorizationRepository;
        this.authorizationTokenRepository = authorizationTokenRepository;
    }

    @Transactional
    @Override
    public void save(OAuth2Authorization oAuth2Authorization) {
        LOGGER.info("[OAuth2AuthorizationServiceImpl][save]");
        AuthorizationModel authorization = new AuthorizationModel(oAuth2Authorization);
        authorizationModelRepository.save(authorization);
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
    public OAuth2Authorization findByToken(String token, OAuth2TokenType oAuth2TokenType) {
        LOGGER.info("[OAuth2AuthorizationServiceImpl][findByToken]: " + token);
        LOGGER.info("[OAuth2AuthorizationServiceImpl][findByToken]: " + oAuth2TokenType.getValue());
        String tokenType = oAuth2TokenType.getValue();
        AuthorizationToken authorizationToken = authorizationTokenRepository.findByTokenAndTokenType(token, tokenType)
                .orElseThrow(() -> new IllegalArgumentException("not found"));

        return authorizationToken.getAuthorizationModel().getOAuth2Authorization();
    }
}
