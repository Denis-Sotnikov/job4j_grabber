package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

public class SqlRuParse {
    public Date parseDate(String date) throws ParseException {
        StringBuilder builder = new StringBuilder();
        Calendar calendar = new GregorianCalendar();
        Date ate = new Date();
        if (date.contains("сегодня") || date.contains("вчера")) {
            if (date.contains("сегодня")) {
                builder.append(calendar.get(Calendar.DAY_OF_MONTH));
                builder.append(" ");
                builder.append(calendar.get(Calendar.MONTH) + 1);
                builder.append(" ");
                builder.append(calendar.get(Calendar.YEAR));
                builder.append(", ");
                builder.append(date.split(" ")[1]);
            } else {
                if (date.contains("вчера")) {
                    long time = ate.getTime() - 86400000;
                    ate.setTime(time);
                    calendar.setTime(ate);
                    builder.append(calendar.get(Calendar.DAY_OF_MONTH));
                    builder.append(" ");
                    builder.append(calendar.get(Calendar.MONTH) + 1);
                    builder.append(" ");
                    builder.append(calendar.get(Calendar.YEAR));
                    builder.append(", ");
                    builder.append(date.split(" ")[1]);
                }
            }
        } else {
            String[] array = date.split(" ");
            String[] atta = {"янв", "фев", "мар", "апр", "мая", "июн",
                    "июл", "авг", "сен", "окт", "ноя", "дек"};
            for (int i = 0; i < atta.length; i++) {
                if (atta[i].equals(array[1])) {
                    builder.append(date
                            .replaceFirst(array[1], String.valueOf(i + 1)));
                }
            }
        }
        SimpleDateFormat format = new SimpleDateFormat("dd MM yyyy, HH:mm");
        return format.parse(builder.toString());
    }

    public static void main(String[] args) throws Exception {
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements row = doc.select(".postslisttopic");
        Elements date = doc.select(".Altcol");
        List<Element> element = date.stream()
                .filter(x -> x.text().contains(", ")).collect(Collectors.toList());
        for (int i = 0; i < row.size(); i++) {
            Element href = row.get(i).child(0);
            System.out.println(href.attr("href"));
            System.out.println(href.text());
            System.out.println(element.get(i).text());
        }
    }
}