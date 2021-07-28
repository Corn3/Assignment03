package com.experis.assignment.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.AccessLevel;
import lombok.Data;
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
@Data
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 150, nullable = false)
    private String genre;

    @Column(name = "release_year", length = 4, nullable = false)
    private int releaseYear;

    @Column(name = "director_name", length = 50, nullable = false)
    private String directorName;

    @Column
    private URL picture;

    @Column
    private URL trailer;

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @ManyToMany
    @JoinTable(
            name ="movie_character",
            joinColumns = {@JoinColumn(name = "movie_id")},
            inverseJoinColumns = {@JoinColumn(name = "character_id")}
    )
    private List<Character> characters;

    @ManyToOne
    @JoinColumn(name = "franchise_id")
    private Franchise franchise;

    /**
     * Creates a list of endpoints for characters to minimize the amount of data displayed
     * when retrieving a Movie.
     *
     * @return a list of endpoints to be displayed to the user.
     */
    @JsonGetter("characters")
    public List<String> characters() {
        if(characters != null) {
            return characters.stream()
                    .map(character -> {
                        return "/api/v1/characters/" + character.getId();
                    }).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    /**
     * Creates an endpoint for franchises to minimize the amount of data displayed
     * when retrieving a Movie.
     *
     * @return an endpoint to be displayed to the user.
     */
    @JsonGetter("franchise")
    public String franchise() {
        return (franchise != null) ? "/api/v1/franchises/" + franchise.getId() : null;
    }

    public void addCharacter(Character character) {
        characters.add(character);
    }

    public void removeCharacter(Character character) {
        characters.remove(character);
    }
}
