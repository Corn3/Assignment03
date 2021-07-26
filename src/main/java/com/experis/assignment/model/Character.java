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
public class Character {

    @Id
    private long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String alias;

    @Column
    private String gender;

    @Column
    private String picture;
/*
    @ManyToMany(mappedBy = "characters")
    private ArrayList<Movie> movies;
*/
}
