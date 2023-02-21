package ru.yandex.practicum.catsgram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {
    /**
     * Логер
     */
    private static final Logger log = LoggerFactory.getLogger(SimpleController.class);

    /**
     * Возвращает название приложения GET /home
     *
     * @return Название приложения
     */
    @GetMapping("/home")
    public String homePage() {
        log.debug("Получен запрос");
        return "Котограм";
    }
}
