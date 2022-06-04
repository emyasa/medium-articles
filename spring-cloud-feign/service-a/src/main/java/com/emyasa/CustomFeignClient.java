package com.emyasa;

import feign.RequestLine;

public interface CustomFeignClient {

    @RequestLine(value = "GET /sample-endpoint")
    String getResponse();

}
