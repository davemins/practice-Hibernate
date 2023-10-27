package com.oreilly.hh;

import java.time.LocalTime;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.oreilly.hh.dao.TrackDAO;
import com.oreilly.hh.data.Track;

public class QueryTest {

    public static void main(String args[]) {

        var context = new AnnotationConfigApplicationContext(PersistenceConfig.class);
        //var context = new ClassPathXmlApplicationContext("applicationContext.xml");

        var trackDAO = context.getBean(TrackDAO.class);

        var tracks = trackDAO.tracksNoLongerThan(LocalTime.of(00,07,00));
        for (Track track : tracks) {
            System.out.printf("Track: \"%s\" %s\n", track.getTitle(), track.getPlayTime());
        }

        context.close();
    }
}

