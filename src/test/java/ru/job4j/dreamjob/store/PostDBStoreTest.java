package ru.job4j.dreamjob.store;


import org.junit.After;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.MainNew;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PostDBStoreTest {
    @Ignore
    @Test
    public void whenCreatePost() {
        PostDBStore store = new PostDBStore(new MainNew().loadPool());
        Post post = new Post(0, "Java Job", new City(1, "Москва"));
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName(), is(post.getName()));
    }

    @Ignore
    @Test
    public void whenUpdatePost() {
        PostDBStore store = new PostDBStore(new MainNew().loadPool());
        Post post = new Post(3, "Java Job", new City(1, "Москва"));
        Post post2 = new Post(3, "Job Java", new City(2, "СПБ"));
        store.add(post);
        store.update(post2);
        Post postInDb = store.findById(post2.getId());
        assertThat(postInDb.getName(), is(post2.getName()));
    }

    @Ignore
    @After
    public void wipeTable() throws SQLException {
        PostDBStore store = new PostDBStore(new MainNew().loadPool());
        store.wipeOut();
    }


}