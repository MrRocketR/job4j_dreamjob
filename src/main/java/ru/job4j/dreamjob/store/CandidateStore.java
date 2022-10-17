package ru.job4j.dreamjob.store;


import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CandidateStore {

    private static final CandidateStore INST = new CandidateStore();

    private  Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private CandidateStore() {
        candidates.put(1, new Candidate(1, "Ivan",
                "Junior Java Developer", LocalDate.now()));
        candidates.put(2, new Candidate(2, "Anna",
                "Junior Java Developer", LocalDate.now()));
        candidates.put(3, new Candidate(3, "Masha",
                "Middle Java Developer", LocalDate.now()));
    }

    public void add(Candidate candidate) {
        candidates.put(candidate.getId(), candidate);
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
