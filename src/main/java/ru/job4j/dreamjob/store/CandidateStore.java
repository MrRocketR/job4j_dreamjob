package ru.job4j.dreamjob.store;


import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CandidateStore {

    private static final CandidateStore INST = new CandidateStore();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private final AtomicInteger idGen = new AtomicInteger(0);

    private CandidateStore() {
        candidates.put(1, new Candidate(1, "Ivan",
                "Junior Java Developer", LocalDateTime.now()));
        candidates.put(2, new Candidate(2, "Anna",
                "Junior Java Developer", LocalDateTime.now()));
        candidates.put(3, new Candidate(3, "Masha",
                "Middle Java Developer", LocalDateTime.now()));
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Candidate findById(int id) {
        return candidates.get(id);
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public void update(Candidate c) {
        candidates.replace(c.getId(), c);
    }

    public void add(Candidate candidate) {
        int id = idGen.incrementAndGet();
        candidate.setId(id);
        candidates.put(candidate.getId(), candidate);
    }
}
