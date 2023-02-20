package ru.yandex.practicum.catsgram.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FeedFriendsService {
    private String sort;
    private Integer size;
    private final List<String> friends = new ArrayList<>();


    public void convert(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Возникли проблемы при обработке (разборе, генерации) содержимого JSON: ", e);
        }
        sort = jsonNode.get("sort").asText();
        if (!(sort.equals("desc") || sort.equals("asc"))) {
            throw new IllegalArgumentException();
        }
        size = jsonNode.get("size").asInt();
        if (size == null || size <= 0) {
            throw new IllegalArgumentException();
        }
        JsonNode friendsNode = jsonNode.withArray("friends");
        Iterator<JsonNode> iterator = friendsNode.iterator();
        iterator.forEachRemaining(e -> friends.add(e.asText()));
        if (friends.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    public String getSort() {
        return sort;
    }

    public Integer getSize() {
        return size;
    }

    public List<String> getFriends() {
        return friends;
    }
}
