package com.emyasa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feature")
public class FeatureController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeatureController.class);

    @Autowired
    private FeatureProperties featureProperties;

    @GetMapping("/isEnabled")
    public boolean isEnabled() {
        boolean isEnabled = featureProperties.isEnabled();
        String message = "Feature is " + (isEnabled ? "enabled" : "disabled");
        LOGGER.info(message);
        return isEnabled;
    }

    @GetMapping("/limit")
    public int getLimit() {
        int limit = featureProperties.getLimit();
        String message = "Feature limit is " + limit;
        LOGGER.info(message);
        return limit;
    }
}
