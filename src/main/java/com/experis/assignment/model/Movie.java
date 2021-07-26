package com.experis.assignment.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "release_year")
    private int releaseYear;

    @Column(name = "director_name")
    private String directorName;

    @Column
    private URL picture;

    @Column
    private URL trailer;

    @ManyToMany
    @JoinTable(
            name ="movie_character",
            joinColumns = {@JoinColumn(name = "movie_id")},
            inverseJoinColumns = {@JoinColumn(name = "character_id")}
    )
    private List<Character> characters;

    @OneToOne
    @JoinColumn(name = "franchise_id")
    private Franchise franchise;

}
