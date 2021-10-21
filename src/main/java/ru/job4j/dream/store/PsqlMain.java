package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

public class PsqlMain {
    public static void main(String[] args) {
        Store store = PsqlStore.instOf();
        store.savePost(new Post(0, "Java Job"));
        store.savePost(new Post(0, "Java Junior Job"));
        for (Post post : store.findAllPosts()) {
            System.out.println(post.getId() + " " + post.getName());
        }
        store.savePost(new Post( 2,"Junior Jon"));
        for (Post post : store.findAllPosts()) {
            System.out.println(post.getId() + " " + post.getName());
        }
        Post find = store.findByIdPost(2);
        System.out.println(find.getId() + " " + find.getName());

        store.saveCandidate(new Candidate(0, "John"));
        store.saveCandidate(new Candidate(0, "Alex"));
        for (Candidate candidate : store.findAllCandidates()) {
            System.out.println(candidate.getId() + " " + candidate.getName());
        }
        store.saveCandidate(new Candidate( 2,"Ivan"));
        for (Candidate candidate : store.findAllCandidates()) {
            System.out.println(candidate.getId() + " " + candidate.getName());
        }
        Candidate candidateFin = store.findByIdCandidate(2);
        System.out.println(candidateFin.getId() + " " + candidateFin.getName());
    }
}