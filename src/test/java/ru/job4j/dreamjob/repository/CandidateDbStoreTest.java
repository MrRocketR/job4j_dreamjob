package ru.job4j.dreamjob.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.Main;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CandidateDbStoreTest {

    private static BasicDataSource pool = new Main().loadPool();

    @AfterEach
    public void wipeTable() throws SQLException {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("TRUNCATE Table CANDIDATE")
        ) {
            ps.execute();
        }
    }

    @AfterClass
    public static void closeCnPool() throws SQLException {
        pool.close();
    }
    @Test
    public void whenCreateCandidate() {
        CandidateDbStore store = new CandidateDbStore(pool);
        Candidate candidate = new Candidate(0, "Ivan Ivanov", "Junior Java", LocalDateTime.now(),
                new City(1, "Москва"), new byte[2]);
        store.add(candidate);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getName()).isEqualTo(candidate.getName());
    }

    @Test
    public void whenFindThreeCandidate() {
        CandidateDbStore store = new CandidateDbStore(pool);
        Candidate candidate1 = new Candidate(0, "Ivan Ivanov", "Junior Java", LocalDateTime.now(),
                new City(1, "Москва"), new byte[2]);
        Candidate candidate2 = new Candidate(0, "Lisa Test", "Junior Test", LocalDateTime.now(),
                new City(2, "СПБ"), new byte[2]);
        Candidate candidate3 = new Candidate(0, "Maria Ivanov", "Junior Giga Java", LocalDateTime.now(),
                new City(3, "ЕКБ"), new byte[2]);
        store.add(candidate1);
        store.add(candidate2);
        store.add(candidate3);
        List<Candidate> candidates = store.findAll();
        List<Candidate> expected = Arrays.asList(candidate1, candidate2, candidate3);
        Assert.assertEquals(candidates, expected);
    }

    @Test
    public void whenUpdatePost() {
        CandidateDbStore store = new CandidateDbStore(pool);
        Candidate candidate = new Candidate(0, "Ivan Ivanov", "Junior Java", LocalDateTime.now(),
                new City(1, "Москва"), new byte[2]);
        store.add(candidate);
        store.update(new Candidate(candidate.getId(), candidate.getName(), "Changed field", candidate.getCreated(),
                new City(1, "Москва"), new byte[2]));
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getDescription()).isEqualTo("Changed field");
    }
}