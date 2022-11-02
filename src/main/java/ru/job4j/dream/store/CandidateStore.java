package ru.job4j.dream.store;


import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.City;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
@Repository
@ThreadSafe
public class CandidateStore {
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private final AtomicInteger idGen = new AtomicInteger(3);

    private CandidateStore() {
        candidates.put(1, new Candidate(1, "Ivan",
                "Junior Java Developer", LocalDateTime.now()));
        candidates.put(2, new Candidate(2, "Anna",
                "Junior Java Developer", LocalDateTime.now()));
        candidates.put(3, new Candidate(3, "Masha",
                "Middle Java Developer", LocalDateTime.now()));
        candidates.get(1).setCity(new City(1, "Москва"));
        candidates.get(2).setCity(new City(2, "СПБ"));
        candidates.get(3).setCity(new City(3, "ЕКБ"));
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }
    public void add(Candidate candidate) {
        candidate.setId(idGen.incrementAndGet());
        candidate.setCreated(LocalDateTime.now());
        candidates.put(candidate.getId(), candidate);
    }

    public Candidate findById(int id) {
        return candidates.get(id);
    }



    public void update(Candidate candidate) {
        candidates.replace(candidate.getId(), candidate);
    }


}
