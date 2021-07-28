package com.experis.assignment.service;

import com.experis.assignment.dao.CharacterRepository;
import com.experis.assignment.dao.FranchiseRepository;
import com.experis.assignment.dao.MovieRepository;
import com.experis.assignment.model.Character;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterService {

    private CharacterRepository repository;
    private MovieRepository movieRepository;
    private FranchiseRepository franchiseRepository;

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

    public boolean removeCharacter(long id) {
        boolean found = repository.existsById(id);
        if(found){
            Character character = repository.getById(id);
            repository.delete(character);
        }
        return found;
    }
}
