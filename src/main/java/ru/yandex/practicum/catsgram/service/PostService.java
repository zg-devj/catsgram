package ru.yandex.practicum.catsgram.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exception.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    //Список постов
    private List<Post> posts = new ArrayList<>();

    private static final Logger log = LoggerFactory.getLogger(PostService.class);

    private final UserService userService;

    @Autowired
    public PostService(UserService userService) {
        this.userService = userService;
    }

    // Возвращаем все посты
    public List<Post> findAll() {
        log.debug("Текущее количество постов {}", posts.size());
        return posts;
    }

    // Создаем пост
    public Post create(Post post) {
        User user = userService.findUserByEmail(post.getAuthor());
        if (user == null) {
            log.warn("Попытка создать пост с несуществующем автором. " +
                    "Пользователь {} не найден", post.getAuthor());
            throw new UserNotFoundException("Пользователь " + post.getAuthor() + " не найден");
        }
        posts.add(post);
        log.debug("Добавлен пост: {}", post);
        return post;
    }
}
