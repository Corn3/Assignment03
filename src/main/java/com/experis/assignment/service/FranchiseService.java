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

    private FranchiseRepository repository;
    private MovieRepository movieRepository;
    private CharacterRepository characterRepository;

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

        List<Character> characters = new ArrayList<>(characterSet);
        return characters;
    }
}
