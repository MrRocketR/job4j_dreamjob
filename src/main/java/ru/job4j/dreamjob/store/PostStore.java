package ru.job4j.dreamjob.store;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
@Repository
@ThreadSafe
public class PostStore {

    private final AtomicInteger idGen = new AtomicInteger(3);

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private PostStore() {
        posts.put(1, new Post(1, "Junior Java Job", "ОАО Рога и Копыта", LocalDateTime.now()));
        posts.put(2, new Post(2, "Middle Java Job", "Xandex", LocalDateTime.now()));
        posts.put(3, new Post(3, "Senior Java Job", "Doogle", LocalDateTime.now()));
    }

    public void add(Post post) {
        post.setId(idGen.incrementAndGet());
        posts.put(post.getId(), post);
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public Post findById(int id) {
        return posts.get(id);
    }

    public void update(Post post) {
        posts.replace(post.getId(), post);
    }
}
