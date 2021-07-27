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

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = movieService.getAllMovies();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(movies, status);
    }

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

    @GetMapping("/{id}/characters")
    public ResponseEntity<List<Character>> getCharactersInMovie(@PathVariable long id) {
        List<Character> characters = null;
        HttpStatus status = HttpStatus.NOT_FOUND;
        if(movieService.movieExist(id)) {
            characters = movieService.getCharactersInMovie(id);
            status = HttpStatus.OK;
        }

        return new ResponseEntity<>(characters, status);
    }

    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        Movie returnMovie = movieService.addMovie(movie);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnMovie, status);
    }

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

    @PutMapping("/{id}/characters/{actorId}")
    public ResponseEntity<Movie> updateMovieWithActor(@PathVariable long id,
                                                      @PathVariable long actorId) {
        Movie movie = movieService.updateMovieWithActor(id, actorId);
        HttpStatus status = (movie == null) ? HttpStatus.BAD_REQUEST : HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(movie, status);
    }

    @PutMapping("/{id}/franchise/{franchiseId}")
    public ResponseEntity<Movie> updateMovieWithFranchise(@PathVariable long id,
                                                      @PathVariable long franchiseId) {
        Movie movie = movieService.updateMovieWithFranchise(id, franchiseId);
        HttpStatus status = (movie == null) ? HttpStatus.BAD_REQUEST : HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(movie, status);
    }

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
