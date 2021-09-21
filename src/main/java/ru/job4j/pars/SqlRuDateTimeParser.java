package ru.job4j.pars;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.AbstractMap;
import java.util.Map;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $.
 * Date: 19.09.2021.
 */
public class SqlRuDateTimeParser implements DateTimeParser {
    private static final Map<String, Month> MONTHS_MAP = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("янв", Month.JANUARY),
            new AbstractMap.SimpleEntry<>("фев", Month.FEBRUARY),
            new AbstractMap.SimpleEntry<>("мар", Month.MARCH),
            new AbstractMap.SimpleEntry<>("апр", Month.APRIL),
            new AbstractMap.SimpleEntry<>("май", Month.MAY),
            new AbstractMap.SimpleEntry<>("июн", Month.JUNE),
            new AbstractMap.SimpleEntry<>("июл", Month.JULY),
            new AbstractMap.SimpleEntry<>("авг", Month.AUGUST),
            new AbstractMap.SimpleEntry<>("сен", Month.SEPTEMBER),
            new AbstractMap.SimpleEntry<>("окт", Month.OCTOBER),
            new AbstractMap.SimpleEntry<>("ноя", Month.NOVEMBER),
            new AbstractMap.SimpleEntry<>("дек", Month.DECEMBER)
    );

    @Override
    public LocalDateTime parse(String parse) {
        LocalDateTime result;
        if (parse != null && !parse.equals("")) {
            String[] elements = parse.split("\\s+");
            if (elements.length > 2) {
                int year = Integer.parseInt(String.format("20%s", elements[2].substring(0, elements[2].length() - 1)));
                int month = MONTHS_MAP.get(elements[1]).getValue();
                int date = Integer.parseInt(elements[0]);
                int hour = Integer.parseInt(elements[3].split(":")[0]);
                int minute = Integer.parseInt(elements[3].split(":")[1]);
                result = LocalDateTime.of(year, month, date, hour, minute);
            } else {
                LocalDate localDate = null;
                int hour = Integer.parseInt(elements[1].split(":")[0]);
                int minute = Integer.parseInt(elements[1].split(":")[1]);
                String date = elements[0].split(",")[0];
                if (date.equals("вчера")) {
                    localDate = LocalDate.now().minusDays(1);
                }
                if (date.equals("сегодня")) {
                    localDate = LocalDate.now();
                }
                result = LocalDateTime.of(localDate.getYear(), localDate.getMonth(), localDate.getDayOfMonth(), hour, minute);
            }
            return result;
        }
        throw new NullPointerException("Date is null");
    }
}
