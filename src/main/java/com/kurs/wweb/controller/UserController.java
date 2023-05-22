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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Контроллер для управления пользователями.
 */
@Controller
@RequiredArgsConstructor
public class UserController {
    /**
     * Эта переменная является экземпляром класса UserRepository, который используется для доступа к базе данных пользователей
     */
    private final UserRepository userRepository;
    /**
     * Эта переменная является экземпляром класса PasswordEncoder, который используется для хэширования паролей пользователей
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Отображает форму регистрации.
     * @param model модель, используемая для отображения пользовательского интерфейса.
     * @return имя представления, отображающего форму регистрации.
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    /**
     * Обрабатывает отправку формы регистрации.
     * @param user данные пользователя, отправленные из формы.
     * @return имя представления, на которое нужно перенаправить пользователя после регистрации.
     */
    @PostMapping("/register")
    public String processRegistrationForm(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        userRepository.save(user);
        return "redirect:/login";
    }

    /**
     * Отображает форму входа в систему.
     * @return имя представления, отображающего форму входа в систему.
     */
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    /**
     * Отображает информацию о текущем пользователе.
     * @param model модель, используемая для отображения пользовательского интерфейса.
     * @return имя представления, отображающего информацию о текущем пользователе.
     * @throws RuntimeException если пользователь не найден в базе данных.
     */
    @GetMapping("/user")
    public String user(final Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("username", currentUser.getUsername());
        model.addAttribute("passwordCount", currentUser.getPasswords().size());
        model.addAttribute("imageData", currentUser.getImage());
        return "user";
    }

    /**
     * Обрабатывает отправку формы с изображением пользователя.
     * @param file файл изображения, отправленный из формы.
     * @param model модель, используемая для отображения пользовательского интерфейса.
     * @return имя представления, на которое нужно перенаправить пользователя после обновления изображения.
     * @throws IOException если возникает ошибка при чтении данных изображения.
     * @throws RuntimeException если пользователь не найден в базе данных.
     */
    @PostMapping("/user")
    public String saveUser(@RequestParam("imageFile") MultipartFile file,final Model model) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        byte[] imageBytes = file.getBytes();
        String imageData = Base64.encodeBase64String(imageBytes);
        currentUser.setImage(imageData);
        userRepository.save(currentUser);
        model.addAttribute("username", currentUser.getUsername());
        model.addAttribute("passwordCount", currentUser.getPasswords().size());
        model.addAttribute("imageData", imageData);
        return "user";
    }

    /**
     * Обрабатывает запрос на выход пользователя из системы.
     * @param request HTTP-запрос, содержащий сессию, которую нужно завершить.
     * @return имя представления, на которое нужно перенаправить пользователя после выхода из системы.
     */
    @GetMapping("/logout")
    public String logout(final HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/login";
    }
}