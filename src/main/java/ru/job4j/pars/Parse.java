package ru.job4j.pars;

import ru.job4j.model.Post;

import java.util.List;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $.
 * Date: 20.09.2021.
 */
public interface Parse {
    List<Post> list(String link);

    Post detail(String link);
}
