package com.qxx.thirdservice.controller;

import com.qxx.thirdservice.service.ProtoTypeTest;
import com.qxx.thirdservice.service.TokenTaskService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/index.html")
    public String hello(String code, Model model) {
        model.addAttribute("msg", tokenTaskService.getDta(code));
        return "index";
    }

    @GetMapping("test")
    public String hello2(){
        Integer i = protoTypeTest.getI();
        protoTypeTest.setI(i+1);
        return protoTypeTest.getI().toString();
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
