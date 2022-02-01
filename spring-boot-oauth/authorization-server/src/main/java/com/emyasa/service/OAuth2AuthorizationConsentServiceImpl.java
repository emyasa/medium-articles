package com.emyasa.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.stereotype.Service;

@Service
public class OAuth2AuthorizationConsentServiceImpl implements OAuth2AuthorizationConsentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2AuthorizationConsentServiceImpl.class);

    @Override
    public void save(OAuth2AuthorizationConsent oAuth2AuthorizationConsent) {
        LOGGER.info("[OAuth2AuthorizationConsentServiceImpl][save]");
    }

    @Override
    public void remove(OAuth2AuthorizationConsent oAuth2AuthorizationConsent) {
        LOGGER.info("[OAuth2AuthorizationConsentServiceImpl][remove]");
    }

    @Override
    public OAuth2AuthorizationConsent findById(String s, String s1) {
        LOGGER.info("[OAuth2AuthorizationConsentServiceImpl][findById]: " + s + ":" + s1);
        return OAuth2AuthorizationConsent.withId("3cc506e8-c564-4869-8c76-ece5ae5f32cb", "username")
                .scope("random")
                .build();

        // return null;
    }
}
