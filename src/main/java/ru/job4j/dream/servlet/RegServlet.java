package ru.job4j.dream.servlet;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.User;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String email = req.getParameter("email");
        if (PsqlStore.instOf().findByEmailUser(email).getId() == 0) {
            PsqlStore.instOf().saveUser(new User(0,
                    req.getParameter("name"), req.getParameter("email"), req.getParameter("password")));
            req.getRequestDispatcher("/auth.do").forward(req, resp);
        } else {
            req.setAttribute("error", "Пользователь с таким email уже существует!");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
        }
    }
}
