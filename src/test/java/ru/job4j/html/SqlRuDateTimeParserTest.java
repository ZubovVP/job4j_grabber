package ru.job4j.html;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $.
 * Date: 20.09.2021.
 */
public class SqlRuDateTimeParserTest {
    private final static DateTimeParser PARSER = new SqlRuDateTimeParser();

    @Test
    public void parseDateUseToday() {
        String action = "сегодня, 02:30";
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDateTime expectDateTime = LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth(), 2, 30);
        LocalDateTime result = PARSER.parse(action);
        Assert.assertEquals(expectDateTime, result);
    }

    @Test
    public void parseDateUseYesterday() {
        String action = "вчера, 02:30";
        LocalDateTime dateTime = LocalDateTime.now();
            dateTime = dateTime.minusDays(1);
        LocalDateTime expectDateTime = LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth(), 2, 30);
        LocalDateTime result = PARSER.parse(action);
        Assert.assertEquals(expectDateTime, result);
    }

    @Test
    public void parseDateUseCurrentDate() {
        String action = "22 янв 19, 02:30";
        LocalDateTime expectDateTime = LocalDateTime.of(2019, 1, 22, 2, 30);
        LocalDateTime result = PARSER.parse(action);
        Assert.assertEquals(expectDateTime, result);
    }

    @Test(expected = NullPointerException.class)
    public void parseDateIsNull() {
        PARSER.parse(null);
    }

    @Test(expected = NullPointerException.class)
    public void parseDateIsEmpty() {
        PARSER.parse("");
    }

}