package com.experis.assignment.model;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.net.URL;

@Entity
@Table
@Getter
public class Character {

    @Id
    private long id;

    @Column
    private String fullName;

    @Column
    private String alias;

    @Column
    private GenderType gender;

    @Column
    private URL picture;

    //public Character(String fullName, String alias, )

}
