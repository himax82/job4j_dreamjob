package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.City;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.Collection;
import java.util.List;

public interface Store {
    Collection<Post> findAllPosts();
    Collection<Post> findAllPostsToDay();

    Collection<Candidate> findAllCandidates();
    Collection<Candidate> findAllCandidatesToDay();

    void savePost(Post post);

    User saveUser(User user);

    void saveCandidate(Candidate candidate);

    Post findByIdPost(int id);

    Candidate findByIdCandidate(int id);

    User findByEmailUser(String email);

    void deleteCandidate(int id);

    Collection<City> findAllCity();

    City saveCity(City city);
}
