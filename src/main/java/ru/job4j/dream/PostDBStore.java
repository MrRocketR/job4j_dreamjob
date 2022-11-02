package ru.job4j.dream;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.dream.model.City;
import ru.job4j.dream.model.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Repository
public class PostDBStore {
    private static final String FIND = "SELECT * FROM post";
    private static final String ADD = "INSERT INTO post(name, description, created, city_id) VALUES(?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE post SET name = ?, description = ?, created = ?, city_id = ?"
            + " where id = ?";
    private static final String FIND_BY_ID = "SELECT * FROM post WHERE id = ?";

    private static final Logger LOG = LogManager.getLogger(PostDBStore.class.getName());
    private final BasicDataSource pool;

    public PostDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND);
        ) {
            ResultSet it = ps.executeQuery();
            posts = addToPosts(it);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return posts;
    }


    private List<Post> addToPosts(ResultSet it) throws SQLException {
        List<Post> posts = new ArrayList<>();
        while (it.next()) {
            posts.add(new Post(it.getInt("id"),
                    it.getString("name"),
                    it.getString("description"),
                    it.getTimestamp("created").toLocalDateTime(),
                    new City(it.getInt("city_id"), "")));
        }
        return posts;
    }


    public Post add(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(ADD,
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(post.getCreated()));
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
            ps.setString(2, post.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(post.getCreated()));
            ps.setInt(4, post.getCity().getId());
            ps.setInt(5, post.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public Post findById(int id) {
        Post post = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_ID);
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if(it.next()) {
                    post = getPost(it);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return post;
    }

    private Post getPost(ResultSet it) throws SQLException {
            return new Post(
                    it.getInt("id"),
                    it.getString("name"),
                    it.getString("description"),
                    it.getTimestamp("created").toLocalDateTime(),
                    new City(it.getInt("city_id"), ""));
    }

}
