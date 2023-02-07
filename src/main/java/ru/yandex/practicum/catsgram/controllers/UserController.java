package ru.yandex.practicum.catsgram.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exceptions.InvalidEmailException;
import ru.yandex.practicum.catsgram.exceptions.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;

import java.util.Collection;
import java.util.HashMap;

@RestController
@RequestMapping("/users")
public class UserController {
    /**
     * Логер
     */
    private static final Logger log = LoggerFactory.getLogger(SimpleController.class);

    // Коллекция пользователей
    private HashMap<String, User> users = new HashMap<>();

    /**
     * Возвращаем коллекцию пользователей
     * GET /users
     *
     * @return Collection<User> коллекцию пользователей
     */
    @GetMapping()
    public Collection<User> findAll() {
        log.debug("Количество пользователей {}", users.size());
        return users.values();
    }

    /**
     * Создаем нового пользователя, если пользователь
     * с указанным email не существует
     * POST /users
     *
     * @param user объект пользователя
     * @return объект пользователя
     */
    @PostMapping()
    public User create(@RequestBody User user) {
        isEmailExist(user.getEmail());

        if (users.containsKey(user.getEmail())) {
            throw new UserAlreadyExistException("Пользователь с электронной почтой " +
                    user.getEmail() + " уже зарегистрирован.");
        }

        users.put(user.getEmail(), user);
        log.debug("Добавлен пользователь: {}", user);
        return user;
    }

    /**
     * Обновляем пользователя если существует, или добавляем нового если нет
     * PUT /users
     *
     * @param user объект пользователя
     * @return объект пользователя
     */
    @PutMapping
    public User update(@RequestBody User user) {
        isEmailExist(user.getEmail());
        users.put(user.getEmail(), user);
        return user;
    }

    /**
     * Проверяем, есть ли пользователь в списке
     * с такой же электронной почтой
     *
     * @param email адрес электронной почты
     */
    private void isEmailExist(String email) {
        if (email == null || email.isBlank()) {
            throw new InvalidEmailException("Адрес электронной почты не может быть пустым.");
        }
    }
}
