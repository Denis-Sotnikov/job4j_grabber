package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SqlRuParse implements Parse {
    private DateFormatSymbols rusSymbols = new DateFormatSymbols() {
        @Override
        public String[] getMonths() {
            return new String[]{"янв", "фев", "мар", "апр", "май", "июн",
                    "июл", "авг", "сен", "окт", "ноя", "дек"};
        }
    };

    public String changeStringDate(String date) {
        String[] array = date.toString().split(" ");
        for (int i = 0; i < rusSymbols.getMonths().length; i++) {
            if (i == Integer.parseInt(array[1])) {
                array[1] = rusSymbols.getMonths()[i + 1];
                date = Arrays.toString(array).replace("[", "")
                        .replace("]", "")
                        .replaceFirst(",", "")
                        .replaceFirst(",", "")
                        .replaceFirst(",", "");
                break;
            }
        }
        return date;
    }

    public Date parseDate(String date) throws ParseException {
        StringBuilder builder = new StringBuilder();
        Calendar calendar = new GregorianCalendar();
        Date ate = new Date();
        String dat = null;
        if (date.contains("сегодня") || date.contains("вчера")) {
            if (date.contains("сегодня")) {
                builder.append(calendar.get(Calendar.DAY_OF_MONTH));
                builder.append(" ");
                builder.append(calendar.get(Calendar.MONTH) + 1);
                builder.append(" ");
                builder.append(calendar.get(Calendar.YEAR));
                builder.append(", ");
                builder.append(date.split(" ")[1]);
                dat = builder.toString();
                dat = changeStringDate(dat);
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
                    dat = builder.toString();
                    dat = changeStringDate(dat);
                }
            }
        } else {
            String[] array = date.split(" ");
            for (int i = 0; i < rusSymbols.getMonths().length; i++) {
                if (rusSymbols.getMonths()[i].equals(array[1])) {
                    builder.append(date
                            .replaceFirst(array[1], rusSymbols.getMonths()[i]));
                }
            }
            dat = builder.toString();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy, HH:mm", rusSymbols);
        return formatter.parse(dat);
    }

    public Post parseAd(String address) throws IOException, ParseException {
        Document doc = Jsoup.connect(address).get();
        Elements row = doc.select(".msgBody");
        Elements date = doc.select(".msgFooter");
        Post post = new Post();
        post.setText(row.get(1).text());
        post.setDateOfCreation(parseDate(date.get(0).text().split(" \\[")[0]));
        return post;
    }

    @Override
    public List<Post> list(String link) throws IOException, ParseException {
            List<Post> list = new ArrayList();
            Document doc = Jsoup.connect(link).get();
            Elements row = doc.select(".postslisttopic");
            for (int j = 0; j < row.size(); j++) {
                Element href = row.get(j).child(0);
                list.add(parseAd(href.attr("href")));
            }
        return list;
    }

    @Override
    public Post detail(String link) throws IOException, ParseException {
        return parseAd(link);
    }

    public static void main(String[] args) throws Exception {
        SqlRuParse ruParse = new SqlRuParse();
//        String address = "https://www.sql.ru/forum/job-offers/";
//        for (int i = 1; i < 2; i++) {
//            String duf = address + i;
//            Document doc = Jsoup.connect(duf).get();
//            Elements row = doc.select(".postslisttopic");
//            Elements date = doc.select(".Altcol");
//            List<Element> element = date.stream()
//                    .filter(x -> x.text().contains(", ")).collect(Collectors.toList());
//            for (int j = 0; j < row.size(); j++) {
//                Element href = row.get(j).child(0);
//                System.out.println(href.attr("href"));
//                ruParse.parseAd(href.attr("href"));
//                System.out.println(href.text());
//                System.out.println(element.get(j).text());
//            }
//        }
        //System.out.println(ruParse.list("https://www.sql.ru/forum/job-offers/1").size());
        List<Post> qw = ruParse.list("https://www.sql.ru/forum/job-offers/1");
        for (Post q : qw) {
            System.out.println(q.toString());
        }
      //  System.out.println(qw.get(30).toString());
    }
}