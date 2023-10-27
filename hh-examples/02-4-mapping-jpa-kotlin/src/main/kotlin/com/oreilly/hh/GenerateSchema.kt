package com.oreilly.hh

import jakarta.persistence.Persistence

fun main() {
    var emf = Persistence.createEntityManagerFactory("genSchema")
    emf.close()
}

