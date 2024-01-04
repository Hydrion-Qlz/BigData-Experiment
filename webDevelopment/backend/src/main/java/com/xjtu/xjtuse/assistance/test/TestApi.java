package com.xjtu.xjtuse.assistance.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestApi {

    @GetMapping("/users")
    public Integer getUserById(Integer id) {
        return id;
    }

    @PostMapping("/postTest")
    public String postTest(@RequestBody String name){
        System.out.println(name);
        return "success " + name;
    }

    @PostMapping("addUser")
    public User addUser(@RequestBody User user){
        System.out.println(user);
        return user;
    }
}
