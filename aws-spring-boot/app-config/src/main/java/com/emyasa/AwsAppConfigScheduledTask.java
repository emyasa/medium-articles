package com.emyasa;

import java.io.UnsupportedEncodingException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class AwsAppConfigScheduledTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(AwsAppConfigScheduledTask.class);

    @Autowired
    private FeatureProperties featureProperties;

    @Autowired
    private AwsAppConfiguration appConfiguration;

    @Scheduled(fixedRate = 5000)
    public void pollConfiguration() throws UnsupportedEncodingException {
        LOGGER.info("polls configuration from aws app config");
        JSONObject externalizedConfig = appConfiguration.getConfiguration();
        featureProperties.setEnabled(externalizedConfig.getBoolean("enabled"));
        featureProperties.setLimit(externalizedConfig.getInt("limit"));
    }

}
