package com.emyasa;

import lombok.val;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceBFeignClientIT {

    @Autowired
    private ServiceBFeignClient feignClient;

    @Test
    public void test() {
        val response = feignClient.getResponse();
        Assert.assertEquals("sample response", response);
    }

}
