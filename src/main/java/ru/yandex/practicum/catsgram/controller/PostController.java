package ru.yandex.practicum.catsgram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.List;

/**
 * Контроллер постов
 */
@RestController
public class PostController {


    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * Возвращает список постов. GET /posts
     *
     * @return Список постов
     */
    @GetMapping("/posts")
    private List<Post> findAll() {
        return postService.findAll();
    }

    /**
     * Добавление нового поста к списку постов. POST /post
     *
     * @param post новый пост
     * @return новый пост
     */
    @PostMapping(value = "/post")
    public Post create(@RequestBody Post post) {
        return postService.create(post);
    }
}