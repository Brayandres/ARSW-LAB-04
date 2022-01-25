/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.services.BlueprintsServices;

/**
 *
 * @author hcadavid
 */

@Service
@RestController
@RequestMapping(value = "/blueprints")
public class BlueprintAPIController {
	
	@Autowired
	private BlueprintsServices service;
    
	
	@RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getBlueprintsManager() {
		Set<Blueprint> blueprints = null;
		try {
			blueprints = service.getAllBlueprints();
		} catch (Exception e) {
			Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, e);
			return new ResponseEntity<>("Not matches found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(blueprints, HttpStatus.ACCEPTED);
	}

	@RequestMapping(method = RequestMethod.GET, path = "{author}")
	public ResponseEntity<?> getBlueprintsByAuthorManger(@PathVariable String author){
		Set<Blueprint> blueprintsByAuthor = null;
		try {
			blueprintsByAuthor = service.getBlueprintsByAuthor(author);
		} catch (Exception e) {
			Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, e);
			return new ResponseEntity<>("Author '"+author+"' has not been found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(blueprintsByAuthor, HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "{author}/{bpname}")
	public ResponseEntity<?> getAnBluePrintManager(@PathVariable String author, @PathVariable String bpname) {
		Blueprint blueprintByNameAndAuthor = null;
		try {
			blueprintByNameAndAuthor = service.getBlueprint(author, bpname);
		} catch (Exception e) {
			Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, e);
			return new ResponseEntity<>("The plane named '"+bpname+"' has not been found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(blueprintByNameAndAuthor, HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> postBlueprintManager(@RequestBody Blueprint newblueprint) {
		try {
			service.addNewBlueprint(newblueprint);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, e);
			return new ResponseEntity<>("Action has been rejected.", HttpStatus.FORBIDDEN);
		}
	}

	@RequestMapping(method = RequestMethod.PUT,path = "{author}/{bpname}")	
	public ResponseEntity<?> putBlueprintManager(@PathVariable String author, @RequestBody String bpname){
	    try {
	        service.getBlueprint(author, bpname);
	        return new ResponseEntity<>(HttpStatus.CREATED);
	    } catch (Exception ex) {
	        Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
	        return new ResponseEntity<>("Error 400",HttpStatus.NOT_FOUND);        
	    }        

	}
}