/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
    public ResponseEntity<?> getResourceXManager() {
		Set<Blueprint> blueprints = null;
		try {
			blueprints = service.getAllBlueprints();
		} catch (Exception e) {
			Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, e);
			return new ResponseEntity<>("No matches found.", HttpStatus.NOT_FOUND);
		}
	}
}