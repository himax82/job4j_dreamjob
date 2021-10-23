package ru.job4j.dream.servlet;

import ru.job4j.dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("posts", PsqlStore.instOf().findAllPostsToDay());
        req.setAttribute("user", req.getSession().getAttribute("user"));
        req.setAttribute("candidates", PsqlStore.instOf().findAllCandidatesToDay());
        req.setAttribute("cities", PsqlStore.instOf().findAllCity());
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
