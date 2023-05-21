package com.kurs.wweb.service;

import com.kurs.wweb.model.User;
import com.kurs.wweb.repository.PasswordRepository;
import com.kurs.wweb.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserDetailsServiceImplTest {

    @InjectMocks
    UserDetailsServiceImpl userService;
    @Mock
    private UserRepository userRepository;


    @Test
    public void testLoadUserByUsername() {
        String username = "username";
        User mockUser = new User();
        mockUser.setUsername(username);
        mockUser.setPassword("password");

        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));

        UserDetails result = userService.loadUserByUsername(username);

        Assert.assertEquals(username, result.getUsername());
        Assert.assertEquals("password", result.getPassword());
        Assert.assertEquals(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")), new ArrayList<>(result.getAuthorities()));
    }
}