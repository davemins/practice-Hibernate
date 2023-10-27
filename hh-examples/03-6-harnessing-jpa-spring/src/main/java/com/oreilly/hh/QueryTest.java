package com.oreilly.hh;

import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.oreilly.hh.data.Track;

public class QueryTest {

    public static List<Track> tracksNoLongerThan(LocalTime length, EntityManager em) {
        var query = em.createNamedQuery("com.oreilly.hh.tracksNoLongerThan", Track.class);
        query.setParameter("length", length);
        return query.getResultList();
    }

    public static void main(String args[]) {

        var context = new AnnotationConfigApplicationContext(PersistenceConfig.class);
        //var context = new ClassPathXmlApplicationContext("applicationContext.xml");

        var emf = context.getBean(EntityManagerFactory.class);
        var em  = emf.createEntityManager();
        var tx  = em.getTransaction();

        try {
            tx.begin();

            var tracks = tracksNoLongerThan(LocalTime.of(00,07,00), em);
            for (Track track : tracks) {
                System.out.printf("Track: \"%s\" %s\n", track.getTitle(), track.getPlayTime());
            }

            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }

        emf.close();
        context.close();
    }
}

