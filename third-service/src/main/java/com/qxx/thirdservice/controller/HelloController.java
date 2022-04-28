package com.qxx.thirdservice.controller;

import com.qxx.thirdservice.service.ProtoTypeTest;
import com.qxx.thirdservice.service.RabbitMQService;
import com.qxx.thirdservice.service.TokenTaskService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    TokenTaskService tokenTaskService;

    @Autowired
    private ProtoTypeTest protoTypeTest;

    @Autowired
    private RabbitMQService rabbitMQService;

    @GetMapping("/index.html")
    public String hello(String code, Model model) {
        model.addAttribute("msg", tokenTaskService.getDta(code));
        return "index";
    }

    @GetMapping("/sendMessage")
    public String hello2(@RequestParam("routingKey") String routingKey,
                         @RequestParam("message") String message){
        return rabbitMQService.sendMessage(routingKey,message);
    }

    @GetMapping("/getMessage")
    public String consumer(){
        return rabbitMQService.getMessage();
    }


    public static void main(String[] args) {
        String dsl = "\'hello \'";
        System.out.println(dsl);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
