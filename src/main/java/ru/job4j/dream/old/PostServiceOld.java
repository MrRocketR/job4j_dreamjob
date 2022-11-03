package ru.job4j.dream.old;

import ru.job4j.dream.model.Post;
import ru.job4j.dream.service.CityService;
import ru.job4j.dream.repository.PostStore;

import java.util.Collection;


public class PostServiceOld {

    private final PostStore store;

    private final CityService cityService;

    public PostServiceOld(PostStore store, CityService cityService) {
        this.store = store;
        this.cityService = cityService;
    }


    public Collection<Post> findAll() {
        Collection<Post> posts = store.findAll();
        posts.forEach(
                post -> post.setCity(
                        cityService.findById(post.getCity().getId())
                )
        );
        return store.findAll();
    }

    public void add(Post post) {
        store.add(post);
    }
    public Post findById(int id) {
        return store.findById(id);
    }

    public void update(Post post) {
        store.update(post);
    }


}
