package com.srs.knox.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.srs.knox.execution.utils.FINotificationRequest;
import com.srs.knox.execution.utils.InvokeExecutionRequest;
import com.srs.knox.execution.utils.InvokeExecutionResponse;
import com.srs.knox.execution.utils.OutputFetchResponse;
import com.srs.knox.models.Execution;
import com.srs.knox.services.ExecutionService;
import com.srs.knox.utils.KnoxException;

@RestController
@RequestMapping(value="/execution", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExecutionController {
	
Logger logger = LoggerFactory.getLogger(ExecutionController.class);
	
	@Autowired
	ExecutionService executionService;
	
	@PostMapping(value="/invoke")
	@ResponseBody
	public ResponseEntity<String> invokeFunction(@RequestBody InvokeExecutionRequest requestBody, @RequestHeader("vdpr_api_key") String key) {
		try {
			InvokeExecutionResponse response = executionService.invokeExecution(requestBody, key);
			return new ResponseEntity<String>(new ObjectMapper().writeValueAsString(response), HttpStatus.OK);
		} catch (KnoxException ex) {
			logger.error("Invoke Execution Error: " + ex.getLocalizedMessage());
			return new ResponseEntity<String>(ex.getMessage(), ex.getStatus());
		} catch (Exception ex) {
			logger.error("Invoke Execution Error: " + ex.getLocalizedMessage());
			return new ResponseEntity<String>(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value="/notification")
	@ResponseBody
	public ResponseEntity<String> notify(@RequestBody FINotificationRequest requestBody, @RequestHeader("vdpr_api_key") String key) {
		try {
			executionService.notify(requestBody, key);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (KnoxException ex) {
			logger.error("Invoke Execution Error: " + ex.getLocalizedMessage());
			return new ResponseEntity<String>(ex.getMessage(), ex.getStatus());
		} catch (Exception ex) {
			logger.error("Invoke Execution Error: " + ex.getLocalizedMessage());
			return new ResponseEntity<String>(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value="/fetch/{executionid}")
	@ResponseBody
	public ResponseEntity<String> fetchOutput(@PathVariable String executionid, @RequestHeader("vdpr_api_key") String key) {
		try {
			OutputFetchResponse response = executionService.fetchOutput(executionid, key);
			return new ResponseEntity<String>(new ObjectMapper().writeValueAsString(response), HttpStatus.OK);
		} catch (KnoxException ex) {
			logger.error("Invoke Execution Error: " + ex.getLocalizedMessage());
			return new ResponseEntity<String>(ex.getMessage(), ex.getStatus());
		} catch (Exception ex) {
			logger.error("Invoke Execution Error: " + ex.getLocalizedMessage());
			return new ResponseEntity<String>(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path="/get/{executionid}")
	@ResponseBody
	public ResponseEntity<String> getExecution(@PathVariable String executionid, @RequestHeader("vdpr_api_key") String key) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Execution execution = executionService.getExecution(executionid);
			if(execution != null) {
				return new ResponseEntity<String>(objectMapper.writeValueAsString(execution), HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("Execution not found.", HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			logger.error("Get Action: Error " + ex.getLocalizedMessage());
			return new ResponseEntity<String>(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path="/getAll/fiu/{fiuid}")
	@ResponseBody
	public ResponseEntity<String> getAllExecutionsByFiuid(@PathVariable String fiuid, @RequestParam(required = false) String archived, @RequestHeader("vdpr_api_key") String key) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			List<Execution> executions = null;
			if(!Strings.isNullOrEmpty(fiuid) && Strings.isNullOrEmpty(archived)) {
				executions = executionService.getAllExecutionsByFiuid(fiuid);
			} else if (!Strings.isNullOrEmpty(fiuid) && !Strings.isNullOrEmpty(archived)) {
				executions = executionService.getAllExecutionsByFiuid(fiuid, Boolean.parseBoolean(archived));
			}
			if(executions != null) {
				return new ResponseEntity<String>(objectMapper.writeValueAsString(executions), HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("No executions found.", HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			logger.error("Get Action: Error " + ex.getLocalizedMessage());
			return new ResponseEntity<String>(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path="/getAll/action/{actionid}")
	@ResponseBody
	public ResponseEntity<String> getAllExecutionsByActionid(@PathVariable String actionid, @RequestParam(required = false) String archived, @RequestHeader("vdpr_api_key") String key) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			List<Execution> executions = null;
			if(!Strings.isNullOrEmpty(actionid) && Strings.isNullOrEmpty(archived)) {
				executions = executionService.getAllExecutionsByActionid(actionid);
			} else if (!Strings.isNullOrEmpty(actionid) && !Strings.isNullOrEmpty(archived)) {
				executions = executionService.getAllExecutionsByActionid(actionid, Boolean.parseBoolean(archived));
			}
			if(executions != null) {
				return new ResponseEntity<String>(objectMapper.writeValueAsString(executions), HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("No executions found.", HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			logger.error("Get Action: Error " + ex.getLocalizedMessage());
			return new ResponseEntity<String>(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
