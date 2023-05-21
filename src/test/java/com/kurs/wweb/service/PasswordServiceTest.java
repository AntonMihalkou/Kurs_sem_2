package com.kurs.wweb.service;



import com.kurs.wweb.model.Password;
import com.kurs.wweb.model.User;
import com.kurs.wweb.repository.PasswordRepository;
import com.kurs.wweb.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class PasswordServiceTest {

    @InjectMocks
    private PasswordService passwordService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordRepository passwordRepository;

    //Тестирование метода AddPassword
    @Test
    public void testAddPassword() {
        Password password = new Password();

        Mockito.when(passwordRepository.save(password)).thenReturn(new Password());

        passwordService.addPassword(password);

        Mockito.verify(passwordRepository, Mockito.times(1)).save(password);
    }

    //Тестирование метода getPassword
    @Test
    public void testGetPassword() {
        Long passwordId = 1L;
        Password password = new Password();
        password.setId(passwordId);
        Mockito.when(passwordRepository.findById(passwordId)).thenReturn(Optional.of(password));
        Password result = passwordService.getPassword(passwordId);
        Mockito.verify(passwordRepository, Mockito.times(1)).findById(passwordId);
        Assert.assertEquals(passwordId, result.getId());
    }

    //Тестирования метода listPasswordsByUserId(Long userId) с использованием модели Password
    @Test
    public void testListPasswordsByUserId() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        Password password1 = new Password();
        password1.setId(1L);
        password1.setUser(user);
        Password password2 = new Password();
        password2.setId(2L);
        password2.setUser(user);
        List<Password> expectedPasswords = Arrays.asList(password1, password2);
        Mockito.when(passwordRepository.findByUserId(userId)).thenReturn(expectedPasswords);
        List<Password> result = passwordService.listPasswordsByUserId(userId);
        Mockito.verify(passwordRepository, Mockito.times(1)).findByUserId(userId);
        Assert.assertEquals(expectedPasswords, result);
    }

    //Тестирование метода editPassword()
    @Test
    public void testEditPassword() {
        Long passwordId = 1L;
        String password = "password";
        Password testPassword = new Password();
        testPassword.setId(passwordId);
        testPassword.setPassword(password);
        Mockito.when(passwordRepository.save(Mockito.any(Password.class))).thenReturn(testPassword);
        passwordService.editPassword(testPassword);
        Mockito.verify(passwordRepository, Mockito.times(1)).save(testPassword);
    }

    //Тестирование метода testDeletePassword()
    @Test
    public void testDeletePassword() {
        Long passwordId = 1L;
        Password password = new Password();
        password.setId(passwordId);
        User user = new User();
        user.getPasswords().add(password);
        password.setUser(user);
        Mockito.when(passwordRepository.findById(passwordId)).thenReturn(Optional.of(password));
        passwordService.deletePassword(passwordId);
        Mockito.verify(passwordRepository, Mockito.times(1)).delete(password);
        Assert.assertFalse(user.getPasswords().contains(password));
    }
}