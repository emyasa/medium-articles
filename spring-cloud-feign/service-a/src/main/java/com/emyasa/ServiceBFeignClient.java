package com.emyasa;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "serviceBFeignClient", url = "http://localhost:8080/service-b")
public interface ServiceBFeignClient {

    @GetMapping(value = "/sample-endpoint")
    String getResponse();

}
