package com.experis.assignment.dao;

import com.experis.assignment.model.Character;
import com.experis.assignment.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {
    List<Character> findByMovies(Movie movie);
}
