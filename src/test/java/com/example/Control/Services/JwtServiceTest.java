package com.example.Control.Services;

import com.example.Control.Security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {
    private JwtService jwtService;
    final private String jwtKey = System.getenv("JWT_SECRET");
}
