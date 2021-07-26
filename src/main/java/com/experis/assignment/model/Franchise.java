package com.experis.assignment.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table
@Getter
@Setter
public class Franchise {

    @Id
    private long id;

    @Column
    private String name;

    @Column
    private String description;
/*
    @OneToMany
    @JoinColumn(name = "movie_id")
    private ArrayList<Movie> movies;
*/
}
