package com.oreilly.hh;

import jakarta.persistence.Persistence;

public class CreateTest {

    public static void main(String args[]) {
        var emf = Persistence.createEntityManagerFactory("default");
        emf.close();
    }
}

