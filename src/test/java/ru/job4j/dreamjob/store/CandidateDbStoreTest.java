package ru.job4j.dreamjob.store;

import org.junit.After;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.MainNew;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;

import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CandidateDbStoreTest {
    @Test
    public void whenCreateCandidate() throws SQLException {
        CandidateDbStore store = new CandidateDbStore(new MainNew().loadPool());
        Candidate candidate = new Candidate(0, "Ivan Ivanov", new City(1, "Москва"));
        store.add(candidate);
        Candidate candidateInDb = store.findById(candidate.getId());
        System.out.println(candidateInDb.getName());
        assertThat(candidateInDb.getName(), is(candidate.getName()));
        store.wipeOut();
    }

    @Test
    public void whenUpdatePost() throws SQLException {
        CandidateDbStore store = new CandidateDbStore(new MainNew().loadPool());
        Candidate candidate = new Candidate(1, "Ivan Krylov", new City(1, "Москва"));
        Candidate candidate2 = new Candidate(1, "Ylya Test", new City(2, "СПБ"));
        store.add(candidate);
        store.update(candidate2);
        Candidate candidateInDb = store.findById(candidate2.getId());
        assertThat(candidateInDb.getName(), is(candidate2.getName()));
        store.wipeOut();
    }
}