package com.kurs.wweb.service;

import com.kurs.wweb.model.Password;
import com.kurs.wweb.model.User;
import com.kurs.wweb.repository.PasswordRepository;
import com.kurs.wweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PasswordService     {
    @Autowired
    private PasswordRepository passwordRepository;

    public PasswordService(PasswordRepository passwordRepository) {
        this.passwordRepository = passwordRepository;
    }
    public void addPassword(Password password) {
        passwordRepository.save(password);
    }

    public Password getPassword(Long id) {
        return passwordRepository.findById(id).get();
    }

    public List<Password> listPasswordsByUserId(Long userId) {
        return passwordRepository.findByUserId(userId);
    }
    public void editPassword(Password password) {
        passwordRepository.save(password);
    }

    public void deletePassword(final long id) {
        Password password = passwordRepository.findById(id).get();
        User user = password.getUser();
        user.getPasswords().remove(password);
        passwordRepository.delete(password);
    }
}