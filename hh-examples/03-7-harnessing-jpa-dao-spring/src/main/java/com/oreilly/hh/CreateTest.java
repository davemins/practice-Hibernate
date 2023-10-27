package com.oreilly.hh;

import java.time.LocalTime;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.oreilly.hh.dao.TrackDAO;
import com.oreilly.hh.data.Track;

public class CreateTest {

    public static void main(String args[]) {

        var context = new AnnotationConfigApplicationContext(PersistenceConfig.class);
        //var context = new ClassPathXmlApplicationContext("applicationContext.xml");

        var trackDAO = context.getBean(TrackDAO.class);

        var track = new Track("Russian Trance",
                              "vol2/album610/track02.mp3",
                              LocalTime.of(00,03,30));
        trackDAO.save(track);

        track = new Track("Video Killed the Radio Star",
                          "vol2/album611/track12.mp3",
                          LocalTime.of(00,03,49));
        trackDAO.save(track);

        track = new Track("Gravity's Angel",
                          "vol2/album175/track03.mp3",
                          LocalTime.of(00,06,06));
        trackDAO.save(track);

        context.close();
    }
}

