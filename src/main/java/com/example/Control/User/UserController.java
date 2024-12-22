package com.example.Control.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "users")
public class UserController {
    final private UserService userService;

    @Autowired
    public UserController(UserService u) {
        this.userService = u;
    }

    @PostMapping()
    public void putUser(@RequestParam(name = "name", required = true) String name, @RequestParam(name = "password", required = true) String password) {
        if (name.isEmpty() || password.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "POST_USERS_PARAM_IS_EMPTY");
        }
        userService.putUser(name, password);
    }

}