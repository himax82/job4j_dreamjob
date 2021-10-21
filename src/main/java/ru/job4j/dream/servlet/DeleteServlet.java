package ru.job4j.dream.servlet;

import ru.job4j.dream.store.MemStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class DeleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Hello");
        System.out.println(req.getParameter("id"));
            int id = Integer.parseInt(req.getParameter("id"));
            System.out.println(id);
            System.out.println(MemStore.instOf().findAllCandidates().size() + " candidates");
            System.out.println(MemStore.instOf().findByIdCandidate(id).getName());
            MemStore.instOf().deleteCandidate(id);
            File file = new File("c:\\images\\" + File.separator + id);
            System.out.println("Файл");
            System.out.println(file);
            System.out.println(file.exists());
            if (file.exists()) {
                file.delete();
            }
        System.out.println(MemStore.instOf().findAllCandidates().size() + " candidates");
        req.setAttribute("candidates", MemStore.instOf().findAllCandidates());
        req.getRequestDispatcher("candidates.jsp").forward(req, resp);
    }

}
