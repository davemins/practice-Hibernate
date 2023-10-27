package exercise;

import org.hibernate.cfg.Configuration;

public class App {

    public static void main(String[] args) {
        System.out.printf("\n\nStart program:\n\n");

        var sessionFactory = new Configuration().configure().buildSessionFactory();

        // Initialize database

        System.out.printf("\n\nInitialize database:\n\n");

        sessionFactory.inTransaction( session -> {
            Node one = new Node("one");
            Node two = new Node("two");
            Node three = new Node("three");
            Node four = new Node("four");

            session.persist(one);
            session.persist(two);
            session.persist(three);
            session.persist(four);

            one.setNext(two);
            two.setNext(three);
            three.setNext(four);
        });

        // Retrieve database

        System.out.printf("\n\nRetrieve database:\n\n");

        sessionFactory.inTransaction( session -> {
            var query = session.createQuery("SELECT node FROM Node node", Node.class);
            var nodes = query.getResultList();

            for (var node : nodes) {
                System.out.println("\t" + node);
            }
        });

        sessionFactory.close();

        System.out.printf("\nGood luck.\n");
    }

}

