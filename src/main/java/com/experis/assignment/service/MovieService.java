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

@Service
public class MovieService {

    private MovieRepository repository;
    private CharacterRepository actorRepository;
    private FranchiseRepository franchiseRepository;

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
        if(found) {
            Movie movie = repository.getById(id);
            removeMovieFromFranchise(movie);
            removeMovieFromCharacter(movie);
            repository.delete(movie);
        }
        return found;
    }

    private void removeMovieFromCharacter(Movie movie) {
        List<Character> characters = actorRepository.findByMovies(movie);
        for(int i = 0; i < characters.size(); i++) {
            Character character = characters.get(i);
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
        Movie movie = repository.getById(id);
        if(movie == null)
            return movie;
        Character character = actorRepository.getById(actorId);
        movie.addCharacter(character);
        Movie returnMovie = repository.save(movie);
        if(returnMovie != null) {
            character.addMovie(returnMovie);
            actorRepository.save(character);
        }
        return returnMovie;
    }

    public Movie updateMovieWithFranchise(long id, long franchiseId) {
        Movie movie = repository.getById(id);
        if(movie == null)
            return movie;
        Franchise franchise = franchiseRepository.getById(franchiseId);
        movie.setFranchise(franchise);
        Movie returnMovie = repository.save(movie);
        if(returnMovie != null) {
            franchise.addMovie(returnMovie);
            franchiseRepository.save(franchise);
        }
        return returnMovie;
    }

    public List<Character> getCharactersInMovie(long id) {
        return actorRepository.findByMovies(repository.findById(id).get());
    }
}
