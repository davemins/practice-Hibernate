package com.oreilly.hh;

import java.time.LocalTime;
import java.util.*;

import jakarta.persistence.*;

import com.oreilly.hh.data.*;

public class CreateTest {

    public static Artist getArtist(String name, boolean create, EntityManager em) {
        var query = em.createNamedQuery("com.oreilly.hh.artistByName", Artist.class);
        query.setParameter("name", name);
        //Artist found = query.getSingleResult(); --> throws RuntimeException if not exists
        Artist found = query.setMaxResults(1).getResultStream().findFirst().orElse(null);
        if (found == null && create) {
            found = new Artist(name, new HashSet<Track>());
            em.persist(found);
        }
        return found;
    }

    private static void addTrackArtist(Track track, Artist artist) {
        track.getArtists().add(artist);
    }

    public static void main(String args[]) throws Exception {

        var emf = Persistence.createEntityManagerFactory("default");
        var em  = emf.createEntityManager();
        var tx  = em.getTransaction();

        try {
            tx.begin();

            var track = new Track("Russian Trance",
                                  "vol2/album610/track02.mp3",
                                  LocalTime.of(00,03,30));
            addTrackArtist(track, getArtist("PPK", true, em));
            em.persist(track);

            track = new Track("Video Killed the Radio Star",
                              "vol2/album611/track12.mp3",
                              LocalTime.of(00,03,49));
            addTrackArtist(track, getArtist("The Buggles", true, em));
            em.persist(track);

            track = new Track("Gravity's Angel",
                              "vol2/album175/track03.mp3",
                              LocalTime.of(00,06,06));
            addTrackArtist(track, getArtist("Laurie Anderson", true, em));
            em.persist(track);

            track = new Track("Adagio for Strings (Ferry Corsten Remix)",
                              "vol2/album972/track01.mp3",
                              LocalTime.of(00,06,35));
            addTrackArtist(track, getArtist("William Orbit", true, em));
            addTrackArtist(track, getArtist("Ferry Corsten", true, em));
            addTrackArtist(track, getArtist("Samuel Barber", true, em));
            em.persist(track);

            track = new Track("Adagio for Strings (ATB Remix)",
                              "vol2/album972/track02.mp3",
                              LocalTime.of(00,07,39));
            addTrackArtist(track, getArtist("William Orbit", true, em));
            addTrackArtist(track, getArtist("ATB", true, em));
            addTrackArtist(track, getArtist("Samuel Barber", true, em));
            em.persist(track);

            track = new Track("The World '99",
                              "vol2/singles/pvw99.mp3",
                              LocalTime.of(00,07,05));
            addTrackArtist(track, getArtist("Pulp Victim", true, em));
            addTrackArtist(track, getArtist("Ferry Corsten", true, em));
            em.persist(track);

            track = new Track("Test Tone 1",
                              "vol2/singles/test01.mp3",
                              LocalTime.of(00,00,10));
            track.getComments().add("Pink noise to test equalization");
            em.persist(track);

            // We're done; make our changes permanent
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }

        emf.close();
    }
}

