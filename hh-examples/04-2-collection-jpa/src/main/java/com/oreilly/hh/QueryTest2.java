package com.oreilly.hh;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalTime;
import java.util.*;

import jakarta.persistence.*;
import javax.swing.*;

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
            var em = emf.createEntityManager();
            var tx = em.getTransaction();
            try {
                tx.begin();

                Artist artist = CreateTest.getArtist(name, false, em);
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
                tx.rollback();
                throw e;
            } finally {
                em.close();
            }
        } catch (Exception e) {
            System.err.println("Problem updating tracks:" + e);
            e.printStackTrace();
        }
    }

    private static EntityManagerFactory emf;

    public static void main(String args[]) throws Exception {

        emf = Persistence.createEntityManagerFactory("default");

        // Set up the UI
        JFrame frame = new JFrame("Artist Track Lookup");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new QueryTest2());
        frame.setSize(400, 180);
        frame.setVisible(true);
    }
}
