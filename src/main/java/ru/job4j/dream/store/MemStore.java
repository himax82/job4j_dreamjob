package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemStore {

    private static final MemStore INST = new MemStore();

    private static AtomicInteger POST_ID = new AtomicInteger(3);
    private static AtomicInteger CANDIDATE_ID = new AtomicInteger(3);

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private MemStore() {
        posts.put(1, new Post(1, "Junior Java Job", LocalDateTime.now()));
        posts.put(2, new Post(2, "Middle Java Job", LocalDateTime.now()));
        posts.put(3, new Post(3, "Senior Java Job", LocalDateTime.now()));
        candidates.put(1, new Candidate(1, "Junior Java", 1, LocalDateTime.now()));
        candidates.put(2, new Candidate(2, "Middle Java", 2, LocalDateTime.now()));
        candidates.put(3, new Candidate(3, "Senior Java", 3, LocalDateTime.now()));
    }

    public static MemStore instOf() {
        return INST;
    }

    public void savePost(Post post) {
        if (post.getId() == 0) {
            post.setId(POST_ID.incrementAndGet());
        }
        posts.put(post.getId(), post);
    }

    public Post findByIdPost(int id) {
        return posts.get(id);
    }

    public void saveCandidate(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(CANDIDATE_ID.incrementAndGet());
        }
        candidates.put(candidate.getId(), candidate);
    }

    public void deleteCandidate(int id) {
        if (candidates.get(id) != null) {
            candidates.remove(id, findByIdCandidate(id));
        }
    }

    public Candidate findByIdCandidate(int id) {
        return candidates.get(id);
    }

    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }
}
