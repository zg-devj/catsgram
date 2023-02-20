package ru.yandex.practicum.catsgram.model;

import java.time.Instant;

/**
 * Пост, публикация
 */
public class Post {
    private Integer id;
    private final String author; // автор
    private Instant creationDate = Instant.now();  // дата создания
    private String description; // описание
    private String photoUrl; // url-адрес фотографии

    public Post(Integer id, String author, String description, String photoUrl) {
        this.id = id;
        this.author = author;
        this.description = description;
        this.photoUrl = photoUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void addDaysToCreationDate(int day) {
        creationDate = creationDate.plusSeconds(day * 24 * 60 * 60);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Override
    public String toString() {
        return "Post{" +
                "author='" + author + '\'' +
                ", creationDate=" + creationDate +
                ", description='" + description + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }
}
