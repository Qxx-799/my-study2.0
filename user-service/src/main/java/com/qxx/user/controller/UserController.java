package com.qxx.user.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/user")
    public String hello(){
        return "hello , user";
    }

    @GetMapping("/admin/user")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String admin(){
        return "hello , admin !";
    }
}
