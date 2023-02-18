package ru.yandex.practicum.catsgram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.model.User;
import ru.yandex.practicum.catsgram.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Возвращаем коллекцию пользователей
     * GET /users
     *
     * @return Collection<User> коллекцию пользователей
     */
    @GetMapping()
    public Collection<User> findAll() {
        return userService.findAll();
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
        return userService.create(user);
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
        return userService.update(user);
    }


}
