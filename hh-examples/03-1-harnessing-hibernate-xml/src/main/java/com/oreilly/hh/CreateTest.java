package com.oreilly.hh;

import java.time.LocalTime;

import org.hibernate.Transaction;

import com.oreilly.hh.data.Track;

public class CreateTest {

    public static void main(String args[]) {

        var sessionFactory = HibernateUtil5.getSessionFactory();
        var session = sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            var track = new Track("Russian Trance",
                                  "vol2/album610/track02.mp3",
                                  LocalTime.of(00,03,30));
            session.persist(track);

            track = new Track("Video Killed the Radio Star",
                              "vol2/album611/track12.mp3",
                              LocalTime.of(00,03,49));
            session.persist(track);

            track = new Track("Gravity's Angel",
                              "vol2/album175/track03.mp3",
                              LocalTime.of(00,06,06));
            session.persist(track);

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

