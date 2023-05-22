package com.kurs.wweb.controller;

import com.kurs.wweb.model.Password;
import com.kurs.wweb.model.User;
import com.kurs.wweb.repository.UserRepository;
import com.kurs.wweb.service.PasswordService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Класс PasswordController представляет контроллер для работы с учетными записями с паролями.
 */
@Controller
@RequestMapping("/passwords")
public class PasswordController {

    /**
     * Переменная currentUsername содержит имя текущего аутентифицированного пользователя
     */
    /**
     * Переменная passwordService представляет сервис для работы с учетными записями с паролями
     */
    private final PasswordService passwordService;

    /**
     * Переменная userRepository представляет репозиторий пользователей
     */
    private final UserRepository userRepository;

    public PasswordController(PasswordService passwordService, UserRepository userRepository) {
        this.passwordService = passwordService;
        this.userRepository = userRepository;
    }

    /**
     * Обрабатывает GET-запрос на список учетных записей с паролями для текущего пользователя.
     * @param model Модель, используемая для передачи данных в представление.
     * @return Имя представления для списка учетных записей с паролями.
     */
    @GetMapping
    public String listPasswords(final Model model) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        com.kurs.wweb.model.User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + currentUsername));
        Long userId = currentUser.getId();
        model.addAttribute("passwords", passwordService.listPasswordsByUserId(userId));
        return "passwords/list";
    }

    /**
     * Обрабатывает GET-запрос на страницу добавления новой учетной записи с паролем.
     * @return Имя представления для страницы добавления новой учетной записи с паролем.
     */
    @GetMapping("/add")
    public String showAddForm() {
        return "passwords/add";
    }

    /**
     * Обрабатывает POST-запрос на добавление новой учетной записи с паролем.
     * @param password Новая учетная запись с паролем, которую нужно добавить в базу данных.
     * @return Перенаправление на список учетных записей с паролями.
     */
    @PostMapping("/add")
    public String addPassword(@ModelAttribute final Password password) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        com.kurs.wweb.model.User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + currentUsername));
        Long userId = currentUser.getId();
        Optional<User> userOptional = userRepository.findById(userId);
        password.setUser(userOptional.get());
        passwordService.addPassword(password);
        return "redirect:/passwords";
    }

    /**
     * Обрабатывает GET-запрос на страницу редактирования учетной записи с паролем с заданным идентификатором.
     * @param id Идентификатор учетной записи с паролем, которую нужно отредактировать.
     * @param model Модель, используемая для передачи данных в представление.
     * @return Имя представления для страницы редактирования учетной записи с паролем.
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable final Long id, final Model model) {
        model.addAttribute("password", passwordService.getPassword(id));
        return "passwords/edit";
    }

    /**
     * Обрабатывает POST-запрос на редактирование учетной записи с паролем.
     * @param password Отредактированная учетная запись с паролем.
     * @return Перенаправление на список учетных записей с паролями.
     */
    @PostMapping("/edit")
    public String editPassword(@ModelAttribute final Password password) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        com.kurs.wweb.model.User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + currentUsername        ));
        Long userId = currentUser.getId();
        Optional<User> userOptional = userRepository.findById(userId);
        password.setUser(userOptional.get());
        passwordService.editPassword(password);
        return "redirect:/passwords";
    }

    /**
     * Обрабатывает GET-запрос на удаление учетной записи с паролем с заданным идентификатором.
     * @param id Идентификатор удаляемой учетной записи с паролем.
     * @return Перенаправление на список учетных записей с паролями.
     */
    @GetMapping("/delete/{id}")
    public String deletePasswords(@PathVariable final Long id) {
        passwordService.deletePassword(id);
        return "redirect:/passwords";
    }
}
