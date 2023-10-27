package com.oreilly.hh.data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

@NamedQuery(
    name = "com.oreilly.hh.tracksNoLongerThan",
    query = """
            select track.id, track.title
            from Track as track
            where track.playTime <= :length
            order by track.title desc
            """)
@NamedQuery(
    name = "com.oreilly.hh.trackSummary",
    query = """
            select count(*), min(track.playTime), max(track.playTime)
            from Track as track
                inner join track.artists artist
            where artist = :artist
            """)
@NamedNativeQuery(
    name = "com.oreilly.hh.tracksEndingAt",
    query = """
            select *
            from TRACK as track
            where SECOND(track.PLAYTIME) = :seconds
            """,
    resultClass = Track.class
)

@Entity

@lombok.Data
@lombok.EqualsAndHashCode(of="id")
@lombok.ToString(exclude={"artists", "comments"})
public class Track  implements java.io.Serializable {

    @Id
    @Column(name = "TRACK_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String title;

    private String filePath;

    private LocalTime playTime = LocalTime.of(0, 0, 0);

    private LocalDate added = LocalDate.now();

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "left", column = @Column(name = "VOL_LEFT")),
        @AttributeOverride(name = "right", column = @Column(name = "VOL_RIGHT"))
    })
    private StereoVolume volume;

    @Enumerated(EnumType.STRING)
    private SourceMedia sourceMedia;

    @ElementCollection
    @JoinTable(name = "TRACK_COMMENTS",
               joinColumns = @JoinColumn(name = "TRACK_ID"))
    @Column(name = "COMMENT")
    private Set<String> comments = new HashSet<String>(0);

    @ManyToMany
    @JoinTable(name = "TRACK_ARTISTS",
               joinColumns = {@JoinColumn(name = "TRACK_ID")},
               inverseJoinColumns = {@JoinColumn(name = "ARTIST_ID")})
    private Set<Artist> artists = new HashSet<Artist>(0);

    public Track() {
    }

    public Track(String title, String filePath) {
        this.title = title;
        this.filePath = filePath;
    }

    public Track(String title, String filePath, LocalTime playTime, Set<Artist> artists, StereoVolume volume, SourceMedia sourceMedia, Set<String> comments) {
        this.title = title;
        this.filePath = filePath;
        this.playTime = playTime;
        this.artists = artists;
        this.volume = volume;
        this.sourceMedia = sourceMedia;
        this.comments = comments;
    }

}

