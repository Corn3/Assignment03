package com.experis.assignment.service;

import com.experis.assignment.dao.CharacterRepository;
import com.experis.assignment.dao.FranchiseRepository;
import com.experis.assignment.dao.MovieRepository;
import com.experis.assignment.model.Character;
import com.experis.assignment.model.Franchise;
import com.experis.assignment.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FranchiseService {

    private final FranchiseRepository repository;
    private final MovieRepository movieRepository;
    private final CharacterRepository characterRepository;

    @Autowired
    public FranchiseService(FranchiseRepository repository,
                            MovieRepository movieRepository,
                            CharacterRepository characterRepository) {
        this.repository = repository;
        this.movieRepository = movieRepository;
        this.characterRepository = characterRepository;
    }

    public boolean franchiseExist(long id) {
        return repository.existsById(id);
    }

    /**
     * Retrieves a list of characters that are part of a franchise. In order to fetch these,
     * all movies for the specific franchise must be retrieved and then the characters that partook
     * in those movies can be returned.
     * <p>
     * This method always returns a list of non-duplicate characters.
     *
     * @param id used to get a franchise.
     * @return a list of characters for a given franchise,
     */
    public List<Character> getCharactersInFranchise(long id) {
        Optional<Franchise> optionalFranchise = repository.findById(id);
        if(optionalFranchise.isEmpty())
            return null;

        List<Movie> movies = movieRepository.findByFranchise(optionalFranchise.get());
        Set<Character> characterSet = new HashSet<>();
        for (Movie movie : movies) {
            characterSet = Stream.concat(characterSet.stream(), characterRepository.findByMovies(movie).stream())
                    .collect(Collectors.toSet());
        }

        return new ArrayList<>(characterSet);
    }


    public List<Movie> getMoviesInFranchise(long id) {
        return movieRepository.getAllByFranchiseId(id);
    }

    public List<Franchise> findAll() {
        return repository.findAll();
    }

    public Franchise getFranchise(long id) {
        Optional<Franchise> optionalFranchise = repository.findById(id);
        return (optionalFranchise.isEmpty()) ? null : optionalFranchise.get();
    }

    public Franchise addFranchise(Franchise franchise) {
        return repository.save(franchise);
    }

    public Franchise updateFranchise(Franchise franchise) {
        return repository.save(franchise);
    }

    /**
     * Removes a franchise in the database specified by the parameter id
     * <p>
     * This method returns a boolean indicating if the
     * operation was successful or not.
     *
     * @param id the franchise primary key
     * @return a boolean indicating if the operation was successful
     */
    public boolean removeFranchise(long id) {
        boolean found = repository.existsById(id);
        if(found){
            Franchise franchise = repository.getById(id);
            removeFranchiseFromMovie(franchise);
            repository.delete(franchise);
        }
        return found;
    }

    /**
     * Updates all movies for the given franchise by removing that franchise
     * from those movies.
     *
     * @param franchise used to remove franchise from a movie.
     */
    private void removeFranchiseFromMovie(Franchise franchise) {
        List<Movie> movies = movieRepository.findByFranchise(franchise);
        for(Movie movie : movies) {
            movie.setFranchise(null);
            movieRepository.save(movie);
        }
    }

    /**
     * Updates a franchise's movies in the database specified by the parameter id
     * and movie specified by the parameter movieId
     * <p>
     * This method returns a franchise object and a http status indicating if the
     * operation was successful or not.
     *
     * @param id the franchise primary key
     * @param movieId the movies primary key
     * @return an updated franchise
     */
    public Franchise updateFranchiseWithMovie(long id, long movieId) {
        Optional<Franchise> optionalFranchise = repository.findById(id);
        Optional<Movie> optionalMovie = movieRepository.findById(movieId);
        if(optionalFranchise.isEmpty())
            return null;
        else if(optionalMovie.isEmpty())
            return null;

        Franchise franchise = optionalFranchise.get();
        Movie movie = optionalMovie.get();
        franchise.addMovie(movie);
        Franchise returnFranchise = repository.save(franchise);
        movie.setFranchise(returnFranchise);
        movieRepository.save(movie);
        return returnFranchise;
    }
}
