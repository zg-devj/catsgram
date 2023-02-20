package ru.yandex.practicum.catsgram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    // Возвращает список постов
    @GetMapping("/posts")
    public List<Post> findAll(
            @RequestParam(name = "sort", defaultValue = "desc") String sort,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size
    ) {
        if (!(sort.equals("asc") || sort.equals("desc"))) {
            throw new IllegalArgumentException();
        }
        if (page < 1 || size <= 0) {
            throw new IllegalArgumentException();
        }

        Integer from = (page - 1) * size;
        return postService.findAll(sort, from, size);
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
