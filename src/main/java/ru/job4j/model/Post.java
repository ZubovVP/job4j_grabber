package ru.job4j.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String title;
    @Column(name = "link")
    private String link;
    @Column(name = "text")
    private String description;
    @Column(name = "created")
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
        return Objects.equals(title, post.title) && Objects.equals(link, post.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, link);
    }
}
