package com.example.Control.Auth;

import com.example.Control.Security.JwtService;
import com.example.Control.User.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.Control.User.User;

import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    final private UserRepository userRepository;
    final private PasswordEncoder passwordEncoder;
    final private JwtService jwtService;
    final private AuthenticationManager authenticationManager;

    public Optional<AuthResponse> register(String name, String password) {
        var user = new User(name, passwordEncoder.encode(password));

        if (userRepository.findByName(user.getName()).isPresent()) {
            return Optional.empty();
        }
        userRepository.save(user);

        String token = jwtService.generateToken(new HashMap<String, Object>() {{
            put("id", user.getId());
        }}, user);

        return Optional.of(AuthResponse.builder().token(token).build());
    }

    public Optional<AuthResponse> auth(String name, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(name, password));
        } catch (Exception e) {
            return Optional.of(AuthResponse.builder().error("LOGIN_INVALID_CREDENTIALS").build());
        }

        var userOptional = userRepository.findByName(name);
        User user;
        if (userOptional.isEmpty()) {
            return Optional.empty();
        } else {
            user = userOptional.get();
        }

        String token = jwtService.generateToken(new HashMap<String, Object>() {{
            put("id", user.getId());
        }}, user);
        return Optional.of(AuthResponse.builder().token(token).build());
    }
}
