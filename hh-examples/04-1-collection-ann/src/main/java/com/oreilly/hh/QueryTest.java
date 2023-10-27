package com.oreilly.hh;

import java.time.LocalTime;
import java.util.*;

import org.hibernate.*;

import com.oreilly.hh.data.*;

public class QueryTest {

    public static List<Track> tracksNoLongerThan(LocalTime length, Session session) {
        var query = session.createNamedQuery("com.oreilly.hh.tracksNoLongerThan", Track.class);
        query.setParameter("length", length);
        return query.getResultList();
    }

    public static String listArtistNames(Set<Artist> artists) {
        var result = new StringBuilder();
        for (Artist artist : artists) {
            result.append((result.length() == 0) ? "(" : ", ");
            result.append(artist.getName());
        }
        if (result.length() > 0) {
            result.append(") ");
        }
        return result.toString();
    }

    public static void main(String args[]) throws Exception {

        var sessionFactory = HibernateUtil5.getSessionFactory();
        var session = sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            var tracks = tracksNoLongerThan(LocalTime.of(00,07,00), session);
            for (Track track : tracks) {
                System.out.printf("Track: \"%s\" %s %s\n",
                                  track.getTitle(),
                                  listArtistNames(track.getArtists()),
                                  track.getPlayTime());
                for (String comment : track.getComments()) {
                    System.out.println("  Comment: " + comment);
                }
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

