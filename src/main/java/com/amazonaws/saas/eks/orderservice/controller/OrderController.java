package com.amazonaws.saas.eks.orderservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, World!";
    }
}
