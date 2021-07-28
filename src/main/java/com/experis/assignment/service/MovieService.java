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

    public boolean movieExist(long id) {
        return repository.existsById(id);
    }

    public Movie getMovieById(long id) {
        return repository.getById(id);
    }

    public Movie getMovieByTitle(String title) {
        return repository.findByTitle(title);
    }

    public boolean existMovieByTitle(String title) {
        return repository.existsByTitle(title);
    }

    public Movie addMovie(Movie movie) {
        return repository.save(movie);
    }

    public Movie updateMovie(Movie movie) {
        return repository.save(movie);
    }

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

    private void removeMovieFromCharacter(Movie movie) {
        List<Character> characters = actorRepository.findByMovies(movie);
        for (Character character : characters) {
            character.removeMovie(movie);
            actorRepository.save(character);
        }
    }

    private void removeMovieFromFranchise(Movie movie) {
        Franchise franchise = movie.getFranchise();
        franchise.removeMovie(movie);
        franchiseRepository.save(franchise);
    }

    public Movie updateMovieWithActor(long id, long actorId) {
        Optional<Movie> optionalMovie = repository.findById(id);
        if (optionalMovie.isEmpty())
            return null;
        Movie movie = optionalMovie.get();

        Optional<Character> optionalCharacter = actorRepository.findById(actorId);
        if (optionalCharacter.isEmpty())
            return null;
        Character character = optionalCharacter.get();

        movie.addCharacter(character);
        Movie returnMovie = repository.save(movie);
        character.addMovie(returnMovie);
        actorRepository.save(character);
        return returnMovie;
    }

    public Movie updateMovieWithFranchise(long id, long franchiseId) {
        Optional<Movie> optionalMovie = repository.findById(id);
        if (optionalMovie.isEmpty())
            return null;
        Movie movie = optionalMovie.get();

        Optional<Franchise> optionalFranchise = franchiseRepository.findById(franchiseId);
        if (optionalFranchise.isEmpty())
            return null;
        Franchise franchise = optionalFranchise.get();

        movie.setFranchise(franchise);
        Movie returnMovie = repository.save(movie);
        franchise.addMovie(returnMovie);
        franchiseRepository.save(franchise);
        return returnMovie;
    }

    public List<Character> getCharactersInMovie(long id) {
        Optional<Movie> optionalMovie = repository.findById(id);
        if(optionalMovie.isEmpty())
            return null;
        else
            return actorRepository.findByMovies(optionalMovie.get());
    }
}
