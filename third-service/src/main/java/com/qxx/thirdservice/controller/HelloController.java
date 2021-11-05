package com.qxx.thirdservice.controller;

import com.qxx.thirdservice.service.TokenTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class HelloController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    TokenTaskService tokenTaskService;

    @GetMapping("/index.html")
    public String hello(String code, Model model) {
        model.addAttribute("msg", tokenTaskService.getDta(code));
        return "index";
    }

}
