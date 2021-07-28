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

    // add a franchise
    @RequestMapping(value="", method = RequestMethod.POST)
    public ResponseEntity<Franchise> addFranchise(@RequestBody Franchise franchise){
        Franchise returnFranchise = franchiseService.save(franchise);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnFranchise, status);
    }

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


    // get a franchise
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

    // get all franchises
    @RequestMapping(value="", method = RequestMethod.GET)
    public ResponseEntity<List<Franchise>> getAllFranchises(){
        List<Franchise> data = franchiseService.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(data, status);
    }

    // update a franchise
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

    // delete a franchise
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

    // updating movies in a franchise
    @RequestMapping(value="/{id}/movies/{movieId}", method = RequestMethod.PUT)
    public ResponseEntity<Franchise> updateMovieInFranchise(@PathVariable long id,
                                                            @PathVariable long movieId) {
        Franchise franchise = franchiseService.updateFranchiseWithMovie(id, movieId);
        HttpStatus status = (franchise == null) ? HttpStatus.BAD_REQUEST : HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(franchise, status);
    }



}
