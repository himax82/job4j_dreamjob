package ru.job4j.dream.servlet;

import ru.job4j.dream.store.MemStore;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class DeleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            int id = Integer.parseInt(req.getParameter("id"));
            PsqlStore.instOf().deleteCandidate(id);
            File file = new File("c:\\images\\" + File.separator + id);
            System.out.println("Файл");
            System.out.println(file);
            System.out.println(file.exists());
            if (file.exists()) {
                file.delete();
            }
        System.out.println(PsqlStore.instOf().findAllCandidates().size() + " candidates");
        req.setAttribute("candidates", PsqlStore.instOf().findAllCandidates());
        req.getRequestDispatcher("candidates.jsp").forward(req, resp);
    }

}
