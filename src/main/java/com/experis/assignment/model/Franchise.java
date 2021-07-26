package com.experis.assignment.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@Getter
@Setter
public class Franchise {

    @Id
    private long id;

    @Column
    private String name;

    @Column
    private String description;
}
