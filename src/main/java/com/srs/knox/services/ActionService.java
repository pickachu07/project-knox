package com.srs.knox.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.srs.knox.action.utils.CreateActionRequest;
import com.srs.knox.action.utils.CreateActionResponse;
import com.srs.knox.action.utils.Exec;
import com.srs.knox.action.utils.Limits;
import com.srs.knox.action.utils.OWActionRequest;
import com.srs.knox.action.utils.OWActionResponse;
import com.srs.knox.action.utils.UpdateActionRequest;
import com.srs.knox.models.Action;
import com.srs.knox.models.FIU;
import com.srs.knox.repositories.ActionRepo;
import com.srs.knox.repositories.FIURepo;
import com.srs.knox.utils.KnoxException;
import com.srs.knox.utils.OWException;
import com.srs.knox.utils.RestTemplateGenerator;
import com.vdurmont.semver4j.Semver;

@Service
public class ActionService {
	
	@Autowired
	FIURepo fiuRepo;
	@Autowired
	ActionRepo actionRepo;
	@Autowired
	RestTemplateGenerator restTemplateGenerator;
	
	@Value("${openwhisk.baseurl}")
	private String BASE_URL;
	
	Logger logger = LoggerFactory.getLogger(ActionService.class);
	
	public CreateActionResponse createAction(CreateActionRequest requestBody, String key)throws Exception {
		if(Strings.isNullOrEmpty(key)) {
			throw new OWException(HttpStatus.FORBIDDEN, "Invalid API Key.");
		}
		String [] auth = key.split(":");
		if(Strings.isNullOrEmpty(requestBody.getFiuid())) {
			throw new KnoxException(HttpStatus.BAD_REQUEST, "FIU ID is required.");
		} else if(Strings.isNullOrEmpty(requestBody.getActionname())) {
			throw new KnoxException(HttpStatus.BAD_REQUEST, "Action name is required.");
		} else if(Strings.isNullOrEmpty(requestBody.getCode())) {
			throw new KnoxException(HttpStatus.BAD_REQUEST, "Code is required.");
		}
		OWActionRequest body = new OWActionRequest();
		Exec exec = new Exec();
		Limits limits = new Limits();
	    RestTemplate restTemplate = restTemplateGenerator.getRestTemplate();
	    
	    OWActionResponse successResponse = null;
	    CreateActionResponse createActionResponse = null;
	    FIU fiuRecord = null;
		try {
			fiuRecord = fiuRepo.findById(Long.parseLong(requestBody.getFiuid()));
			if(fiuRecord != null) {
				body.setNamespace(fiuRecord.getUsername());
				body.setName(requestBody.getActionname());
				body.setVersion("1.0.0");
				body.setPublish(true);
				exec.setKind(requestBody.getRuntime());
				exec.setCode(requestBody.getCode());
				exec.setMain(requestBody.getMain());
				body.setExec(exec);
				limits.setMemory(Long.parseLong(requestBody.getMemory()));
				limits.setTimeout(Long.parseLong(requestBody.getTimeout()));
				limits.setLogs(10);
				limits.setConcurrency(1);
				body.setLimits(limits);
				
				final String uri = BASE_URL + "/namespaces/" + fiuRecord.getUsername() + "/actions/" + requestBody.getActionname();
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				headers.setBasicAuth(auth[0], auth[1]);
				HttpEntity<String> entity = new HttpEntity<String>(new ObjectMapper().writeValueAsString(body), headers);
				ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.PUT, entity, String.class);
				if(response.getStatusCode() == HttpStatus.OK) {
					successResponse = new ObjectMapper().readValue(response.getBody(), OWActionResponse.class);
				}
			} else {
				throw new KnoxException(HttpStatus.NOT_FOUND, "FIU not found.");
			}
			if(successResponse != null) {
				Action newAction = new Action(fiuRecord.getId(),
						successResponse.getName(), 
						successResponse.getExec().getMain(), 
						successResponse.getExec().getCode(),
						successResponse.getExec().getKind(),
						(int)successResponse.getLimits().getTimeout(),
						(int)successResponse.getLimits().getMemory(), 
						successResponse.getVersion());
				Action actionRecord = actionRepo.save(newAction);
				createActionResponse = new CreateActionResponse(actionRecord.getFiuid(), actionRecord.getId());
			}
		} catch (HttpClientErrorException ex) {
			throw new OWException(ex.getStatusCode(), ex.getResponseBodyAsString());
		} catch (HttpServerErrorException ex) {
			throw new OWException(ex.getStatusCode(), ex.getResponseBodyAsString());
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return createActionResponse; 
	}
	
