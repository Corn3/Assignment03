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

    /**
     * Adds a franchise passed as a parameter using the REST operation POST
     * <p>
     * This method adds a franchise and returns a status indicating if the
     * operation was successful or not.
     *
     * @param franchise to be added to the database
     * @return added franchise with the corresponding HTTP status
     */
    @PostMapping
    public ResponseEntity<Franchise> addFranchise(@RequestBody Franchise franchise) {
        Franchise returnFranchise = franchiseService.addFranchise(franchise);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnFranchise, status);
    }


    /**
     * Gets all movies in a franchise using the parameter id and the REST operation GET
     * <p>
     * This method returns a list of movies and returns a status indicating if the
     * operation was successful or not.
     *
     * @param id of the franchise
     * @return a list of movies in the franchise with the corresponding HTTP status
     */
    @GetMapping(value = "/{id}/movies")
    public ResponseEntity<List<Movie>> getAllMoviesInFranchise(@PathVariable long id) {
        List<Movie> movies = franchiseService.getMoviesInFranchise(id);
        HttpStatus status = getStatus(movies);
        return new ResponseEntity<>(movies, status);
    }



    /**
     * Gets a franchise using the REST operation GET using the  parameter id
     * <p>
     * This method returns a franchise object and status indicating if the
     * operation was successful or not.
     *
     * @param id used for finding a franchise.
     * @return a franchise if found as well as the corresponding HTTP status
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Franchise> getAFranchise(@PathVariable long id) {
        Franchise franchise = franchiseService.getFranchise(id);
        HttpStatus status = getStatus(franchise);
        return new ResponseEntity<>(franchise, status);
    }


    /**
     * Gets all franchises in the database using the REST operation GET
     * <p>
     * This method returns a list of franchises and returns a status indicating if the
     * operation was successful or not.
     *
     * @return a list of franchises with the corresponding HTTP status
     */
    @GetMapping
    public ResponseEntity<List<Franchise>> getAllFranchises() {
        List<Franchise> data = franchiseService.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(data, status);
    }



    /**
     * Updates a franchise specified with the parameter id and the REST operation PUT
     * <p>
     * This method returns a franchise object and a status indicating if the
     * operation was successful or not.
     *
     * @param id of the franchise
     * @param updatedFranchise the data used to update the franchise
     * @return an updated franchise with the corresponding HTTP status
     */
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



    /**
     * Deletes a franchise in the database specified by the parameter id using the REST operation DELETE
     * <p>
     * This method returns a boolean and a status indicating if the
     * operation was successful or not.
     *
     * @param id the franchise primary key
     * @return a boolean and http status indicating if the operation was successful
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteFranchise(@PathVariable long id) {
        boolean deleted = franchiseService.removeFranchise(id);
        HttpStatus status = (deleted) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(deleted, status);
    }

    /**
     * Updates movies in a franchise specified by the parameter id and movie using its movieId
     * using the REST operation DELETE
     * <p>
     * This method returns an updated franchise object  and a status indicating if the
     * operation was successful or not.
     *
     * @param id the franchise primary key
     * @param movieId the movies primary key
     * @return an updated franchise and http status indicating if the operation was successful or not
     */
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
