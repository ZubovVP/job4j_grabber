package ru.job4j;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import ru.job4j.db.Store;
import ru.job4j.pars.Parse;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $.
 * Date: 21.09.2021.
 */
public interface Grabe {
    void init(Parse parse, Store store, Scheduler scheduler) throws SchedulerException;
}
