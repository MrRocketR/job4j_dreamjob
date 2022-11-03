package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.AfterClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.dream.Main;
import ru.job4j.dream.repository.UserDBStore;
import ru.job4j.dream.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDBStoreTest {

    private static BasicDataSource pool = new Main().loadPool();

    @AfterEach
    public void wipeTable() throws SQLException {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("TRUNCATE table users")
        ) {
            ps.execute();
        }
    }

    @AfterClass
    public static void closeCnPool() throws SQLException {
        pool.close();
    }

    @Test
    public void whenUserAdded() {
        User user = new User(0, "Tester", "tester@gmail.com", "qwerty");
        UserDBStore store = new UserDBStore(pool);
        store.add(user);
        User userInDb = store.findUserByEmailAndPassword(user.getEmail(), user.getPassword());
        assertThat(userInDb.getEmail().equals(user.getEmail()));
    }

}
