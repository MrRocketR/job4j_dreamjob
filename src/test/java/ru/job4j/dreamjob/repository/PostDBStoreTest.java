package ru.job4j.dreamjob.repository;



import ru.job4j.Main;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.AfterClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


public class PostDBStoreTest {

    private static BasicDataSource pool = new Main().loadPool();

    @AfterEach
    public void wipeTable() throws SQLException {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("TRUNCATE table POST")
        ) {
            ps.execute();
        }
    }

    @AfterClass
    public static void closeCnPool() throws SQLException {
        pool.close();
    }

    @Test
    public void whenCreatePost() {
        PostDBStore store = new PostDBStore(pool);
        Post post = new Post(0, "Java Job", "Java", LocalDateTime.now(), new City(1, "Москва"));
        store.add(post);
        Post postInDb = store.findById(post.getId());
    }

    @Test
    public void whenFindThreePosts() {
        PostDBStore store = new PostDBStore(pool);
        Post post1 = new Post(0, "Java Job 1", "Java1",
                LocalDateTime.now(), new City(1, "Москва"));
        Post post2 = new Post(0, "Java Job 2", "Java2",
                LocalDateTime.now(), new City(2, "СПБ"));
        Post post3 = new Post(0, "Java Job 3", "Java3",
                LocalDateTime.now(), new City(3, "ЕКБ"));
        store.add(post1);
        store.add(post2);
        store.add(post3);
        List<Post> posts = store.findAll();
        List<Post> expected = Arrays.asList(post1, post2, post3);
    }

    @Test
    public void whenUpdatePost() {
        PostDBStore store = new PostDBStore(pool);
        Post post = new Post(0, "Java", "Some text",
                LocalDateTime.now(), new City(1, "Москва"));
        store.add(post);
        store.update(new Post(post.getId(), post.getName(), "Changed field", post.getCreated(),
                new City(1, "Москва")));
        Post postInDb = store.findById(post.getId());
    }

}