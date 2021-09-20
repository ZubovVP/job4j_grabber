package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $.
 * Date: 19.09.2021.
 */
public class SqlRuParse {
    private final static DateTimeParser PARSER = new SqlRuDateTimeParser();

    public List<Post> download(String link, int page) {
        List<Post> result = new ArrayList<>();
        if (page > 5) {
            return result;
        }
        try {
            Document doc = Jsoup.connect(String.format("%s/%d", link, page)).get();
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                Post post = new Post();
                Element href = td.child(0);
                Element parent = td.parent();
                post.setLink(href.attr("href"));
                post.setTitle(href.text());
                post.setCreated(PARSER.parse(parent.child(5).text()));
                result.add(post);
            }
            result.addAll(download(link, ++page));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        SqlRuParse sqlRuParse = new SqlRuParse();
        List<Post> posts = sqlRuParse.download("https://www.sql.ru/forum/job-offers", 1);
        for (Post post : posts) {
            System.out.println(post);
        }
        System.out.println(posts.size());
    }
}
