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

    private FranchiseService franchiseService;

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
        List<Character> characters = null;
        HttpStatus status = HttpStatus.NOT_FOUND;
        if(franchiseService.franchiseExist(id)) {
            characters = franchiseService.getCharactersInFranchise(id);
            status = HttpStatus.OK;
        }
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
    @RequestMapping(value="", method = RequestMethod.POST)
    public ResponseEntity<Franchise> addFranchise(@RequestBody Franchise franchise){
        Franchise returnFranchise = franchiseService.save(franchise);
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
    @RequestMapping(value = "/{id}/movies", method = RequestMethod.GET)
    public ResponseEntity<List<Movie>> getAllMoviesInFranchise(@PathVariable long id) {
        List<Movie> movies = null;
        HttpStatus status = HttpStatus.NOT_FOUND;
        if(franchiseService.franchiseExist(id)) {
            movies = franchiseService.getMoviesInFranchise(id);
            status = HttpStatus.OK;
        }
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
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Franchise> getAFranchise(@PathVariable long id){
        HttpStatus status;
        Franchise franchise = new Franchise();
        if(!franchiseService.existsById(id)){
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(franchise, status);
        }
        franchise = franchiseService.findById(id).get();
        status = HttpStatus.OK;
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
    @RequestMapping(value="", method = RequestMethod.GET)
    public ResponseEntity<List<Franchise>> getAllFranchises(){
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
    @RequestMapping(value="/{id}", method= RequestMethod.PUT)
    public ResponseEntity<Franchise> updateFranchise(@PathVariable long id, @RequestBody Franchise updatedFranchise){
        HttpStatus status;
        Franchise franchise = new Franchise();
        if(franchiseService.existsById(id)){
            franchise = franchiseService.findById(id).get();

            if(updatedFranchise.getName() != null){
                franchise.setName(updatedFranchise.getName());
            }
            if(updatedFranchise.getDescription() != null){
                franchise.setDescription(updatedFranchise.getDescription());
            }
            franchiseService.save(franchise);
            status = HttpStatus.OK;

        }else{
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
    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteFranchise(@PathVariable long id){
        HttpStatus status;
        boolean deleted = franchiseService.removeFranchise(id);
        if(deleted){
            status = HttpStatus.OK;
        }else{
            status = HttpStatus.NOT_FOUND;
        }
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
    @RequestMapping(value="/{id}/movies/{movieId}", method = RequestMethod.PUT)
    public ResponseEntity<Franchise> updateMovieInFranchise(@PathVariable long id,
                                                            @PathVariable long movieId) {
        Franchise franchise = franchiseService.updateFranchiseWithMovie(id, movieId);
        HttpStatus status = (franchise == null) ? HttpStatus.BAD_REQUEST : HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(franchise, status);
    }
}
