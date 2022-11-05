package ru.job4j.dream.controller;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.Model;
import ru.job4j.dream.model.City;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;
import ru.job4j.dream.service.CityService;
import ru.job4j.dream.service.PostService;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class PostControllerTest {
    @Test
    public void whenPosts() {
        List<Post> posts = Arrays.asList(
                new Post(1, "new post"),
                new Post(2, "new post")
        );
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        when(postService.findAll()).thenReturn(posts);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(postService, cityService);
        HttpSession httpSession = new MockHttpSession();
        String page = postController.posts(model, httpSession);
        verify(model).addAttribute("posts", posts);
        Assertions.assertEquals(page, "posts");
    }

    @Test
    public void whenCreatePost() {
        Post input = new Post(1, "new post");
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        int cityId = 1;
        when(cityService.findById(cityId)).thenReturn(new City(1, "Москва"));
        PostController postController = new PostController(postService, cityService);
        String page = postController.createPost(input, cityId);
        verify(postService).add(input);
        Assertions.assertEquals(page, "redirect:/posts");
    }

    @Test
    public void whenAddPost() {
        User user = new User(1, "Alex", "test", "test");
        List<City> cities = Arrays.asList(
                new City(1, "Москва"),
                new City(2, "Санкт-Петербург")
        );
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        HttpSession httpSession = new MockHttpSession();
        model.addAttribute("user", user);
        when(cityService.getAllCities()).thenReturn(cities);
        PostController postController = new PostController(postService, cityService);
        String page = postController.addPost(model, httpSession);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("cities", cities);
        Assertions.assertEquals(page, "addPost");
    }

    @Test
    public void whenFormUpdatePost() {
        User user = new User(1, "Alex", "test", "test");
        Post post = new Post(1, "new post");
        int postId = 1;
        List<City> cities = Arrays.asList(
                new City(1, "Москва"),
                new City(2, "Санкт-Петербург")
        );
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        model.addAttribute("user", user);
        when(postService.findById(postId)).thenReturn(post);
        when(cityService.getAllCities()).thenReturn(cities);
        PostController postController = new PostController(postService, cityService);
        String page = postController.formUpdatePost(model, postId);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("post", post);
        verify(model).addAttribute("cities", cities);
        Assertions.assertEquals(page, "updatePost");
    }

    @Test
    public void whenUpdatePost() {
        List<Post> posts = Arrays.asList(
                new Post(1, "post1", "some text", LocalDateTime.now()),
                new Post(2, "post2", "some text", LocalDateTime.now())
        );
        Post updPost = new Post(3, "new post", "some text", LocalDateTime.now());
        City city = new City(1, "Москва");
        int updId = 1;
        Model model = mock((Model.class));
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(postService, cityService);
        when(cityService.findById(city.getId())).thenReturn(city);
        doNothing().when(postService).update(isA(Post.class));
        postService.update(updPost);
        HttpSession httpSession = new MockHttpSession();
        String page = postController.updatePost(updPost, updId, httpSession, model);
        verify(cityService).findById(city.getId());
        verify(postService, times(2)).update(updPost);
        Assertions.assertEquals(page, "redirect:/posts");
    }

}