package com.example.jwtdemo.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("portal/userInfo")
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("user")
    public void addUser(@RequestBody User user){
        logger.info("user info --> {}",user);
    }
}
