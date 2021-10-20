package ru.job4j.dream.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        System.out.println(name);
        File downloadFile = null;
        for (File file : new File("c:\\images\\").listFiles()) {
            if (name.equals(file.getName())) {
                downloadFile = file;
                break;
            }
        }
        try (FileInputStream stream = new FileInputStream(downloadFile)){
            resp.getOutputStream().write(stream.readAllBytes());
        resp.setContentType("image/png");
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + downloadFile.getName() + "\"");
        }
    }
}