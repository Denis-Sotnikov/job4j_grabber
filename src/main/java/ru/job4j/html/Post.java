package ru.job4j.html;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Post {
    private String name;
    private String text;
    private String link;
    private Timestamp dateOfCreation;

    public Post() {
    }

    public Post(String name, String text, String link, Timestamp dateOfCreation) {
        this.text = text;
        this.dateOfCreation = dateOfCreation;
        this.name = name;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Timestamp dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy, HH:mm");
        return "Post{"
                + "name='"
                + name
                + '\''
                + ", text='"
                + text
                + '\''
                + ", dateOfCreation="
                + formatter.format(dateOfCreation)
                + '\''
                + ", link='"
                + link
                + '\''
                + '}';
    }
}
