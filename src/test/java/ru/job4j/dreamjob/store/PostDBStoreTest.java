package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PostDBStoreTest {
    private static PostDBStore store;
    private static BasicDataSource pool;

    @BeforeClass
    public static void initConnection() {
        pool = new Main().loadPool();
        store = new PostDBStore(pool);
    }

    @Test
    public void whenCreatePost() {
        Post post = new Post(0, "Java Job", "Java", LocalDateTime.now(), new City(1, "Москва"));
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName(), is(post.getName()));
    }

    @Test
    public void whenFindThreePosts() {
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
        Assert.assertEquals(posts, expected);
    }

    @Test
    public void whenUpdatePost() {
        Post post = new Post(0, "Java", "Some text",
                LocalDateTime.now(), new City(1, "Москва"));
        store.add(post);
        store.update(new Post(post.getId(), post.getName(), "Changed field", post.getCreated(),
                new City(1, "Москва")));
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getDescription(), is("Changed field"));
    }


    @After
    public void wipeTable() throws SQLException {
        Connection cn = pool.getConnection();
        try (PreparedStatement statement = cn.prepareStatement("TRUNCATE TABLE post")) {
            statement.execute();
        }
    }

}