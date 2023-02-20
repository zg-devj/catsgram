package ru.yandex.practicum.catsgram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.FeedFriendsService;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/feed")
public class PostFeedController {

    private final PostService postService;

    @Autowired
    public PostFeedController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/friends")
    public List<Post> findFriendLastPost(
            @RequestBody String find
    ) {
        FeedFriendsService serviceFeed = new FeedFriendsService();
        serviceFeed.convert(find);

        if (!(serviceFeed.getSort().equals("desc") || serviceFeed.getSort().equals("asc"))) {
            throw new IllegalArgumentException();
        }
        if (serviceFeed.getSize() == null || serviceFeed.getSize() <= 0) {
            throw new IllegalArgumentException();
        }
        if (serviceFeed.getFriends().isEmpty()) {
            throw new IllegalArgumentException();
        }

        List<Post> posts = new ArrayList<>();
        for (String friendEmail : serviceFeed.getFriends()) {
            posts.addAll(postService.findAllByUserEmail(friendEmail,
                    serviceFeed.getSort(), serviceFeed.getSize()));
        }
        return posts;
    }
}
