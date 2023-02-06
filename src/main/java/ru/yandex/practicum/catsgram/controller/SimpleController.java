package ru.yandex.practicum.catsgram.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    /**
     * Возвращает название приложения GET /home
     * @return Название приложения
     */
    @GetMapping("/home")
    public String homePage() {
        return "Котограм";
    }
}
