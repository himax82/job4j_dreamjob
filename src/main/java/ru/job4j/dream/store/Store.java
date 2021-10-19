package ru.job4j.dream.store;

import ru.job4j.dream.model.Post;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Store {

    private static final Store INST = new Store();

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private Store() {
        posts.put(1, new Post(1, "Junior Java Job",
                "Работа в Сбербанке зп 100к", LocalDateTime.now()));
        posts.put(2, new Post(2, "Middle Java Job",
                "Работа в ВТБ зп 200к", LocalDateTime.of(2021, 10, 18, 12, 00)));
        posts.put(3, new Post(3, "Senior Java Job",
                "Работа в Сколково зп 300к", LocalDateTime.of(2021, 10, 15, 18, 15)));
    }

    public static Store instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }
}