package ru.job4j.db;

import ru.job4j.model.Post;

import java.util.List;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $.
 * Date: 21.09.2021.
 */
public interface Store {
    void save(Post post);

    List<Post> getAll();

    Post findById(int id);

    void delete(int id);
}
