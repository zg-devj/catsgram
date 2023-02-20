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

@Service
public class PostService {
    private int id = 0;
    //Список постов
    private List<Post> posts = new ArrayList<>();

    private static final Logger log = LoggerFactory.getLogger(PostService.class);

    private final UserService userService;

    @Autowired
    public PostService(UserService userService) {
        this.userService = userService;
    }

    private void init() throws InterruptedException {
        userService.create(new User("user@example.com", "user", LocalDate.of(2000, 01, 15)));

        for (int i = 1; i < 13; i++) {
            Post post = new Post(i, "user@example.com",
                    "desc" + i, "photoUrl");
            post.addDaysToCreationDate(i);
            posts.add(post);
        }
    }

    // Возвращаем все посты
    public List<Post> findAll(String sort, Integer from, Integer size) {
        log.debug("Текущее количество постов {}", posts.size());
        return posts.stream()
                .sorted((o1, o2) -> {
                    int compare = o1.getCreationDate().compareTo(o2.getCreationDate());
                    if (sort.equals("desc")) {
                        compare = -1 * compare;
                    }
                    return compare;
                })
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
}
