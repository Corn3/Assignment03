package com.experis.assignment.controller;

import com.experis.assignment.model.Character;
import com.experis.assignment.service.FranchiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/franchises")
public class FranchiseController {

    private FranchiseService franchiseService;

    @Autowired
    public FranchiseController(FranchiseService franchiseService) {
        this.franchiseService = franchiseService;
    }

    @GetMapping("/{id}/characters")
    public ResponseEntity<List<Character>> getAllCharactersInFranchise(@PathVariable long id) {
        List<Character> characters = null;
        HttpStatus status = HttpStatus.NOT_FOUND;
        if(franchiseService.franchiseExist(id)) {
            characters = franchiseService.getCharactersInFranchise(id);
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(characters, status);
    }

}
