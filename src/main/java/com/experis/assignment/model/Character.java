package com.experis.assignment.model;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
public class Character {

    @Id
    private long id;

}
