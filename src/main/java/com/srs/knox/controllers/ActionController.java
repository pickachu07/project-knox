package com.srs.knox.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.srs.knox.action.utils.CreateActionRequest;
import com.srs.knox.action.utils.CreateActionResponse;
import com.srs.knox.action.utils.UpdateActionRequest;
import com.srs.knox.models.Action;
import com.srs.knox.services.ActionService;
import com.srs.knox.utils.OWException;

@RestController
@RequestMapping(value="/action", produces = MediaType.APPLICATION_JSON_VALUE)
public class ActionController {
	
	Logger logger = LoggerFactory.getLogger(ActionController.class);
	
	@Autowired
	ActionService actionService;
	
	@PostMapping(value="/create")
	@ResponseBody
	public ResponseEntity<String> createAction(@RequestBody CreateActionRequest requestBody, @RequestHeader("vdpr_api_key") String key) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			CreateActionResponse response = actionService.createAction(requestBody, key);
			return new ResponseEntity<String>(objectMapper.writeValueAsString(response), HttpStatus.OK);
		} catch (OWException ex) {
			logger.error("OpenWhisk Exception: " + ex.getLocalizedMessage());
			return new ResponseEntity<String>(ex.getMessage(), ex.getStatus());
		} catch (Exception ex) {
			logger.error("Create Action: Error " + ex.getLocalizedMessage());
			return new ResponseEntity<String>(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(value="/update")
	@ResponseBody
	public ResponseEntity<String> updateAction(@RequestBody UpdateActionRequest requestBody, @RequestHeader("vdpr_api_key") String key) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			CreateActionResponse response = actionService.updateAction(requestBody, key);
			return new ResponseEntity<String>(objectMapper.writeValueAsString(response), HttpStatus.OK);
		} catch (OWException ex) {
			logger.error("OpenWhisk Exception: " + ex.getLocalizedMessage());
			return new ResponseEntity<String>(ex.getMessage(), ex.getStatus());
		} catch (Exception ex) {
			logger.error("Create Action: Error " + ex.getLocalizedMessage());
			return new ResponseEntity<String>(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value="/active/{actionid}")
	@ResponseBody
	public ResponseEntity<String> activateAction(@PathVariable String actionid, @RequestParam boolean value, @RequestHeader("vdpr_api_key") String key) {
		try {
			actionService.activateAction(actionid, value, key);
			return new ResponseEntity<String>("", HttpStatus.OK);
		} catch (OWException ex) {
			logger.error("OpenWhisk Exception: " + ex.getLocalizedMessage());
			return new ResponseEntity<String>(ex.getMessage(), ex.getStatus());
		} catch (Exception ex) {
			logger.error("Create Action: Error " + ex.getLocalizedMessage());
			return new ResponseEntity<String>(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(value="/delete/{actionid}")
	@ResponseBody
	public ResponseEntity<String> deleteAction(@PathVariable String actionid, @RequestHeader("vdpr_api_key") String key) {
		try {
			actionService.deleteAction(actionid, key);
			return new ResponseEntity<String>("", HttpStatus.OK);
		} catch (OWException ex) {
			logger.error("OpenWhisk Exception: " + ex.getLocalizedMessage());
			return new ResponseEntity<String>(ex.getMessage(), ex.getStatus());
		} catch (Exception ex) {
			logger.error("Create Action: Error " + ex.getLocalizedMessage());
			return new ResponseEntity<String>(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path="/get/{actionId}")
	@ResponseBody
	public ResponseEntity<String> getAction(@PathVariable String actionId, @RequestHeader("vdpr_api_key") String key) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Action action = actionService.getAction(actionId);
			if(action != null) {
				return new ResponseEntity<String>(objectMapper.writeValueAsString(action), HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("Action not found.", HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			logger.error("Get Action: Error " + ex.getLocalizedMessage());
			return new ResponseEntity<String>(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path="/getAll/{fiuId}")
	@ResponseBody
	public ResponseEntity<String> getAllActions(@PathVariable String fiuId, @RequestParam(required = false) String active, @RequestParam(required = false) String deleted, @RequestHeader("vdpr_api_key") String key) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			List<Action> actions = null;
			if(!Strings.isNullOrEmpty(fiuId) && Strings.isNullOrEmpty(active) && Strings.isNullOrEmpty(deleted)) {
				actions = actionService.getAllActions(fiuId);
			} else if(!Strings.isNullOrEmpty(fiuId) && !Strings.isNullOrEmpty(active) && Strings.isNullOrEmpty(deleted)) {
				actions = actionService.getAllActiveActions(fiuId, active);
			} else if(!Strings.isNullOrEmpty(fiuId) && Strings.isNullOrEmpty(active) && !Strings.isNullOrEmpty(deleted)) {
				actions = actionService.getAllDeletedActions(fiuId, deleted); 
			} else {
				return new ResponseEntity<String>("", HttpStatus.BAD_REQUEST);
			}
			if(actions != null && !actions.isEmpty()) {
				return new ResponseEntity<String>(objectMapper.writeValueAsString(actions), HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("Actions not found.", HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			logger.error("Get Action: Error " + ex.getLocalizedMessage());
			return new ResponseEntity<String>(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
