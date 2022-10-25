package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.store.PostStore;

import java.util.Collection;
import java.util.List;

@Service
@ThreadSafe
public class PostService {

    private final PostStore store;

    private final CityService cityService;

    public PostService(PostStore store, CityService cityService) {
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
