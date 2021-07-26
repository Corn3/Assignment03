package com.experis.assignment.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table
@Getter
@Setter
public class Franchise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column
    private String description;

    @OneToMany
    @JoinColumn(name = "movie_id")
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
