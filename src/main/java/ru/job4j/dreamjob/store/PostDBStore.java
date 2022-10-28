package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostDBStore {
    private static final String FIND = "SELECT * FROM post";
    private static final String ADD = "INSERT INTO post(name, description, created, city_id) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE post SET name = (?), description = (?),   city_id = (?),  "
            + "WHERE id =  (?)";
    private static final String FINDBYID = "SELECT * FROM post WHERE id = ?";

    private static final Logger LOG = LogManager.getLogger(PostDBStore.class.getName());
    private final BasicDataSource pool;

    public PostDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    LocalDateTime localDateTime = it.getTimestamp("created").toLocalDateTime();
                    Post post = new Post(it.getInt("id"),
                            it.getString("name"), it.getString("description"),
                            localDateTime, it.getInt("ciy_id"));
                    posts = addToPosts(post);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return posts;
    }

    private List<Post> addToPosts(Post post) {
        List<Post> posts = new ArrayList<>();
        posts.add(post);
        return posts;
    }


    public Post add(Post post) {
        Timestamp timestampSQL = Timestamp.valueOf(post.getCreated());
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(ADD,
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setTimestamp(3, timestampSQL);
            ps.setInt(4, post.getCity().getId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return post;
    }

    public void update(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(UPDATE)) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getName());
            ps.setInt(3, post.getCity().getId());
            ps.setInt(4, post.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public Post findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FINDBYID);
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new Post(it.getInt("id"), it.getString("name"));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }


    public void wipeOut() throws SQLException {
        try (Connection cn = pool.getConnection();
             PreparedStatement statement = cn.prepareStatement("delete from post")) {
            statement.execute();
        }
    }

}
