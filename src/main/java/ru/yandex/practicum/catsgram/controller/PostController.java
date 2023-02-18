package ru.yandex.practicum.catsgram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.List;
import java.util.Optional;

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

    // Возвращает список постов
    @GetMapping("/posts")
    public List<Post> findAll() {
        return postService.findAll();
    }

    // Возвращаем пост по postId
    @GetMapping("/post/{postId}")
    public Post findById(@PathVariable int postId) {
        return postService.findById(postId);
    }

    // Добавление нового поста к списку постов
    @PostMapping(value = "/post")
    public Post create(@RequestBody Post post) {
        return postService.create(post);
    }
}
