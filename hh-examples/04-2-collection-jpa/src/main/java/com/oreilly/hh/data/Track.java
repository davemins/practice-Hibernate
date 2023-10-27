package com.oreilly.hh.data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

@NamedQuery(name = "com.oreilly.hh.tracksNoLongerThan",
            query = """
                    SELECT track
                    FROM Track AS track
                    WHERE track.playTime <= :length
                    """)

@Entity

@lombok.Data
@lombok.EqualsAndHashCode(of="id")
@lombok.ToString(exclude={"playTime", "added", "volume", "comments"})
public class Track  implements java.io.Serializable {

    @Id
    @Column(name = "TRACK_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String title;

    private String filePath;

    private LocalTime playTime = LocalTime.of(0, 0, 0);

    private LocalDate added = LocalDate.now();

    private short volume = 0;

    @ElementCollection
    @JoinTable(name = "TRACK_COMMENTS",
               joinColumns = @JoinColumn(name = "TRACK_ID"))
    @Column(name = "COMMENT")
    private Set<String> comments = new HashSet<String>();

    @ManyToMany
    @JoinTable(name = "TRACK_ARTISTS",
               joinColumns = {@JoinColumn(name = "TRACK_ID")},
               inverseJoinColumns = {@JoinColumn(name = "ARTIST_ID")})
    private Set<Artist> artists = new HashSet<Artist>();

    public Track() {
    }

    public Track(String title, String filePath, LocalTime playTime) {
        this.title = title;
        this.filePath = filePath;
        this.playTime = playTime;
    }

    public Track(String title, String filePath, LocalTime playTime, Set<Artist> artists, short volume, Set<String> comments) {
        this.title = title;
        this.filePath = filePath;
        this.playTime = playTime;
        this.artists = artists;
        this.volume = volume;
        this.comments = comments;
    }

}

