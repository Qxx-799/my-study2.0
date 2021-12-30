package com.qxx.user.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(value = "*")
public class UserController {

    @GetMapping("/user")
    public String hello(){
        return "hello , user";
    }

    @GetMapping("/admin/user")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String admin(HttpServletRequest request){
        return "hello , admin !";
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode("123456");
        System.out.println(encode);
    }


}
