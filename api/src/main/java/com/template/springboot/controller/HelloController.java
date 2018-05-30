package com.template.springboot.controller;

import com.template.springboot.model.Account;
import com.template.springboot.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    private HelloService helloService;

    @GetMapping("/hello")
    public String sayHello() {
        return "hello";
    }

    @GetMapping("/hi")
    public String sayHi() {
        return "hi";
    }

    @GetMapping("/hi/{name}")
    public Account sayHi(@PathVariable String name) {
        return helloService.getAccountByName(name);
    }
}
