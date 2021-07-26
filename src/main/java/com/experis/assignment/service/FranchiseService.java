package com.experis.assignment.service;

import com.experis.assignment.dao.FranchiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FranchiseService {

    @Autowired
    private FranchiseRepository repository;

}
