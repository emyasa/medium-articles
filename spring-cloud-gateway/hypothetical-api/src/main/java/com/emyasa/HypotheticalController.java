package com.emyasa;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HypotheticalController {

    @GetMapping
    public void hypotheticalEndpoint(@RequestHeader("Authorization") String authorization) {
        System.out.println(authorization);
    }

}
