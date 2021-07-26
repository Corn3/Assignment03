package com.experis.assignment.controller;

import com.experis.assignment.service.FranchiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FranchiseController {

    @Autowired
    private FranchiseService franchiseService;

}
