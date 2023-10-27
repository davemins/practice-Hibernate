package com.oreilly.hh;

import jakarta.persistence.Persistence;

public class GenerateSchema {
    public static void main(String args[]) {
        var emf = Persistence.createEntityManagerFactory("genSchema");
        emf.close();
    }
}
