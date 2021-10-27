package ru.job4j.dream.servlet;

import org.junit.*;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;
import ru.job4j.dream.store.PsqlStore;
import ru.job4j.dream.store.Store;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.*;
import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class PostServletTest {

    @Test
    public void whenCreatePostNow() {
        Store store = PsqlStore.instOf();
        Post post = new Post(0, "Java Job", LocalDateTime.now());
        store.savePost(post);
        Post postInDb = store.findByIdPost(post.getId());
        assertThat(postInDb.getName(), is(post.getName()));
    }

    @Ignore
    @Test
    public void whenCreatePost() throws ServletException, IOException {
        String id = "0";
        String name = "Test";
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("id")).thenReturn(id);
        when(req.getParameter("name")).thenReturn(name);
        PostServlet servlet = new PostServlet();
        int i = PsqlStore.instOf().findAllPosts().size();
        servlet.doPost(req, resp);
        PsqlStore.instOf().findAllPosts().size();
        assertThat(PsqlStore.instOf().findAllPosts().size(), is(i + 1));
    }

    @Ignore
    @Test
    public void whenGet() throws ServletException, IOException {
        String name = "post.jps";
        User user = new User(0, "user", "email", "pass");
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        HttpSession sc = mock(HttpSession.class);
        when(req.getRequestDispatcher(name)).thenReturn(dispatcher);
        when(req.getSession()).thenReturn(sc);
        PostServlet servlet = new PostServlet();
        servlet.doGet(req, resp);
    }
}