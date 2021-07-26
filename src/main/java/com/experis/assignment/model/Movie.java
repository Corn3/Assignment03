package com.experis.assignment.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.net.URL;

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
}
