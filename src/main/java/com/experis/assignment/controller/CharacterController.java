package com.experis.assignment.controller;

import com.experis.assignment.model.Character;
import com.experis.assignment.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/character")
public class CharacterController {

    private CharacterService characterService;

    @Autowired
    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }


    // add a character
    @RequestMapping(value="", method = RequestMethod.POST)
    public ResponseEntity<Character> addCharacter(@RequestBody Character character){
        Character returnCharacter = characterService.save(character);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnCharacter, status);
    }


    // get all characters
    @RequestMapping(value="", method= RequestMethod.GET)
    public  ResponseEntity<List<Character>> getAllCharacters(){
        List<Character> data = characterService.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(data, status);
    }

    // get a character
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Character> getACharacter(@PathVariable long id){
        HttpStatus status;
        Character character = new Character();
        if(!characterService.existsById(id)){
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(character, status);
        }
        character = characterService.findById(id).get();
        status = HttpStatus.OK;
        return new ResponseEntity<>(character, status);

    }


    // update a character
    @RequestMapping(value="/{id}", method= RequestMethod.PUT)
    public ResponseEntity<Character> updateCharacter(@PathVariable long id, @RequestBody Character updatedCharacter){
        HttpStatus status;
        Character character = new Character();
        if(characterService.existsById(id)){
            character = characterService.findById(id).get();

            if(updatedCharacter.getFirstName() != null){
                character.setFirstName(updatedCharacter.getFirstName());
            }
            if(updatedCharacter.getLastName() != null){
                character.setLastName(updatedCharacter.getLastName());
            }
            if(updatedCharacter.getAlias() != null){
                character.setAlias(updatedCharacter.getAlias());
            }
            if(updatedCharacter.getGender() != null){
                character.setGender(updatedCharacter.getGender());
            }
            if(updatedCharacter.getPicture() != null){
                character.setPicture(updatedCharacter.getPicture());
            }
            characterService.save(character);
            status = HttpStatus.OK;

        }else{
            status = HttpStatus.NO_CONTENT;
        }
        return new ResponseEntity<>(character, status);
    }


    // delete a character
    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteCharacter(@PathVariable long id){
        HttpStatus status;
        boolean deleted = characterService.removeCharacter(id);
        if(deleted){
            status = HttpStatus.OK;
        }else{
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(deleted, status);
    }


}
