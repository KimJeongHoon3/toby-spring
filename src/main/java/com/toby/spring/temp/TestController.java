package com.toby.spring.temp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class TestController {

    @PostMapping("/webclient/post")
    @ResponseBody
    public String test(@RequestBody String body){
        log.info("body : "+body);

        return body;
    }

    @PostMapping("/webclient/post2")
    @ResponseBody
    public String test2(@RequestBody String body) throws InterruptedException {
        log.info("body : "+body);
        Thread.sleep(6000);
        return body;
    }

    @PostMapping("/webclient/post3")
    @ResponseBody
    public String test3(@RequestBody String body) throws InterruptedException {
        log.info("body : "+body);
        Thread.sleep(2000);
        return body;
    }
}
