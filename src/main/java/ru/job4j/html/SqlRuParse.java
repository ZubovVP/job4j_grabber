package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDateTime;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $.
 * Date: 19.09.2021.
 */
public class SqlRuParse {
    public static void main(String[] args) throws Exception {
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements row = doc.select(".postslisttopic");
        DateTimeParser dateTimeParser = new SqlRuDateTimeParser();
        for (Element td : row) {
            Element href = td.child(0);
            Element parent = td.parent();
            System.out.println(href.attr("href"));
            System.out.println(href.text());
            LocalDateTime dateTime = dateTimeParser.parse(parent.child(5).text());
            System.out.println("Дата публикации : " + dateTime.toString());
            System.out.println("-----------------------------------------------------");
        }
    }
}
