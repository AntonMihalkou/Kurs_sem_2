package com.kurs.wweb.controller;

import com.kurs.wweb.model.User;
import com.kurs.wweb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.tomcat.util.codec.binary.Base64;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/login";
    }
    @PostMapping("/register")
    public String processRegistrationForm(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        userRepository.save(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
    @GetMapping("/user")
    public String user(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("username", currentUser.getUsername());
        model.addAttribute("passwordCount", currentUser.getPasswords().size());
        model.addAttribute("imageData", currentUser.getImage());
        return "user";
    }
    @PostMapping("/user")
    public String saveUser(@RequestParam("imageFile") MultipartFile file, Model model) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // конвертация изображения в base64
        byte[] imageBytes = file.getBytes();
        String imageData = Base64.encodeBase64String(imageBytes);
        currentUser.setImage(imageData);
        userRepository.save(currentUser);

        model.addAttribute("username", currentUser.getUsername());
        model.addAttribute("passwordCount", currentUser.getPasswords().size());
        model.addAttribute("imageData", imageData);

        return "user";
    }
}