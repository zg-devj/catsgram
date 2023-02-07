package ru.yandex.practicum.catsgram.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.model.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер постов
 */
@RestController
public class PostController {
    /**
     * Логер
     */
    private static final Logger log = LoggerFactory.getLogger(PostController.class);

    /**
     * Список постов
     */
    private List<Post> posts = new ArrayList<>();

    /**
     * Возвращает список постов. GET /posts
     *
     * @return Список постов
     */
    @GetMapping("/posts")
    private List<Post> findAll() {
        log.debug("Текущее количество постов {}", posts.size());
        return posts;
    }

    /**
     * Добавление нового поста к списку постов. POST /post
     *
     * @param post новый пост
     * @return новый пост
     */
    @PostMapping(value = "/post")
    public Post create(@RequestBody Post post) {
        posts.add(post);
        log.debug("Добавлен пост: {}", post);
        return post;
    }
}
