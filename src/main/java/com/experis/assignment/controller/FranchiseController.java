package com.experis.assignment.controller;

import com.experis.assignment.model.Character;
import com.experis.assignment.model.Franchise;
import com.experis.assignment.model.Movie;
import com.experis.assignment.service.FranchiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/franchises")
public class FranchiseController {

    private final FranchiseService franchiseService;

    @Autowired
    public FranchiseController(FranchiseService franchiseService) {
        this.franchiseService = franchiseService;
    }

    /**
     * Fetches all characters in a given franchise. To obtain a franchise a valid ID must be
     * passed in the parameter. The REST operation GET is used.
     * <p>
     * This method will always return a character list and a status, showing if the call was
     * successful or not.
     *
     * @param id used for finding a franchise.
     * @return a list of characters that are part of the given franchise.
     */
    @GetMapping("/{id}/characters")
    public ResponseEntity<List<Character>> getAllCharactersInFranchise(@PathVariable long id) {
        List<Character> characters = franchiseService.getCharactersInFranchise(id);
        HttpStatus status = getStatus(characters);
        return new ResponseEntity<>(characters, status);
    }

    // add a franchise
    @PostMapping
    public ResponseEntity<Franchise> addFranchise(@RequestBody Franchise franchise) {
        Franchise returnFranchise = franchiseService.addFranchise(franchise);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnFranchise, status);
    }

    @GetMapping(value = "/{id}/movies")
    public ResponseEntity<List<Movie>> getAllMoviesInFranchise(@PathVariable long id) {
        List<Movie> movies = franchiseService.getMoviesInFranchise(id);
        HttpStatus status = getStatus(movies);
        return new ResponseEntity<>(movies, status);
    }


    // get a franchise
    @GetMapping(value = "/{id}")
    public ResponseEntity<Franchise> getAFranchise(@PathVariable long id) {
        Franchise franchise = franchiseService.getFranchise(id);
        HttpStatus status = getStatus(franchise);
        return new ResponseEntity<>(franchise, status);
    }

    // get all franchises
    @GetMapping
    public ResponseEntity<List<Franchise>> getAllFranchises() {
        List<Franchise> data = franchiseService.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(data, status);
    }

    // update a franchise
    @PutMapping(value = "/{id}")
    public ResponseEntity<Franchise> updateFranchise(@PathVariable long id, @RequestBody Franchise updatedFranchise) {
        HttpStatus status;
        Franchise franchise = franchiseService.getFranchise(id);
        if (franchise != null) {
            if (updatedFranchise.getName() != null)
                franchise.setName(updatedFranchise.getName());
            if (updatedFranchise.getDescription() != null)
                franchise.setDescription(updatedFranchise.getDescription());

            franchiseService.updateFranchise(franchise);
            status = HttpStatus.OK;

        } else {
            status = HttpStatus.NO_CONTENT;
        }
        return new ResponseEntity<>(franchise, status);
    }

    // delete a franchise
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteFranchise(@PathVariable long id) {
        boolean deleted = franchiseService.removeFranchise(id);
        HttpStatus status = (deleted) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(deleted, status);
    }

    // updating movies in a franchise
    @PutMapping(value = "/{id}/movies/{movieId}")
    public ResponseEntity<Franchise> updateMovieInFranchise(@PathVariable long id,
                                                            @PathVariable long movieId) {
        Franchise franchise = franchiseService.updateFranchiseWithMovie(id, movieId);
        HttpStatus status = getContentStatus(franchise);
        return new ResponseEntity<>(franchise, status);
    }

    private <E> HttpStatus getStatus(E element) {
        return (element == null) ? HttpStatus.NOT_FOUND : HttpStatus.OK;
    }

    private <E> HttpStatus getContentStatus(E element) {
        return (element == null) ? HttpStatus.BAD_REQUEST : HttpStatus.NO_CONTENT;
    }


}
