package com.emyasa.service;

import com.emyasa.domain.AuthorizationConsentId;
import com.emyasa.domain.AuthorizationConsentModel;
import com.emyasa.repository.AuthorizationConsentModelRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OAuth2AuthorizationConsentServiceImpl implements OAuth2AuthorizationConsentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2AuthorizationConsentServiceImpl.class);

    private final AuthorizationConsentModelRepository authorizationConsentModelRepository;

    public OAuth2AuthorizationConsentServiceImpl(AuthorizationConsentModelRepository authorizationConsentModelRepository) {
        this.authorizationConsentModelRepository = authorizationConsentModelRepository;
    }

    @Transactional
    @Override
    public void save(OAuth2AuthorizationConsent oAuth2AuthorizationConsent) {
        LOGGER.info("[OAuth2AuthorizationConsentServiceImpl][save]");
        AuthorizationConsentModel authorizationConsentModel = new AuthorizationConsentModel(oAuth2AuthorizationConsent);
        authorizationConsentModelRepository.save(authorizationConsentModel);
    }

    @Transactional
    @Override
    public void remove(OAuth2AuthorizationConsent oAuth2AuthorizationConsent) {
        LOGGER.info("[OAuth2AuthorizationConsentServiceImpl][remove]");
        AuthorizationConsentId authorizationConsentId = new AuthorizationConsentId(oAuth2AuthorizationConsent);
        authorizationConsentModelRepository.deleteById(authorizationConsentId);
    }

    @Override
    public OAuth2AuthorizationConsent findById(String registeredClientId, String principalName) {
        LOGGER.info("[OAuth2AuthorizationConsentServiceImpl][findById]");
        AuthorizationConsentId authorizationConsentId = new AuthorizationConsentId(registeredClientId, principalName);
        Optional<AuthorizationConsentModel> authorizationConsentModel = authorizationConsentModelRepository.findById(authorizationConsentId);
        return authorizationConsentModel.map(AuthorizationConsentModel::getOAuth2AuthorizationConsent).orElse(null);
    }
}
