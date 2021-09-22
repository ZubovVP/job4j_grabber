package ru.job4j.db;

import org.junit.jupiter.api.*;
import ru.job4j.model.Post;

import java.time.LocalDateTime;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $.
 * Date: 22.09.2021.
 */
class PsqlStoreTest {
    private static PsqlStore store;

    @BeforeAll
    static void start() {
        if (store == null) {
            store = new PsqlStore();
        }
    }

    @AfterAll
    static void finish() {
        if (store != null) {
            store.close();
        }
    }

    @Test
    void save() {
        Assertions.assertEquals(0, store.getAll().size());
        Post post = new Post();
        post.setTitle("Title");
        post.setLink("Link");
        post.setDescription("Description");
        post.setCreated(LocalDateTime.now());
        store.save(post);
        Assertions.assertEquals(1, store.getAll().size());
        Post result = store.getAll().get(0);
        Assertions.assertEquals(post, result);
        store.delete(result.getId());
    }

    @Test
    void getAll() {
        Assertions.assertEquals(0, store.getAll().size());
    }

    @Test
    void findById() {
        Post post = new Post();
        post.setId(123);
        post.setTitle("Title");
        post.setLink("Link");
        post.setDescription("Description");
        post.setCreated(LocalDateTime.now());
        Assertions.assertFalse(store.getAll().contains(post));
        store.save(post);
        Assertions.assertEquals(post, store.findById(post.getId()));
        store.delete(post.getId());
    }

    @Test
    void delete() {
        Assertions.assertEquals(0, store.getAll().size());
        Post post = new Post();
        post.setTitle("Title");
        post.setLink("Link");
        post.setDescription("Description");
        post.setCreated(LocalDateTime.now());
        store.save(post);
        Assertions.assertEquals(1, store.getAll().size());
        Post result = store.getAll().get(0);
        Assertions.assertEquals(post, result);
        store.delete(result.getId());
    }

}