package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.City;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.*;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class PsqlStore implements Store {

    private static final Logger LOG = LogManager.getLogger(PsqlStore.class.getName());
    private final BasicDataSource pool = new BasicDataSource();

    private PsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new FileReader("src\\test\\resources\\db.properties")
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public Collection<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM post")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(it.getInt("id"), it.getString("name"),
                            it.getTimestamp("created").toLocalDateTime()));
                }
            }
        } catch (SQLException e) {
            LOG.error("SQL Error " + e.getMessage(), e);
        }
        return posts;
    }

    @Override
    public Collection<Post> findAllPostsToDay() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM post WHERE created >= NOW() - '1 day'::INTERVAL")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(it.getInt("id"), it.getString("name"),
                            it.getTimestamp("created").toLocalDateTime()));
                }
            }
        } catch (SQLException e) {
            LOG.error("SQL Error " + e.getMessage(), e);
        }
        return posts;
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM candidate")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(new Candidate(it.getInt("id"), it.getString("name"),
                            it.getInt("cityId"), it.getTimestamp("created").toLocalDateTime()));
                }
            }
        }  catch (SQLException e) {
            LOG.error("SQL Error " + e.getMessage(), e);
        }
        return candidates;
    }

    @Override
    public Collection<Candidate> findAllCandidatesToDay() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM candidate WHERE created >= NOW() - '1 day'::INTERVAL")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(new Candidate(it.getInt("id"), it.getString("name"),
                            it.getInt("cityId"), it.getTimestamp("created").toLocalDateTime()));
                }
            }
        }  catch (SQLException e) {
            LOG.error("SQL Error " + e.getMessage(), e);
        }
        return candidates;
    }

    @Override
    public void savePost(Post post) {
        if (post.getId() == 0) {
            createPost(post);
        } else {
            updatePost(post);
        }
    }

    @Override
    public void saveCandidate(Candidate candidate) {
        if (candidate.getId() == 0) {
            createCandidate(candidate);
        } else {
            updateCandidate(candidate);
        }
    }

    @Override
    public User saveUser(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("INSERT INTO users(name, email, password) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
        }  catch (SQLException e) {
            LOG.error("SQL Error " + e.getMessage(), e);
        }
        return user;
    }

    private Post createPost(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("INSERT INTO post(name, created) VALUES (?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.setTimestamp(2, Timestamp.valueOf(post.getCreated()));
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        }  catch (SQLException e) {
            LOG.error("SQL Error " + e.getMessage(), e);
        }
        return post;
    }

    private Candidate createCandidate(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("INSERT INTO candidate(name, cityid, created) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
            ps.setInt(2, candidate.getCityId());
            ps.setTimestamp(3, Timestamp.valueOf(candidate.getCreated()));
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidate;
    }

    private void updatePost(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("UPDATE post SET name = ? WHERE id = ?")
        ) {
            ps.setString(1, post.getName());
            ps.setInt(2, post.getId());
            ps.execute();
        }  catch (SQLException e) {
            LOG.error("SQL Error " + e.getMessage(), e);
        }
    }

    private void updateCandidate(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("UPDATE candidate SET name = ? cityid = ? WHERE id = ?")
        ) {
            ps.setString(1, candidate.getName());
            ps.setInt(2, candidate.getCityId());
            ps.setInt(3, candidate.getId());
            ps.execute();
        }  catch (SQLException e) {
            LOG.error("SQL Error " + e.getMessage(), e);
        }
    }

    @Override
    public Post findByIdPost(int id) {
        Post post = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM post WHERE id = ?")
        ) {
            ps.setInt(1, id);
            ps.execute();
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    post = new Post(id, it.getString("name"), it.getTimestamp("created").toLocalDateTime());
                }
            }
        }  catch (SQLException e) {
            LOG.error("SQL Error " + e.getMessage(), e);
        }
        return post;
    }

    @Override
    public Candidate findByIdCandidate(int id) {
        Candidate candidate = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM candidate WHERE id = ?")
        ) {
            ps.setInt(1, id);
            ps.execute();
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    candidate = new Candidate(id, it.getString("name"),
                            it.getInt("cityId"), it.getTimestamp("created").toLocalDateTime());
                }
            }
        }  catch (SQLException e) {
            LOG.error("SQL Error " + e.getMessage(), e);
        }
        return candidate;
    }

    @Override
    public User findByEmailUser(String email) {
        User user = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM users WHERE email = ?")
        ) {
            ps.setString(1, email);
            ps.execute();
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    user = new User(
                    it.getInt("id"),
                    it.getString("name"),
                    email,
                    it.getString("password"));
                }
            }
        }  catch (SQLException e) {
            LOG.error("SQL Error " + e.getMessage(), e);
        }
        return user;
    }

    public void deleteCandidate(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("delete FROM candidate WHERE id = ?")
        ) {
            ps.setInt(1, id);
            ps.execute();
        }  catch (SQLException e) {
            LOG.error("SQL Error " + e.getMessage(), e);
        }
    }

    public Collection<City> findAllCity() {
        List<City> cities = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM city")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    cities.add(new City(it.getInt("id"), it.getString("name")));
                }
            }
        }  catch (SQLException e) {
            LOG.error("SQL Error " + e.getMessage(), e);
        }
        return cities;
    }
}