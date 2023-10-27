package com.oreilly.hh

import jakarta.persistence.Persistence

fun main() {
    Persistence.createEntityManagerFactory("default").use { _ ->
        // do nothing
    }
}

