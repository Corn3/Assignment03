package com.experis.assignment.controller;

import com.experis.assignment.model.Character;
import com.experis.assignment.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/characters")
public class CharacterController {

    private final CharacterService characterService;

    @Autowired
    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }


    /**
     * Adds a character passed as a parameter using the REST operation POST
     * <p>
     * This method adds a character and returns a status indicating if the
     * operation was successful or not.
     *
     * @param character to be added in the database
     * @return added character with the corresponding HTTP status indicating success
     */
    @RequestMapping(value="", method = RequestMethod.POST)
    public ResponseEntity<Character> addCharacter(@RequestBody Character character){
        Character returnCharacter = characterService.save(character);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnCharacter, status);
    }


    /**
     * Gets all characters in the database using the REST operation GET
     * <p>
     * This method returns a list of characters and returns a status indicating if the
     * operation was successful or not.
     *
     * @return a list of characters with the corresponding HTTP status indicating success
     */
    @RequestMapping(value="", method= RequestMethod.GET)
    public  ResponseEntity<List<Character>> getAllCharacters(){
        List<Character> data = characterService.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(data, status);
    }

    /**
     * Gets a character using the REST operation GET using the parameter id
     * <p>
     * This method returns a character object and status indicating if the
     * operation was successful or not.
     *
     * @param id used for finding a character.
     * @return a character if found as well as the corresponding HTTP status
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Character> getACharacter(@PathVariable long id) {
        Character character = characterService.findById(id);
        HttpStatus status = getStatus(character);
        return new ResponseEntity<>(character, status);
    }


    /**
     * Updates a character specified with the parameter id and the REST operation PUT
     * <p>
     * This method returns a character object and a status indicating if the
     * operation was successful or not.
     *
     * @param id of the character
     * @param updatedCharacter the data used to update the character
     * @return an updated character with the corresponding HTTP status
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Character> updateCharacter(@PathVariable long id, @RequestBody Character updatedCharacter) {
        Character character = characterService.findById(id);
        HttpStatus status = getContentStatus(character);

        if (character != null) {
            if (updatedCharacter.getFirstName() != null) {
                character.setFirstName(updatedCharacter.getFirstName());
            }
            if (updatedCharacter.getLastName() != null) {
                character.setLastName(updatedCharacter.getLastName());
            }
            if (updatedCharacter.getAlias() != null) {
                character.setAlias(updatedCharacter.getAlias());
            }
            if (updatedCharacter.getGender() != null) {
                character.setGender(updatedCharacter.getGender());
            }
            if (updatedCharacter.getPicture() != null) {
                character.setPicture(updatedCharacter.getPicture());
            }
            characterService.save(character);
        }

        return new ResponseEntity<>(character, status);
    }

    /**
     * Deletes a character in the database specified by the parameter id using the REST operation DELETE
     * <p>
     * This method returns a boolean and a status indicating if the
     * operation was successful or not.
     *
     * @param id the character primary key
     * @return a boolean and http status indicating if the operation was successful
     */
    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteCharacter(@PathVariable long id){

        boolean deleted = characterService.removeCharacter(id);
        HttpStatus status = (deleted) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(deleted, status);
    }

    private <E> HttpStatus getStatus(E element) {
        return (element == null) ? HttpStatus.NOT_FOUND : HttpStatus.OK;
    }

    private <E> HttpStatus getContentStatus(E element) {
        return (element == null) ? HttpStatus.BAD_REQUEST : HttpStatus.NO_CONTENT;
    }

}
