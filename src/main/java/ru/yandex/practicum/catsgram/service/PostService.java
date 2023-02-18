package ru.yandex.practicum.catsgram.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.model.Post;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    /**
     * Список постов
     */
    private List<Post> posts = new ArrayList<>();

    /**
     * Логер
     */
    private static final Logger log = LoggerFactory.getLogger(PostService.class);

    public List<Post> findAll() {
        log.debug("Текущее количество постов {}", posts.size());
        return posts;
    }

    public Post create(Post post) {
        posts.add(post);
        log.debug("Добавлен пост: {}", post);
        return post;
    }
}
