package com.experis.assignment.dao;

import com.experis.assignment.model.Franchise;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FranchiseRepository extends JpaRepository<Franchise, Long> {
}
