package ru.job4j.pars;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.model.Post;

import java.io.IOException;
import java.util.*;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $.
 * Date: 19.09.2021.
 */
public class SqlRuParse implements Parse {
    private final DateTimeParser parser;

    public SqlRuParse(DateTimeParser parser) {
        this.parser = parser;
    }

    @Override
    public List<Post> list(String link) {
        List<Post> result = new ArrayList<>();
        int page = Integer.parseInt(link.substring(link.length() - 1));
        if (page > 5) {
            return result;
        }
        try {
            Document doc = Jsoup.connect(link).get();
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                Post post = new Post();
                Element href = td.child(0);
                Element parent = td.parent();
                post.setLink(href.attr("href"));
                post.setTitle(href.text());
                post.setCreated(parser.parse(parent.child(5).text()));
                result.add(post);
            }
            String newLink = link.replaceFirst(String.valueOf(page), String.valueOf(page + 1));
            result.addAll(list(newLink));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Post detail(String link) {
        Post post = new Post();
        post.setLink(link);
        try {
            Document doc = Jsoup.connect(link).get();
            Element element = doc.select(".msgBody").last();
            post.setDescription(element.text());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return post;
    }

    public static void main(String[] args) {
        SqlRuParse sqlRuParse = new SqlRuParse(new SqlRuDateTimeParser());
        List<Post> posts = sqlRuParse.list("https://www.sql.ru/forum/job-offers/1");
        for (Post post : posts) {
            System.out.println(post);
        }
        System.out.println(posts.size());
    }
}
