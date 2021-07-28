package com.experis.assignment.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Lombok is used here to minimize boiler plate code.
 */
@Entity
@Table
@Getter
@Setter
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name", length = 40)
    private String firstName;

    @Column(name = "last_name", length = 60)
    private String lastName;

    @Column(length = 40)
    private String alias;

    @Column(length = 20)
    private String gender;

    @Column
    private URL picture;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @ManyToMany(mappedBy = "characters")
    private List<Movie> movies;

    /**
     * Creates a list of endpoints for movies to minimize the amount of data displayed
     * when retrieving a Character.
     *
     * @return a list of endpoints to be displayed to the user.
     */
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

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public void removeMovie(Movie movie) {
        movies.remove(movie);
    }

}
