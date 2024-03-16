package com.javarush.dao;


import com.javarush.domain.City;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CityDAOTest {

    @Mock
    private SessionFactory sessionFactory;
    @Mock
    private Session session;
    @Mock
    private Query<City> query;

    private CityDAO cityDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cityDAO = new CityDAO(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
    void testGetItems() {
        int offset = 0;
        int limit = 10;
        List<City> expectedCities = Collections.singletonList(new City());
        when(session.createQuery("select c from City c", City.class)).thenReturn(query);
        when(query.setFirstResult(offset)).thenReturn(query);
        when(query.setMaxResults(limit)).thenReturn(query);
        when(query.list()).thenReturn(expectedCities);

        List<City> actualCities = cityDAO.getItems(offset, limit);
        assertEquals(expectedCities, actualCities);
        verify(query).setFirstResult(offset);
        verify(query).setMaxResults(limit);
    }

    @Test
    void testGetTotalCount() {
        long expectedTotalCount = 5L;
        Query<Long> countQuery = mock(Query.class);
        when(session.createQuery("select count(c) from City c", Long.class)).thenReturn(countQuery);
        when(countQuery.uniqueResult()).thenReturn(expectedTotalCount);

        int actualTotalCount = cityDAO.getTotalCount();
        assertEquals(expectedTotalCount, actualTotalCount);
    }

    @Test
    void testGetById() {
        int cityId = 1;
        City expectedCity = new City();
        when(session.createQuery("select c from City c join fetch c.country where c.id = :ID", City.class)).thenReturn(query);
        when(query.setParameter("ID", cityId)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(expectedCity);

        City actualCity = cityDAO.getById(cityId);
        assertEquals(expectedCity, actualCity);
        verify(query).setParameter("ID", cityId);
    }
}
