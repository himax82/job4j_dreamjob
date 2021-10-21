package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

public class PsqlMain {
    public static void main(String[] args) {
        Store store = PsqlStore.instOf();
        store.saveUser(new User(0, "Max", "hhh", "kkk"));
        store.saveUser(new User(0, "Petr", "bbb", "kkk"));
        User user = store.findByEmailUser("hhh");
        System.out.println(user.getEmail() + " " + user.getPassword());
    }
}