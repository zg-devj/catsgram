package ru.yandex.practicum.catsgram.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exception.PostNotFoundException;
import ru.yandex.practicum.catsgram.exception.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.yandex.practicum.catsgram.util.Constants.DESCENDING_ORDER;

@Service
public class PostService {
    private static final Logger log = LoggerFactory.getLogger(PostService.class);
    private int id = 0;
    //Список постов
    private final List<Post> posts = new ArrayList<>();
    private final UserService userService;

    @Autowired
    public PostService(UserService userService) {
        this.userService = userService;
        try {
            init();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // инициализация данных
    private void init() throws InterruptedException {
        userService.create(new User("puss@boots.com", "puss", LocalDate.of(2000, 01, 15)));
        for (int i = 1; i < 11; i++) {
            Post post = new Post(i, "puss@boots.com",
                    "desc" + i, "photoUrl");
            post.addDaysToCreationDate(i);
            posts.add(post);
        }

        userService.create(new User("cat@dogs.net", "cat", LocalDate.of(2001, 01, 15)));
        for (int i = 11; i < 21; i++) {
            Post post = new Post(i, "cat@dogs.net",
                    "desc" + i, "photoUrl");
            post.addDaysToCreationDate(i);
            posts.add(post);
        }

        userService.create(new User("purrr@luv.me", "purrr", LocalDate.of(2003, 01, 15)));
        for (int i = 21; i < 31; i++) {
            Post post = new Post(i, "purrr@luv.me",
                    "desc" + i, "photoUrl");
            post.addDaysToCreationDate(i);
            posts.add(post);
        }
    }

    // Возвращаем все посты
    public List<Post> findAll(String sort, Integer from, Integer size) {
        log.debug("Текущее количество постов {}", posts.size());
        return posts.stream()
                .sorted((o1, o2) -> compare(o1, o2, sort))
                .skip(from)
                .limit(size)
                .collect(Collectors.toList());
    }

    // Создаем пост
    public Post create(Post post) {
        User user = userService.findUserByEmail(post.getAuthor());
        if (user == null) {
            log.warn("Попытка создать пост с несуществующем автором. " +
                    "Пользователь {} не найден", post.getAuthor());
            throw new UserNotFoundException("Пользователь " + post.getAuthor() + " не найден");
        }
        post.setId(++id);
        posts.add(post);
        log.debug("Добавлен пост: {}", post);
        return post;
    }

    // Вернуть пост по postId
    public Post findById(int postId) {
        Optional<Post> post = posts.stream()
                .filter(x -> x.getId() == postId)
                .findFirst();
        //.orElseThrow(() -> new PostNotFoundException("Пост c №" + postId + " не найден."));
        if (post.isPresent()) {
            log.debug("Запрошен пост с id={}", postId);
            return post.get();
        } else {
            log.warn("Запрошен не существующий пост с id={}", postId);
            throw new PostNotFoundException("Пост c №" + postId + " не найден.");
        }
    }

    public List<Post> findAllByUserEmail(String friendEmail, String sort, Integer size) {
        log.debug("Последние {} поста, друга {}", size, friendEmail);
        return posts.stream()
                .filter(e -> e.getAuthor().equals(friendEmail))
                .sorted((o1, o2) -> compare(o1, o2, sort))
                .limit(size)
                .collect(Collectors.toList());
    }

    private int compare(Post p0, Post p1, String sort) {
        int compare = p0.getCreationDate().compareTo(p1.getCreationDate());
        if (sort.equals(DESCENDING_ORDER)) {
            compare = -1 * compare;
        }
        return compare;
    }
}
