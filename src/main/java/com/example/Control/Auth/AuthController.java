package com.example.Control.Auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    final private AuthService authService;

    @PostMapping(path = "/signup")
    public ResponseEntity<AuthResponse> signup(@RequestParam(name = "name") String name, @RequestParam(name = "password") String password) {
        if (name.isEmpty() || password.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "POST_USERS_PARAM_IS_EMPTY");
        }

        var responseEntityOptional = authService.register(name, password);
        if (responseEntityOptional.isPresent()) {
            var responseEntity = responseEntityOptional.get();

            ResponseCookie cookie = ResponseCookie.from("Auth", responseEntity.getToken())
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(24 * 60 * 60)
                    .sameSite("Strict")
                    .build();
            return ResponseEntity.ok().header("Set-Cookie", cookie.toString()).body(responseEntity);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(AuthResponse.builder().error("SIGNUP_CONFLICT").build());
        }
    }

    @PostMapping(path = "/login")
    public ResponseEntity<AuthResponse> login(@RequestParam(name = "name") String name, @RequestParam(name = "password") String password) {
        if (name.isEmpty() || password.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "POST_USERS_PARAM_IS_EMPTY");
        }

        var responseEntityOptional = authService.auth(name, password);
        if (responseEntityOptional.isPresent()) {
            var responseEntity = responseEntityOptional.get();
            if (responseEntity.getError() != null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseEntity);

            ResponseCookie cookie = ResponseCookie.from("Auth", responseEntity.getToken())
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(24 * 60 * 60)
                    .sameSite("Strict")
                    .build();
            return ResponseEntity.ok().header("Set-Cookie", cookie.toString()).body(responseEntity);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(AuthResponse.builder().error("LOGIN_USER_NOT_FOUND").build());
        }
    }
}
