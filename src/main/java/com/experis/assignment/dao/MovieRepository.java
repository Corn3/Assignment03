package com.experis.assignment.dao;

import com.experis.assignment.model.Franchise;
import com.experis.assignment.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findByTitle(String title);
    boolean existsByTitle(String title);
    List<Movie> findByFranchise(Franchise franchise);
}
