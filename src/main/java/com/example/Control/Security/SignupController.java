package com.example.Control.Security;

import com.example.Control.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "signup")
@RequiredArgsConstructor
public class SignupController {
    final private UserService userService;

    @PostMapping()
    public void putUser(@RequestParam(name = "name") String name, @RequestParam(name = "password") String password) {
        if (name.isEmpty() || password.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "POST_USERS_PARAM_IS_EMPTY");
        }
        userService.putUser(name, password);
    }

}
