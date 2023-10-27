package com.oreilly.hh.data;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

@NamedQuery(name = "com.oreilly.hh.artistByName",
            query = """
                    select artist
                    from Artist as artist
                    where upper(artist.name) = upper(:name)
                    """)

@Entity

@lombok.Data
@lombok.EqualsAndHashCode(of="id")
@lombok.ToString(exclude="tracks")
public class Artist implements java.io.Serializable {

    @Id
    @Column(name = "ARTIST_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "artists")      // yck
    private Set<Track> tracks = new HashSet<Track>(0);

    @ManyToOne
    @JoinColumn(name = "actualArtist")
    private Artist actualArtist;

    public Artist() {
    }

    public Artist(String name) {
        this.name = name;
    }

    public Artist(String name, Set<Track> tracks, Artist actualArtist) {
        this.name = name;
        this.tracks = tracks;
        this.actualArtist = actualArtist;
    }

}

