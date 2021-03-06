package ru.job4j.dream.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.City;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CityServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<City> list = (List<City>) PsqlStore.instOf().findAllCity();
        ObjectMapper mapper = new ObjectMapper();
        String string = mapper.writeValueAsString(list);
        resp.setContentType("application/json; charset=utf-8");
        resp.getWriter().write(string);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<City> list = (List<City>) PsqlStore.instOf().findAllCity();
        String city = list.get(Integer.parseInt(req.getParameter("id"))).getName();
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(city);
    }
}
