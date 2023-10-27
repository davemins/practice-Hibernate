package com.oreilly.hh.dao.hibernate;

import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oreilly.hh.dao.TrackDAO;
import com.oreilly.hh.data.Track;

@Repository
@Transactional
public class TrackHibernateDAO implements TrackDAO {

    @PersistenceContext
    private EntityManager em;

    public Track save(Track track) {
        em.persist(track);
        return track;
    }

    public void delete(Track track) {
        em.remove(track);
    }

    public Track findById(int id) {
        return em.find(Track.class, id);
    }

    public List<Track> findAll() {
        var hql = "SELECT t FROM Track t";
        return em.createQuery(hql, Track.class)
                 .getResultList();
    }

    public List<Track> findByNameLike(final String namePattern) {
        var hql = "SELECT t FROM Track t WHERE name LIKE :name";
        return em.createQuery(hql, Track.class)
                 .setParameter("name", namePattern)
                 .getResultList();
    }

    public List<Track> tracksNoLongerThan(LocalTime length) {
        return em.createNamedQuery("com.oreilly.hh.tracksNoLongerThan", Track.class)
                 .setParameter("length", length)
                 .getResultList();
    }

}

