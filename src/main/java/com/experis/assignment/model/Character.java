package com.experis.assignment.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.net.URL;

@Entity
@Table
@Getter
@Setter
public class Character {

    @Id
    private long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String alias;

    @Column
    private GenderType gender;

    @Column
    private URL picture;

}
