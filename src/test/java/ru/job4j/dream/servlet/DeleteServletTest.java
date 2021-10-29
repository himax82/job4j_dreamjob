package ru.job4j.dream.servlet;

import org.junit.Test;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.City;
import ru.job4j.dream.store.PsqlStore;
import ru.job4j.dream.store.Store;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DeleteServletTest {

    @Test
    public void whenCreateCandidate() throws IOException, ServletException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        City city = new City(1, "Moscow");
        Store store = PsqlStore.instOf();
        store.saveCity(city);
        int i = store.findAllCandidates().size();
        Candidate candidate = new Candidate(0, "Ivan", 1, LocalDateTime.now());
        store.saveCandidate(candidate);
        when(req.getParameter("id")).thenReturn(String.valueOf(candidate.getId()));
        when(req.getRequestDispatcher("candidates.jsp")).thenReturn(requestDispatcher);
        new DeleteServlet().doPost(req, resp);
        PsqlStore.instOf().deleteCandidate(candidate.getId());
        assertThat(PsqlStore.instOf().findAllCandidates().size(), is(i));
    }

}