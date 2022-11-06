package ru.job4j.dreamjob.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.AfterClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.job4j.Main;
import ru.job4j.dreamjob.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

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
        User userInDb = store.findUserByEmailAndPassword(user.getEmail(), user.getPassword()).get();
        Assertions.assertEquals(userInDb.getEmail(), user.getEmail());
    }

    @Test
    public void whenUserAlreadySame() {
        User user = new User(0, "Tester", "tester@gmail.com", "qwerty");
        User user2 = new User(0, "Tester", "tester@gmail.com", "qwerty");
        UserDBStore store = new UserDBStore(pool);
        store.add(user);
        Optional<User> userInDb = store.add(user2);
        Assertions.assertTrue(userInDb.isEmpty());
    }

}
