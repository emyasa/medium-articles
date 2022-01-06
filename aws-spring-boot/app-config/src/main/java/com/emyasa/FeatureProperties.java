package com.emyasa;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "feature")
@Data
public class FeatureProperties {

    private boolean enabled;
    private int limit;


}
