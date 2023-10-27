package com.oreilly.hh.data;

import java.io.Serializable;

import jakarta.persistence.*;

@Embeddable

@lombok.Data
public class AlbumTrack implements java.io.Serializable {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TRACK_ID")
    private Track track;

    private Integer disc;

    private Integer positionOnDisc;

    public AlbumTrack() {
    }

    public AlbumTrack(Track track, Integer disc, Integer positionOnDisc) {
        this.track = track;
        this.disc = disc;
        this.positionOnDisc = positionOnDisc;
    }

}

