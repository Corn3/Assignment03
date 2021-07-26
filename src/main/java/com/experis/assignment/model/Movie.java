package com.experis.assignment.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.net.URL;
import java.util.ArrayList;

@Entity
@Table
@Getter
@Setter
public class Movie {

    @Id
    private long id;

    @Column
    private String title;

    @Column
    private String genre;

    @Column
    private int releaseYear;

    @Column
    private String directorName;

    @Column
    private URL picture;

    @Column
    private URL trailer;

    @ManyToMany
    @JoinTable(
            name ="title",
            joinColumns = {@JoinColumn(name = "movie_id")},
            inverseJoinColumns = {@JoinColumn(name = "character_id")}
    )
    private ArrayList<Character> characters;

    @OneToOne
    @JoinColumn(name = "franchise_id")
    private Franchise franchise;
}
