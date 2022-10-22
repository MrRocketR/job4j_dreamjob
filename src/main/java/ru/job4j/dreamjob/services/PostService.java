package ru.job4j.dreamjob.services;

import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.store.PostStore;

import java.util.Collection;

public class PostService {

    private final PostStore store;

    public PostService() {
        this.store = PostStore.instOf();
    }

    public void add(Post p) {
        store.add(p);
    }

    public Collection<Post> findAll() {
        return store.findAll();
    }

    public Post findById(int id) {
        return store.findById(id);
    }

    public void update(Post post) {
        store.update(post);
    }


}
