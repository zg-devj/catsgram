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

    // Возвращаем коллекцию пользователей
    @GetMapping
    public Collection<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{email}")
    public User findUserByEmail(@PathVariable String email){
        return userService.getUserByEmail(email);
    }

    // Создаем нового пользователя, если пользователь с указанным email не существует
    @PostMapping
    public User create(@RequestBody User user) {
        return userService.create(user);
    }

    // Обновляем пользователя если существует, или добавляем нового если нет
    @PutMapping
    public User update(@RequestBody User user) {
        return userService.update(user);
    }


}
