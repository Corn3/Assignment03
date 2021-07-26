package com.experis.assignment.model;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
public class Movie {

    @Id
    private long id;

}
