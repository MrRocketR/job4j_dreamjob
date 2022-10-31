package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;


@Repository
public class UserDBStore {

    private static final String ADD = "INSERT INTO users(name, email, password) VALUES(?, ?, ?)";
    private static final String FIND = "SELECT * FROM users WHERE email = ? AND password = ?";
    private static final Logger LOG = LogManager.getLogger(UserDBStore.class.getName());
    private final BasicDataSource pool;

    public UserDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<User> add(User user) {
        Optional<User> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(ADD, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                    return Optional.of(user);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception", e);
        }
        return result;
    }

    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        Optional<User> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND)
        ) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("password")
                    );
                    return Optional.of(user);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception", e);
        }
        return result;
    }
}
