package com.qxx.thirdservice.controller;

import com.qxx.thirdservice.service.DeadLetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rabbit")
public class RabbitMQController {

    @Autowired
    private DeadLetterService deadLetterService;

    @GetMapping("/publish")
    public String publish() throws InterruptedException {
        return deadLetterService.producer();
    }

    @GetMapping("/getMsg")
    public String consumer(){
        return deadLetterService.getMsg();
    }
}
