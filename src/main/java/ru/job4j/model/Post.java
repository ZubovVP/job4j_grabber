package ru.job4j.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $.
 * Date: 20.09.2021.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private int id;
    private String title;
    private String link;
    private String description;
    private LocalDateTime created;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return Objects.equals(title, post.title) && Objects.equals(link, post.link) && Objects.equals(description, post.description) && Objects.equals(created, post.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, link, description, created);
    }
}
