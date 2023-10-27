package com.oreilly.hh;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalTime;
import java.util.*;

import javax.swing.*;

import org.hibernate.*;

import com.oreilly.hh.data.*;

public class QueryTest2 extends JPanel {

    JList<String> list;  // Will contain tracks associated with current artist
    DefaultListModel<String> model; // Lets us manipulate the list contents

    public QueryTest2() {
        setLayout(new BorderLayout());
        model = new DefaultListModel<String>();
        list = new JList<String>(model);
        add(new JScrollPane(list), BorderLayout.SOUTH);

        final JTextField artistField = new JTextField(28);
        artistField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        updateTracks(artistField.getText());
                    }
                });
            }
        });
        add(artistField, BorderLayout.EAST);
        add(new JLabel("Artist: "), BorderLayout.WEST);
    }

    private void updateTracks(String name) {
        model.removeAllElements();  // Clear out previous tracks
        if (name.length() < 1) return;   // Nothing to do

        try {
            var session = sessionFactory.openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                Artist artist = CreateTest.getArtist(name, false, session);
                if (artist == null) {  // Unknown artist
                    model.addElement("Artist not found");
                } else {                                // yckim
                    // List the tracks associated with the artist
                    for (Track track : artist.getTracks()) {
                        model.addElement(
                            String.format("Track: \"%s\" %s\n", track.getTitle(), track.getPlayTime())
                        );
                    }
                }

                tx.commit();
            } catch (RuntimeException e) {
                if (tx != null) tx.rollback();
                throw e;
            } finally {
                session.close();
            }
        } catch (Exception e) {
            System.err.println("Problem updating tracks:" + e);
            e.printStackTrace();
        }
    }

    private static SessionFactory sessionFactory;

    public static void main(String args[]) throws Exception {

        sessionFactory = HibernateUtil5.getSessionFactory();

        // Set up the UI
        JFrame frame = new JFrame("Artist Track Lookup");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new QueryTest2());
        frame.setSize(400, 180);
        frame.setVisible(true);
    }
}
