package com.kurs.wweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Класс MainController представляет контроллер для обработки запросов на главную страницу.
 */
@Controller
public class MainController {

    /**
     * Обрабатывает GET-запрос на главную страницу и возвращает ее представление.
     * @param model Модель, используемая для передачи данных в представление.
     * @return Имя представления для главной страницы.
     */
    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }
}
