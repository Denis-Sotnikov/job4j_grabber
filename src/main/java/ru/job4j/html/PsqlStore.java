package ru.job4j.html;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {

    private Connection cnn;

    public PsqlStore(Properties cfg) {
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
            cnn = DriverManager.getConnection(cfg.getProperty("url"),
                    cfg.getProperty("username"), cfg.getProperty("password"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void save(Post post) {
        try (PreparedStatement statement =
                     cnn.prepareStatement("insert into grabber"
                             + " (name, text, link, date_of_created) VALUES (?, ?, ?, ?)")) {
                    statement.setString(1, post.getName());
                    statement.setString(2, post.getText());
                    statement.setString(3, post.getLink());
                    statement.setDate(4, new java.sql.Date(post.getDateOfCreation().getTime()));
                    statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> listPost = new ArrayList<>();
        try (PreparedStatement statement =
                     cnn.prepareStatement("select * from grabber")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    listPost.add(new Post(
                            resultSet.getString("name"),
                            resultSet.getString("text"),
                            resultSet.getString("link"),
                            resultSet.getTimestamp("date_of_created")
                    ));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return listPost;
    }

    @Override
    public Post findById(String id) {
        Post post = null;
        try (PreparedStatement statement =
                     cnn.prepareStatement("select * from grabber where id = ?")) {
            statement.setInt(1, Integer.parseInt(id));
            try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                            post = new Post(
                                    resultSet.getString("name"),
                                    resultSet.getString("text"),
                                    resultSet.getString("link"),
                                    resultSet.getTimestamp("date_of_created"));
                    }
                }
            } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return post;
    }

    @Override
    public void close() throws Exception {
        if (cnn != null) {
            cnn.close();
        }
    }

    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        BufferedReader reader = new BufferedReader(new FileReader("app.properties"));
        properties.load(reader);
        PsqlStore psqlStore = new PsqlStore(properties);
//        psqlStore.save(new Post("требуется спаситель",
//                "Нам требуется спаситель нашего проекта, запрлата - весь бюджет компании",
//                "yandex.ru", new java.util.Date()));
//        psqlStore.save(new Post("требуется java developer",
//                "Задача простая - работать и еще раз работать."
//                        + " А когда поработали, пора и поработать",
//                "google.com", new java.util.Date()));
//        psqlStore.save(new Post("требуется человек",
//                "В первую очередь человек",
//                                "apple.com", new java.util.Date()));
//        System.out.println(psqlStore.findById("8"));
        for (Post p : psqlStore.getAll()) {
            System.out.println(p);
        }
    }
}