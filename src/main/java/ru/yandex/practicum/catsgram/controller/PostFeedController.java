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
        FeedFriendsService feedFriendsService = new FeedFriendsService();
        feedFriendsService.convert(find);
        List<Post> posts = new ArrayList<>();
        for (String friendEmail : feedFriendsService.getFriends()) {
            posts.addAll(postService.findLastFriendPost(friendEmail,
                    feedFriendsService.getSort(), feedFriendsService.getSize()));
        }
        return posts;
    }
}
