package com.example.Control.Services;

import com.example.Control.User.User;
import com.example.Control.User.UserRepository;
import com.example.Control.User.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void putUserTest() {
        String nameMock = "KOT";
        String passwordMock = "1234";
        Optional<User> mockUser = Optional.of(new User(nameMock, passwordMock.getBytes()));

        when(userRepository.findByName(nameMock)).thenReturn(mockUser);

        assertThrows(ResponseStatusException.class, () -> userService.putUser(nameMock, passwordMock));
    }
}
