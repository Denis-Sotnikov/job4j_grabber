package ru.job4j.html;

import java.util.Date;

public class Post {
    private String text;
    private Date dateOfCreation;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    @Override
    public String toString() {
        return "Post{"
                + "text='"
                + text
                + '\''
                + ", dateOfCreation="
                + dateOfCreation
                + '}';
    }
}
