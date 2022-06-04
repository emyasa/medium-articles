package com.emyasa;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service-b")
public class SampleRestController {

    @GetMapping("/sample-endpoint")
    public String sampleEndpoint() {
        return "sample response";
    }

}