	public CreateActionResponse updateAction(UpdateActionRequest requestBody, String key) throws Exception {
		CreateActionResponse createActionResponse = null;
		if(Strings.isNullOrEmpty(key)) {
			throw new OWException(HttpStatus.FORBIDDEN, "Invalid API Key.");
		}
		String [] auth = key.split(":");
		if(Strings.isNullOrEmpty(requestBody.getFiuid())) {
			throw new KnoxException(HttpStatus.BAD_REQUEST, "FIU ID is required.");
		} else if(Strings.isNullOrEmpty(requestBody.getActionid())) {
			throw new KnoxException(HttpStatus.BAD_REQUEST, "Action ID is required.");
		} else if(Strings.isNullOrEmpty(requestBody.getCode())) {
			throw new KnoxException(HttpStatus.BAD_REQUEST, "Code is required.");
		}
		Action actionRecord = actionRepo.findById(Long.parseLong(requestBody.getActionid()));
		if(actionRecord != null) {
			if(!actionRecord.isDeleted()) {
				OWActionRequest body = new OWActionRequest();
				Exec exec = new Exec();
				Limits limits = new Limits();
			    RestTemplate restTemplate = restTemplateGenerator.getRestTemplate();
			    
			    OWActionResponse successResponse = null;
			    FIU fiuRecord = null;
			    
			    try {
					fiuRecord = fiuRepo.findById(Long.parseLong(requestBody.getFiuid()));
					if(fiuRecord != null) {
						body.setNamespace(fiuRecord.getUsername());
						body.setName(actionRecord.getName());
						body.setVersion(new Semver(actionRecord.getVersion()).withIncMajor().toString());
						body.setPublish(true);
						exec.setKind(actionRecord.getRuntime());
						exec.setCode(requestBody.getCode());
						exec.setMain(requestBody.getMain());
						body.setExec(exec);
						limits.setMemory(Long.parseLong(requestBody.getMemory()));
						limits.setTimeout(Long.parseLong(requestBody.getTimeout()));
						limits.setLogs(10);
						limits.setConcurrency(1);
						body.setLimits(limits);
						
						final String uri = BASE_URL + "/namespaces/" + fiuRecord.getUsername() + "/actions/" + actionRecord.getName() + "?overwrite=true";
						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.APPLICATION_JSON);
						headers.setBasicAuth(auth[0], auth[1]);
						HttpEntity<String> entity = new HttpEntity<String>(new ObjectMapper().writeValueAsString(body), headers);
						ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.PUT, entity, String.class);
						if(response.getStatusCode() == HttpStatus.OK) {
							successResponse = new ObjectMapper().readValue(response.getBody(), OWActionResponse.class);
						}
					} else {
						throw new KnoxException(HttpStatus.NOT_FOUND, "FIU not found.");
					}
					if(successResponse != null) {
						actionRecord.setMain(successResponse.getExec().getMain());
						actionRecord.setCode(successResponse.getExec().getCode());
						actionRecord.setMemory((int)successResponse.getLimits().getMemory());
						actionRecord.setTimeout((int)successResponse.getLimits().getTimeout());
						actionRecord.setVersion(successResponse.getVersion());
						actionRepo.save(actionRecord);
						createActionResponse = new CreateActionResponse(actionRecord.getFiuid(), actionRecord.getId());
					}
				} catch (HttpClientErrorException ex) {
					throw new OWException(ex.getStatusCode(), ex.getResponseBodyAsString());
				} catch (HttpServerErrorException ex) {
					throw new OWException(ex.getStatusCode(), ex.getResponseBodyAsString());
				}
			} else {
				throw new KnoxException(HttpStatus.NOT_FOUND, "Action not found.");
			}
		} else {
			throw new KnoxException(HttpStatus.NOT_FOUND, "Action not found.");
		}
		return createActionResponse;
	}

	public void activateAction(String actionId, boolean value, String key) throws Exception {
		if(Strings.isNullOrEmpty(key)) {
			throw new OWException(HttpStatus.FORBIDDEN, "Invalid API Key.");
		}
		if(Strings.isNullOrEmpty(actionId)) {
			throw new KnoxException(HttpStatus.BAD_REQUEST, "Action ID is required.");
		}
		Action actionRecord = actionRepo.findById(Long.parseLong(actionId));
		if(actionRecord != null) {
			if(!actionRecord.isDeleted()) {
				actionRecord.setActive(value);
				actionRepo.save(actionRecord);
			} else {
				throw new KnoxException(HttpStatus.NOT_FOUND, "Action not found.");
			}
		} else {
			throw new KnoxException(HttpStatus.NOT_FOUND, "Action not found.");
		}
	}
	
	public void deleteAction(String actionId, String key) throws Exception {
		if(Strings.isNullOrEmpty(key)) {
			throw new OWException(HttpStatus.FORBIDDEN, "Invalid API Key.");
		}
		String [] auth = key.split(":");
		if(Strings.isNullOrEmpty(actionId)) {
			throw new KnoxException(HttpStatus.BAD_REQUEST, "Action ID is required.");
		}
		Action actionRecord = actionRepo.findById(Long.parseLong(actionId));
		if(actionRecord != null) {
			if(!actionRecord.isDeleted()) {
			    FIU fiuRecord = null;
			    try {
					fiuRecord = fiuRepo.findById(actionRecord.getFiuid());
					if(fiuRecord != null) {
						RestTemplate restTemplate = restTemplateGenerator.getRestTemplate();
						final String uri = BASE_URL + "/namespaces/" + fiuRecord.getUsername() + "/actions/" + actionRecord.getName();
						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.APPLICATION_JSON);
						headers.setBasicAuth(auth[0], auth[1]);
						HttpEntity<String> entity = new HttpEntity<String>("", headers);
						ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.DELETE, entity, String.class);
						if(response.getStatusCode() == HttpStatus.OK) {
							actionRecord.setActive(false);
							actionRecord.setDeleted(true);
							actionRepo.save(actionRecord);
						}
					} else {
						throw new KnoxException(HttpStatus.NOT_FOUND, "FIU not found.");
					}
				} catch (HttpClientErrorException ex) {
					throw new OWException(ex.getStatusCode(), ex.getResponseBodyAsString());
				} catch (HttpServerErrorException ex) {
					throw new OWException(ex.getStatusCode(), ex.getResponseBodyAsString());
				}
			} else {
				throw new KnoxException(HttpStatus.NOT_FOUND, "Action not found.");
			}
		} else {
			throw new KnoxException(HttpStatus.NOT_FOUND, "Action not found.");
		}
	}
	
	public Action getAction(String actionId) throws JsonProcessingException {
		return actionRepo.findById(Long.parseLong(actionId));
	}
	
	public List<Action> getAllActions(String fiuId) throws JsonProcessingException {
		return actionRepo.findByFiuid(Long.parseLong(fiuId));
	}

	public List<Action> getAllActiveActions(String fiuId, String active) {
		return actionRepo.findByFiuidAndActive(Long.parseLong(fiuId), Boolean.parseBoolean(active));
	}

	public List<Action> getAllDeletedActions(String fiuId, String deleted) {
		return actionRepo.findByFiuidAndDeleted(Long.parseLong(fiuId), Boolean.parseBoolean(deleted));
	}
}
