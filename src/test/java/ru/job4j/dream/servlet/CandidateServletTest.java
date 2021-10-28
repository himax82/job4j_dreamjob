package ru.job4j.dream.servlet;

import org.junit.Test;
import ru.job4j.dream.model.City;
import ru.job4j.dream.store.PsqlStore;
import ru.job4j.dream.store.Store;
import static org.hamcrest.core.Is.is;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CandidateServletTest {

    @Test
    public void whenCreateCandidate() throws IOException, ServletException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        int id = PsqlStore.instOf().findAllCandidates().size();
        City city = new City(1, "Moscow");
        Store store = PsqlStore.instOf();
        store.saveCity(city);
        when(req.getParameter("id")).thenReturn("0");
        when(req.getParameter("name")).thenReturn("new Candidate");
        when(req.getParameter("idCity")).thenReturn("1");
        new CandidateServlet().doPost(req, resp);
        int idAdd = PsqlStore.instOf().findAllCandidates().size();
        assertThat(idAdd, is(id + 1));
    }

}