package ru.job4j.dream;


import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Repository
public class CandidateDbStore {

    private static final String FIND = "SELECT * FROM candidate";
    private static final String ADD = "INSERT INTO candidate(name, description, created, city_id, photo) "
            + "VALUES(?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE candidate SET name = ?, description = ?, created = ?,"
            + "city_id = ?, photo = ? where id = ?";
    private static final String FIND_BY_ID = "SELECT * FROM candidate WHERE id = ?";
    private static final Logger LOG = LogManager.getLogger(CandidateDbStore.class.getName());

    private final BasicDataSource pool;

    public CandidateDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Candidate> findAll() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND)
        ) {
            ResultSet it = ps.executeQuery();
            candidates = addToPosts(it);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return candidates;
    }

    private List<Candidate> addToPosts(ResultSet it) throws SQLException {
        List<Candidate> candidates = new ArrayList<>();
        while (it.next()) {
            candidates.add(new Candidate(it.getInt("id"),
                    it.getString("name"),
                    it.getString("description"),
                    it.getTimestamp("created").toLocalDateTime(),
                    new City(it.getInt("city_id"), ""),
                    it.getBytes("photo")));
        }
        return candidates;
    }


    public Candidate add(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(ADD,
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(candidate.getCreated()));
            ps.setInt(4, candidate.getCity().getId());
            ps.setBytes(5, candidate.getPhoto());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return candidate;
    }

    public void update(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(UPDATE)) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(candidate.getCreated()));
            ps.setInt(4, candidate.getCity().getId());
            ps.setBytes(5, candidate.getPhoto());
            ps.setInt(6, candidate.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public Candidate findById(int id) {
        Candidate candidate = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_ID);
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                candidate = constructPost(it);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return candidate;
    }


    private Candidate constructPost(ResultSet it) throws SQLException {
        if (it.next()) {
            return new Candidate(
                    it.getInt("id"),
                    it.getString("name"),
                    it.getString("description"),
                    it.getTimestamp("created").toLocalDateTime(),
                    new City(it.getInt("city_id"), ""),
                    it.getBytes("photo"));
        }
        return null;
    }

}
