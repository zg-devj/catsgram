package ru.yandex.practicum.catsgram.controller;

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
    private List<Post> posts = new ArrayList<>();

    /**
     * Возвращает список постов. GET /posts
     * @return Список постов
     */
    @GetMapping("/posts")
    private List<Post> findAll() {
        return posts;
    }

    /**
     * Добавление нового поста к списку постов. POST /post
     * @param post новый пост
     * @return новый пост
     */
    @PostMapping(value = "/post")
    public Post create(@RequestBody Post post) {
        posts.add(post);
        return post;
    }
}
