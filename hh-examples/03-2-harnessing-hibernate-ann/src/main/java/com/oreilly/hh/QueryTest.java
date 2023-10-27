package com.oreilly.hh;

import java.time.LocalTime;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.oreilly.hh.data.Track;

public class QueryTest {

    public static List<Track> tracksNoLongerThan(LocalTime length, Session session) {
        var query = session.createNamedQuery("com.oreilly.hh.tracksNoLongerThan", Track.class);
        query.setParameter("length", length);
        return query.getResultList();
    }

    public static void main(String args[]) {

        var sessionFactory = HibernateUtil5.getSessionFactory();
        var session = sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            var tracks = tracksNoLongerThan(LocalTime.of(00,03,40), session);
            for (Track track : tracks) {
                System.out.printf("Track: \"%s\" %s\n", track.getTitle(), track.getPlayTime());
            }

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }

        sessionFactory.close();
    }
}

