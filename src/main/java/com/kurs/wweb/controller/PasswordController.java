package com.kurs.wweb.controller;

import com.kurs.wweb.service.PasswordService;
import com.kurs.wweb.model.Password;
import com.kurs.wweb.model.User;
import com.kurs.wweb.repository.PasswordRepository;
import com.kurs.wweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/passwords")
public class PasswordController {

    @Autowired
    private PasswordRepository passwordRepository;
    @Autowired
    private PasswordService passwordService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String listPasswords(Model model) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        com.kurs.wweb.model.User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + currentUsername));
        Long userId = currentUser.getId();
        model.addAttribute("passwords", passwordService.listPasswordsByUserId(userId));
        return "passwords/list";
    }

    @GetMapping("/add")
    public String showAddForm() {
        return "passwords/add";
    }

    @PostMapping("/add")
    public String addPassword(@ModelAttribute Password password) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        com.kurs.wweb.model.User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + currentUsername));
        Long userId = currentUser.getId();
        Optional<User> userOptional = userRepository.findById(userId);
        password.setUser(userOptional.get());
        passwordService.addPassword(password);
        return "redirect:/passwords";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("password", passwordService.getPassword(id));
        return "passwords/edit";
    }

    @PostMapping("/edit")
    public String editPassword(@ModelAttribute Password password) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        com.kurs.wweb.model.User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + currentUsername));
        Long userId = currentUser.getId();
        Optional<User> userOptional = userRepository.findById(userId);
        password.setUser(userOptional.get());
        passwordService.editPassword(password);
        return "redirect:/passwords";
    }

        @GetMapping("/delete/{id}")
        public String deletePasswords(@PathVariable Long id) {
            passwordService.deletePassword(id);
            return "redirect:/passwords";
        }
}