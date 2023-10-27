package com.oreilly.hh;

import java.time.LocalTime;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.oreilly.hh.data.Track;

public class CreateTest {

    public static void main(String args[]) {

        var context = new AnnotationConfigApplicationContext(PersistenceConfig.class);
        //var context = new ClassPathXmlApplicationContext("applicationContext.xml");

        var emf = context.getBean(EntityManagerFactory.class);
        var em  = emf.createEntityManager();
        var tx  = em.getTransaction();

        try {
            tx.begin();

            var track = new Track("Russian Trance",
                                  "vol2/album610/track02.mp3",
                                  LocalTime.of(00,03,30));
            em.persist(track);

            track = new Track("Video Killed the Radio Star",
                              "vol2/album611/track12.mp3",
                              LocalTime.of(00,03,49));
            em.persist(track);

            track = new Track("Gravity's Angel",
                              "vol2/album175/track03.mp3",
                              LocalTime.of(00,06,06));
            em.persist(track);

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

