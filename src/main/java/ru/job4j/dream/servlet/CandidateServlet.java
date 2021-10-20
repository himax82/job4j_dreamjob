package ru.job4j.dream.servlet;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.store.Store;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CandidateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("HI");
        System.out.println(req.getParameter("id"));
        if (req.getParameter("id") != null) {
            System.out.println("Зашел в цикл");
            int id = Integer.parseInt(req.getParameter("id"));
            System.out.println(id);
            String fileName = Store.instOf().findByIdCandidate(id).getImages();
            Store.instOf().deleteCandidate(id);
            File file = new File("c:\\images\\" + File.separator + fileName);
            if (file.exists()) {
                file.delete();
            }
        }
        req.setAttribute("candidates", Store.instOf().findAllCandidates());
        req.getRequestDispatcher("candidates.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Store.instOf().saveCandidate(new Candidate(Integer.parseInt(req.getParameter("id")),
                req.getParameter("name"), null));
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }
}
