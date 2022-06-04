package com.emyasa;

import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@AllArgsConstructor
@Import({FeignClientsConfiguration.class})
public class CustomFeignClientConfig {

    private final Encoder encoder;
    private final Decoder decoder;

    @Bean
    public CustomFeignClient customFeignClient() {
        return Feign.builder()
                .requestInterceptor(interceptor -> {
                    // Set Auth related headers
                    interceptor.header("key", "value");
                })
                .encoder(encoder)
                .decoder(decoder)
                .errorDecoder((methodKey, response) -> {
                    val defaultErrorEncoder = new ErrorDecoder.Default();
                    // Handle specific exception
                    return defaultErrorEncoder.decode(methodKey, response);
                }) // Set the appropriate url
                .target(CustomFeignClient.class, "http://localhost:8080/service-b");
    }

}
