package com.experis.assignment.service;

import com.experis.assignment.dao.CharacterRepository;
import com.experis.assignment.dao.FranchiseRepository;
import com.experis.assignment.dao.MovieRepository;
import com.experis.assignment.model.Character;
import com.experis.assignment.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterService {

    private final CharacterRepository repository;
    private final MovieRepository movieRepository;
    private final FranchiseRepository franchiseRepository;

    @Autowired
    public CharacterService(CharacterRepository repository,
                            MovieRepository movieRepository,
                            FranchiseRepository franchiseRepository) {
        this.repository = repository;
        this.movieRepository = movieRepository;
        this.franchiseRepository = franchiseRepository;
    }


    public Character save(Character character) {
        return repository.save(character);
    }

    public List<Character> findAll() {
        return repository.findAll();
    }

    public Character findById(long id) {
        Optional<Character> optionalCharacter = repository.findById(id);
        return (optionalCharacter.isEmpty()) ? null : optionalCharacter.get();
    }

    public Character updateCharacter(Character character) {
        return repository.save(character);
    }

    /**
     * Removes a character in the database specified by the parameter id
     * <p>
     * This method returns a boolean indicating if the
     * operation was successful or not.
     *
     * @param id the character primary key
     * @return a boolean indicating if the operation was successful
     */
    public boolean removeCharacter(long id) {
        boolean found = repository.existsById(id);
        if(found){
            Character character = repository.getById(id);
            removeCharacterFromMovies(character);
            repository.delete(character);
        }
        return found;
    }

    /**
     * Updates the movies that are in the the character,
     * by removing the character from those movies.
     *
     * @param character used to remove a character from movies.
     */
    private void removeCharacterFromMovies(Character character) {
        List<Movie> movies = movieRepository.findByCharacters(character);
        for (Movie movie : movies) {
            movie.removeCharacter(character);
            movieRepository.save(movie);
        }
    }
}
