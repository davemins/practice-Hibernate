package com.oreilly.hh;

import org.hibernate.*;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;               // assertTrue, assertThat

import com.oreilly.hh.data.Artist;

public class ArtistTest {

    protected Session session;
    protected static SessionFactory sessionFactory;

    @BeforeAll
    public static void startTestSystem() throws Exception {
        sessionFactory = HibernateUtil5.getSessionFactory();
    }

    @AfterAll
    public static void stopTestSystem() throws Exception {
        sessionFactory.close();
    }

    @BeforeEach
    public void initBeforeEachTest() throws Exception {
        session = sessionFactory.openSession();
    }

    @AfterEach
    public void cleanAfterEachTest() throws Exception {
        session.close();
    }

    @Test
    public void testSave() throws Exception {
        Transaction tx = null;
        try {
            // Create some data and persist it
            tx = session.beginTransaction();

            Artist a = new Artist();
            a.setName("Jim Elliott's Funk Band");
            assertNull(a.getId());

            //a = (Artist) session.merge(a);
            session.persist(a);
            assertNotNull(a.getId());

            // We're done; make our changes permanent
            tx.commit();

        } catch (Exception e) {
            if (tx != null) {
                // Something went wrong; discard all partial changes
                tx.rollback();
            }
            throw new Exception("Transaction failed", e);
        }
    }
}
