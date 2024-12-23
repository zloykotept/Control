package com.example.Control.User;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {
    final private UserRepository userRepository;

    public void putUser(String name, String password) {
        if (userRepository.findByName(name).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "POST_USERS_EXISTS");
        }
        userRepository.save(new User(name, password.getBytes()));
    }
}
