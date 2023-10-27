package com.oreilly.hh

import java.time.LocalTime

import jakarta.persistence.EntityManager
import jakarta.persistence.Persistence

import com.oreilly.hh.data.Track

fun tracksNoLongerThan(length: LocalTime, em: EntityManager): List<Track> {
    val query = em.createNamedQuery("com.oreilly.hh.tracksNoLongerThan", Track::class.java)
    query.setParameter("length", length)
    return query.resultList
}

fun main() {
    Persistence.createEntityManagerFactory("default").use { emf ->

        emf.createEntityManager().use { em ->
            em.transaction.use {
                val tracks = tracksNoLongerThan(LocalTime.of(0, 7, 0), em)
                for (track in tracks) {
                    println("Track: '${track.title}' ${track.playTime}")
                }
            }
        }
    }
}

