package ru.job4j;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import ru.job4j.db.PsqlStore;
import ru.job4j.db.Store;
import ru.job4j.model.Post;
import ru.job4j.pars.Parse;
import ru.job4j.pars.SqlRuDateTimeParser;
import ru.job4j.pars.SqlRuParse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $.
 * Date: 22.09.2021.
 */
public class Grabber implements Grabe {
    private static final Logger LOGGER = LoggerFactory.getLogger(Grabber.class.getName());
    private static final Properties CFG = new Properties();
    private final static Store STORE = new PsqlStore();

    public Store store() {
        return STORE;
    }

    public Scheduler scheduler() throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        return scheduler;
    }

    public void cfg() throws IOException {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("app.properties")) {
            CFG.load(in);
        }
    }

    @Override
    public void init(Parse parse, Store store, Scheduler scheduler) throws SchedulerException {
        JobDataMap data = new JobDataMap();
        data.put("store", store);
        data.put("parse", parse);
        JobDetail job = newJob(GrabJob.class)
                .usingJobData(data)
                .build();
        SimpleScheduleBuilder times = simpleSchedule()
                .withIntervalInSeconds(Integer.parseInt(CFG.getProperty("interval")))
                .repeatForever();
        Trigger trigger = newTrigger()
                .startNow()
                .withSchedule(times)
                .build();
        scheduler.scheduleJob(job, trigger);
    }

    public void web(Store store) {
        new Thread(() -> {
            try (ServerSocket server = new ServerSocket(Integer.parseInt(CFG.getProperty("port")))) {
                while (!server.isClosed()) {
                    Socket socket = server.accept();
                    try (OutputStream out = socket.getOutputStream()) {
                        out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
                        for (Post post : store.getAll()) {
                            out.write(post.toString().getBytes(Charset.forName("Windows-1251")));
                            out.write(System.lineSeparator().getBytes());
                            out.write("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------".getBytes());
                            out.write(System.lineSeparator().getBytes());
                        }
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static class GrabJob implements Job {

        @Override
        public void execute(JobExecutionContext context) {
            LOGGER.info("Started finding new posts");
            JobDataMap map = context.getJobDetail().getJobDataMap();
            Store store = (Store) map.get("store");
            Parse parse = (Parse) map.get("parse");
            List<Post> postsFromSite = parse.list(CFG.getProperty("link"));
            List<Post> postsFromDb = store.getAll();
            postsFromSite.removeAll(postsFromDb);
            postsFromSite.forEach(e -> {
                e.setDescription(parse.detail(e.getLink()).getDescription());
                store.save(e);
            });
            LOGGER.info("Finished finding new posts");
        }
    }

    public static void main(String[] args) throws Exception {
        Grabber grab = new Grabber();
        grab.cfg();
        Scheduler scheduler = grab.scheduler();
        Store store = grab.store();
        grab.init(new SqlRuParse(new SqlRuDateTimeParser()), store, scheduler);
        grab.web(store);
    }
}
