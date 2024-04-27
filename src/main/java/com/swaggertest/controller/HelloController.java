package com.swaggertest.controller;

import com.swaggertest.entity.User;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tomtian
 * @create 2022-08-28 19:49
 * @Description
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }


    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/user")
    public User user(){

        return new User("hello","1234556");
    }


}
