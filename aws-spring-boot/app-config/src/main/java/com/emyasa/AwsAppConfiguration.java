package com.emyasa;

import com.amazonaws.services.appconfig.AmazonAppConfig;
import com.amazonaws.services.appconfig.AmazonAppConfigClient;
import com.amazonaws.services.appconfig.model.GetConfigurationRequest;
import com.amazonaws.services.appconfig.model.GetConfigurationResult;
import java.io.UnsupportedEncodingException;
import java.util.Objects;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsAppConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(AwsAppConfiguration.class);

    private final AmazonAppConfig appConfig;
    private final GetConfigurationRequest request;

    public AwsAppConfiguration() {
        appConfig = AmazonAppConfigClient.builder().build();
        request = new GetConfigurationRequest();
        request.setClientId("clientId");
        request.setApplication("FeatureFlags");
        request.setConfiguration("demo");
        request.setEnvironment("dev");
    }

    public JSONObject getConfiguration() throws UnsupportedEncodingException {
        GetConfigurationResult result = appConfig.getConfiguration(request);
        String message = String.format("contentType: %s", result.getContentType());
        LOGGER.info(message);

        if (!Objects.equals("application/json", result.getContentType())) {
            throw new IllegalStateException("config is expected to be JSON");
        }

        String content = new String(result.getContent().array(), "ASCII");
        return new JSONObject(content).getJSONObject("feature");
    }
}
