package com.experis.assignment.model;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
public class Franchise {

    @Id
    private long id;

}
