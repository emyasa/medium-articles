package com.emyasa.aspect;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleServiceIT {

    @Autowired
    private SimpleService simpleService;

    @Test
    public void create() throws InterruptedException {
        simpleService.create();
    }

    @Test
    public void update() throws InterruptedException {
        simpleService.update();
    }
}
