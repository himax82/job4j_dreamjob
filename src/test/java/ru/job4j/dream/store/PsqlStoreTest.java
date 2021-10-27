package ru.job4j.dream.store;

import org.junit.Test;
import ru.job4j.dream.model.Post;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class PsqlStoreTest {

    @Test
    public void whenCreatePostNow() {
        Store store = PsqlStore.instOf();
        Post post = new Post(0, "Java Job", LocalDateTime.now());
        store.savePost(post);
        Post postInDb = store.findByIdPost(post.getId());
        assertThat(postInDb.getName(), is(post.getName()));
    }

}