package com.javarush.dao;

import com.javarush.domain.Country;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CountryDAOTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Query<Country> query;

    @InjectMocks
    private CountryDAO countryDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        List<Country> countries = new ArrayList<>();
        countries.add(new Country());
        countries.add(new Country());

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(anyString(), eq(Country.class))).thenReturn(query);
        when(query.list()).thenReturn(countries);

        List<Country> result = countryDAO.getAll();

        assertEquals(countries.size(), result.size());
        verify(sessionFactory).getCurrentSession();
        verify(session).createQuery(anyString(), eq(Country.class));
        verify(query).list();
    }
}
