package ru.yandex.practicum.catsgram.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exception.InvalidEmailException;
import ru.yandex.practicum.catsgram.exception.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;

import java.util.Collection;
import java.util.HashMap;

@Service
public class UserService {
    /**
     * Логер
     */
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    /**
     * Коллекция пользователей
     */
    private final HashMap<String, User> users = new HashMap<>();

    public Collection<User> findAll() {
        log.debug("Количество пользователей {}", users.size());
        return users.values();
    }

    public User create(User user) {
        isEmailExist(user.getEmail());

        if (users.containsKey(user.getEmail())) {
            throw new UserAlreadyExistException("Пользователь с электронной почтой " +
                    user.getEmail() + " уже зарегистрирован.");
        }

        users.put(user.getEmail(), user);
        log.debug("Добавлен пользователь: {}", user);
        return user;
    }

    public User update(User user) {
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
