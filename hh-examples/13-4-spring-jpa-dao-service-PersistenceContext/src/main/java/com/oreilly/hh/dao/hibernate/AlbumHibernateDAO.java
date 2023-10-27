package com.oreilly.hh.dao.hibernate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oreilly.hh.dao.AlbumDAO;
import com.oreilly.hh.data.Album;

@Repository
@Transactional
public class AlbumHibernateDAO implements AlbumDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public Album persist(Album album) {
        entityManager.persist(album);
        return album;
    }

    public void delete(Album album) {
        entityManager.remove(album);
    }

}
