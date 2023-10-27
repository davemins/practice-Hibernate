package com.oreilly.hh.data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;

@Entity

@lombok.Data
@lombok.EqualsAndHashCode(of="id")
@lombok.ToString(exclude={"added", "artists", "comments"})
public class Album implements java.io.Serializable {

    @Id
    @Column(name = "ALBUM_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String title;

    private Integer numDiscs;

    private LocalDate added = LocalDate.now();

    @ElementCollection
    @JoinTable(name = "ALBUM_COMMENTS",
               joinColumns = @JoinColumn(name = "ALBUM_ID"))
    @Column(name = "COMMENT")
    private Set<String> comments = new HashSet<String>(0);

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "ALBUM_ARTISTS",
               joinColumns = @JoinColumn(name = "ALBUM_ID"),
               inverseJoinColumns = @JoinColumn(name = "ARTIST_ID"))
    private Set<Artist> artists = new HashSet<Artist>(0);

    @ElementCollection
    @JoinTable(name = "ALBUM_TRACKS",
               joinColumns = @JoinColumn(name = "ALBUM_ID"))
    @OrderColumn(name = "LIST_POS")
    private List<AlbumTrack> tracks = new ArrayList<AlbumTrack>(0);

    public Album() {
    }

    public Album(String title) {
        this.title = title;
    }

    public Album(String title, Integer numDiscs, Set<Artist> artists, Set<String> comments, List<AlbumTrack> tracks) {
        this.title = title;
        this.numDiscs = numDiscs;
        this.artists = artists;
        this.comments = comments;
        this.tracks = tracks;
    }

}

