package com.oreilly.hh;

public class CreateTest {

    public static void main(String args[]) {
        var sessionFactory = HibernateUtil5.getSessionFactory();
        sessionFactory.close();
    }
}

