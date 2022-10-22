package ru.job4j.dreamjob.services;

import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.CandidateStore;

import java.util.Collection;

public class CandidateService {
    private final CandidateStore store;

    public CandidateService() {
        this.store = CandidateStore.instOf();
    }

    public void add(Candidate candidate) {
        store.add(candidate);
    }

    public Candidate findById(int id) {
        return store.findById(id);
    }

    public Collection<Candidate> findAll() {
        return store.findAll();
    }

    public void update(Candidate candidate) {
        store.update(candidate);
    }
}
