package ru.job4j.pars;

import java.time.LocalDateTime;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $.
 * Date: 19.09.2021.
 */
public interface DateTimeParser {
    LocalDateTime parse(String parse);
}
