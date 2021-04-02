package com.emyasa.aspect;

import org.springframework.stereotype.Service;

@Service
@PerformanceLogger
public class SimpleService {

    public void create() throws InterruptedException {
        Thread.sleep(3000);
    }

    public void update() throws InterruptedException {
        Thread.sleep(2000);
    }

}
