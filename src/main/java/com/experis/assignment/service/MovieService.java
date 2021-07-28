package com.experis.assignment.service;

import com.experis.assignment.dao.CharacterRepository;
import com.experis.assignment.dao.FranchiseRepository;
import com.experis.assignment.dao.MovieRepository;
import com.experis.assignment.model.Character;
import com.experis.assignment.model.Franchise;
import com.experis.assignment.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository repository;
    private final CharacterRepository actorRepository;
    private final FranchiseRepository franchiseRepository;

    @Autowired
    public MovieService(MovieRepository repository,
                        CharacterRepository actorRepository,
                        FranchiseRepository franchiseRepository) {
        this.repository = repository;
        this.actorRepository = actorRepository;
        this.franchiseRepository = franchiseRepository;
    }

    public List<Movie> getAllMovies() {
        return repository.findAll();
    }

    public Movie getMovieById(long id) {
        Optional<Movie> optionalMovie = repository.findById(id);
        return (optionalMovie.isEmpty()) ? null : optionalMovie.get();
    }

    public Movie getMovieByTitle(String title) {
        return repository.findByTitle(title);
    }

    public Movie addMovie(Movie movie) {
        return repository.save(movie);
    }

    public Movie updateMovie(Movie movie) {
        return repository.save(movie);
    }

    /**
     * Deletes a movie by first finding it by checking if it exists
     * and then simply removing it if found.
     * <p>
     * This method will always return a boolean, indicating if the removal
     * was successful.
     *
     * @param id used to identify a movie.
     * @return a boolean for if the removal was successful.
     */
    public boolean removeMovie(long id) {
        boolean found = repository.existsById(id);
        if (found) {
            Movie movie = repository.getById(id);
            removeMovieFromFranchise(movie);
            removeMovieFromCharacter(movie);
            repository.delete(movie);
        }
        return found;
    }

    /**
     * Updates the characters that are in the the movie,
     * by removing the movie from those characters.
     *
     * @param movie used to remove a movie from characters.
     */
    private void removeMovieFromCharacter(Movie movie) {
        List<Character> characters = actorRepository.findByMovies(movie);
        for (Character character : characters) {
            character.removeMovie(movie);
            actorRepository.save(character);
        }
    }

    /**
     * Updates a franchise for the given movie by removing that movie
     * from the franchise.
     *
     * @param movie used to remove movie from a franchise.
     */
    private void removeMovieFromFranchise(Movie movie) {
        Franchise franchise = movie.getFranchise();
        franchise.removeMovie(movie);
        franchiseRepository.save(franchise);
    }

    /**
     * Updates a movie with a character, by using the parameters
     * this methode checks if they both exists and then adds
     * the character to the movie.
     * <p>
     * This method always returns a movie object or null if one of the IDs
     * are wrong.
     *
     * @param id          a long used to identify a movie.
     * @param actorId a long used to identify a character.
     * @return the specified movie update with a character.
     */
    public Movie updateMovieWithActor(long id, long actorId) {
        Optional<Movie> optionalMovie = repository.findById(id);
        Optional<Character> optionalCharacter = actorRepository.findById(actorId);
        if (optionalMovie.isEmpty())
            return null;
        else if (optionalCharacter.isEmpty())
            return null;

        Movie movie = optionalMovie.get();
        Character character = optionalCharacter.get();
        movie.addCharacter(character);
        Movie returnMovie = repository.save(movie);
        character.addMovie(returnMovie);
        actorRepository.save(character);
        return returnMovie;
    }

    /**
     * Updates a movie with a franchise, by using the parameters
     * this methode checks if they both exists and then adds
     * the franchise to the movie.
     * <p>
     * This method always returns a movie object or null if one of the IDs
     * are wrong.
     *
     * @param id          a long used to identify a movie.
     * @param franchiseId a long used to identify a franchise.
     * @return the specified movie update with a franchise.
     */
    public Movie updateMovieWithFranchise(long id, long franchiseId) {
        Optional<Movie> optionalMovie = repository.findById(id);
        Optional<Franchise> optionalFranchise = franchiseRepository.findById(franchiseId);
        if (optionalMovie.isEmpty())
            return null;
        else if (optionalFranchise.isEmpty())
            return null;

        Franchise franchise = optionalFranchise.get();
        Movie movie = optionalMovie.get();
        movie.setFranchise(franchise);
        Movie returnMovie = repository.save(movie);
        franchise.addMovie(returnMovie);
        franchiseRepository.save(franchise);
        return returnMovie;
    }

    /**
     * Retrieves a list of characters for the specified movie found using this method's
     * ID parameter.
     * <p>
     * This method returns immediately a list of characters or null if the movie was not found.
     *
     * @param id used to identify a movie.
     * @return a list of characters that partook in the creation of the movie.
     */
    public List<Character> getCharactersInMovie(long id) {
        Optional<Movie> optionalMovie = repository.findById(id);
        return (optionalMovie.isEmpty()) ? null : actorRepository.findByMovies(optionalMovie.get());
    }
}
