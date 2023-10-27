package com.oreilly.hh.data;

import java.time.LocalDate;
import java.time.LocalTime;

@lombok.Data
@lombok.EqualsAndHashCode(of="id")
public class Track  implements java.io.Serializable {

    private Integer id;

    private String title;

    private String filePath;

    private LocalTime playTime = LocalTime.of(0, 0, 0);

    private LocalDate added = LocalDate.now();

    private short volume = 0;

    public Track() {
    }

    public Track(String title, String filePath, LocalTime playTime) {
        this.title = title;
        this.filePath = filePath;
        this.playTime = playTime;
    }

    public Track(String title, String filePath, LocalTime playTime, short volume) {
        this.title = title;
        this.filePath = filePath;
        this.playTime = playTime;
        this.volume = volume;
    }

}

