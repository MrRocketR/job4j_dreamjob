package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CandidateDbStoreTest {
    private static CandidateDbStore store;
    private static BasicDataSource pool;

    @BeforeClass
    public static void initConnection() {
        pool = new Main().loadPool();
        store = new CandidateDbStore(pool);
    }

    @Test
    public void whenCreateCandidate() {
        Candidate candidate = new Candidate(0, "Ivan Ivanov", "Junior Java", LocalDateTime.now(),
                new City(1, "Москва"), new byte[2]);
        store.add(candidate);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getName(), is(candidate.getName()));
    }

    @Test
    public void whenFindThreeCandidate() {
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
        Candidate candidate = new Candidate(0, "Ivan Ivanov", "Junior Java", LocalDateTime.now(),
                new City(1, "Москва"), new byte[2]);
        store.add(candidate);
        store.update(new Candidate(candidate.getId(), candidate.getName(), "Changed field", candidate.getCreated(),
                new City(1, "Москва"), new byte[2]));
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getDescription(), is("Changed field"));
    }


    @After
    public void wipeTable() throws SQLException {
        Connection cn = pool.getConnection();
        try (PreparedStatement statement = cn.prepareStatement("TRUNCATE TABLE candidate")) {
            statement.execute();
        }
    }

}