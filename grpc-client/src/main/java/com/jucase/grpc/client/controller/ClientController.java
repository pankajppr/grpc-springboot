package com.jucase.grpc.client.controller;

import com.jucase.grpc.client.GreetingClient;
import com.jucase.grpc.client.UserClient;
import com.jucase.grpc.lib.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ClientController {

    @Autowired
    GreetingClient greetingClient;

    @Autowired
    UserClient userClient;

    @PostMapping("/unarygreet")
    public String greet(@RequestBody User user){
        return greetingClient.callUnaryGreet(user);
    }

    @PostMapping("/user")
    public String createUser(@RequestBody User user) throws IOException, ClassNotFoundException {
        return userClient.callUnaryUser(user);
    }
}
