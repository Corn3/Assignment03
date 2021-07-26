package com.experis.assignment.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table
@Getter
@Setter
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @ManyToMany(mappedBy = "characters")
    private List<Movie> movies;

    @JsonGetter("movies")
    public List<String> movies() {
        if(movies != null) {
            return movies.stream()
                    .map(movie -> {
                       return "/api/v1/movies/" + movie.getId();
                    }).collect(Collectors.toList());
        } else {
            return null;
        }
    }

}
