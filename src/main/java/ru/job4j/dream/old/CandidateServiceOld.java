package ru.job4j.dream.old;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.service.CityService;
import ru.job4j.dream.CandidateStore;

import java.util.Collection;


public class CandidateServiceOld {
    private final CandidateStore store;
    private final CityService cityService;

    public CandidateServiceOld(CandidateStore store, CityService cityService) {
        this.store = store;
        this.cityService = cityService;
    }

    public Collection<Candidate> findAll() {
        Collection<Candidate> posts = store.findAll();
        posts.forEach(
                candidate -> candidate.setCity(
                        cityService.findById(candidate.getCity().getId())
                )
        );
        return store.findAll();
    }

    public void add(Candidate candidate) {
        store.add(candidate);
    }

    public Candidate findById(int id) {
        return store.findById(id);
    }


    public void update(Candidate candidate) {
        store.update(candidate);
    }
}
