package com.experis.assignment.controller;

import com.experis.assignment.model.Character;
import com.experis.assignment.model.Movie;
import com.experis.assignment.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    /**
     * Retrieves a list of movies using the REST operation GET.
     * <p>
     * This will always return a list whether or not movies exist.
     * A HTTP status of 200 will always be returned.
     *
     * @return a list of movies and a status dictating if the operation was successful.
     */
    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = movieService.getAllMovies();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(movies, status);
    }

    /**
     * Finds a movie by applying the REST method GET using id passed as a parameter.
     * <p>
     * This method always returns a movie object and a
     * status indicating if the retrieval was successful or not.
     *
     * @param id the primary key for a specific movie to be retrieved.
     * @return a movie if one with the specific id was found, together with a HTTP status
     * for if the retrieval was successful.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable long id) {
        Movie movie = new Movie();
        HttpStatus status;

        if (movieService.movieExist(id)) {
            status = HttpStatus.OK;
            movie = movieService.getMovieById(id);
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(movie, status);
    }

    /**
     * Finds a movie by applying the REST method GET using the title passed as a parameter.
     * <p>
     * This method always returns a movie object and a
     * status indicating if the retrieval was successful or not.
     *
     * @param title the name of the movie to be retrieved.
     * @return a movie if one with the specific title was found, together with a HTTP status
     * for if the retrieval was successful.
     */
    @GetMapping("/{title}")
    public ResponseEntity<Movie> getMovieByTitle(@PathVariable String title) {
        Movie movie = new Movie();
        HttpStatus status;

        if (movieService.existMovieByTitle(title)) {
            status = HttpStatus.OK;
            movie = movieService.getMovieByTitle(title);
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(movie, status);
    }

    /**
     * Finds all characters in a movie by applying the REST method GET
     * using the id passed as a parameter.
     * <p>
     * This method always returns a list of Characters and a
     * status indicating if the retrieval was successful or not.
     *
     * @param id the primary key of the movie from which characters are to be retrieved.
     * @return a list of characters if the movie with the specified id exists, together with a HTTP status
     * for if the retrieval was successful.
     */
    @GetMapping("/{id}/characters")
    public ResponseEntity<List<Character>> getCharactersInMovie(@PathVariable long id) {
        List<Character> characters = null;
        HttpStatus status = HttpStatus.NOT_FOUND;
        if (movieService.movieExist(id)) {
            characters = movieService.getCharactersInMovie(id);
            status = HttpStatus.OK;
        }

        return new ResponseEntity<>(characters, status);
    }

    /**
     * Adds the movie passed as a parameter to the database
     * by applying the REST method POST.
     * <p>
     * This method always returns the added movie and a
     * status indicating if the creation was successful.
     *
     * @param movie the movie to be added to the database.
     * @return the added movie and a status indicating
     * that the movie has been added.
     */
    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        Movie returnMovie = movieService.addMovie(movie);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnMovie, status);
    }

    /**
     * Updates the specified movie with the data passed in the parameter.
     * The REST method PUT is used in order to accomplish this.
     * <p>
     * This method will always return a movie object and a
     * status code indicating if the update worked.
     *
     * @param id    the primary key of the movie.
     * @param movie the data used for updating the specified movie with.
     * @return an updated movie is returned with a HTTP status indicating if the update went through.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable long id,
                                             @RequestBody Movie movie) {

        Movie returnMovie = new Movie();
        HttpStatus status;

        if (movie.getId() != id)
            status = HttpStatus.BAD_REQUEST;
        else {
            status = HttpStatus.NO_CONTENT;
            returnMovie = movieService.updateMovie(movie);
        }

        return new ResponseEntity<>(returnMovie, status);
    }

    /**
     * Updates the specified movie's participating actors list
     * by first using an actor's id and getting that actors data,
     * secondly adding that data to the movie.
     * The REST method PUT is used in order to accomplish this.
     * <p>
     * This method will always return an updated movie object and a
     * status code indicating if the update worked.
     *
     * @param id      the primary key of the movie.
     * @param actorId the key for the actor to be added.
     * @return an updated movie is returned with a HTTP status indicating if the update went through.
     */
    @PutMapping("/{id}/characters/{actorId}")
    public ResponseEntity<Movie> updateMovieWithActor(@PathVariable long id,
                                                      @PathVariable long actorId) {
        Movie movie = movieService.updateMovieWithActor(id, actorId);
        HttpStatus status = (movie == null) ? HttpStatus.BAD_REQUEST : HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(movie, status);
    }

    /**
     * Updates the specified movie's franchise by first using a franchise id
     * and getting the franchise data, this is then added to the movie.
     * The REST method PUT is used in order to accomplish this.
     * <p>
     * This method will always return an updated movie object and a
     * status code indicating if the update worked.
     *
     * @param id          the primary key of the movie.
     * @param franchiseId the key for the franchise to be added.
     * @return an updated movie is returned with a HTTP status indicating if the update went through.
     */
    @PutMapping("/{id}/franchise/{franchiseId}")
    public ResponseEntity<Movie> updateMovieWithFranchise(@PathVariable long id,
                                                          @PathVariable long franchiseId) {
        Movie movie = movieService.updateMovieWithFranchise(id, franchiseId);
        HttpStatus status = (movie == null) ? HttpStatus.BAD_REQUEST : HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(movie, status);
    }

    /**
     * Deletes the specified movie by using the parameter id.
     * The REST method DELETE is used in order to accomplish this.
     * <p>
     * This method will always return a boolean and a status indicating if the removal worked.
     *
     * @param id the primary key of the movie.
     * @return a boolean is returned indicating if the removal was successful,
     * together with a status.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteMovie(@PathVariable long id) {
        HttpStatus status;
        boolean deleted = movieService.removeMovie(id);
        if (deleted) {
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(deleted, status);
    }
}
