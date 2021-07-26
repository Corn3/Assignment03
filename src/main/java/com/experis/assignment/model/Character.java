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
public class Character {

    @Id
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column
    private String alias;

    @Column
    private String gender;

    @Column
    private URL picture;

    @ManyToMany(mappedBy = "characters")
    private List<Movie> movies;

}
