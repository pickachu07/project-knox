package com.srs.knox.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.srs.knox.events.DataReadyEvent;
import com.srs.knox.execution.utils.ExecutionStatusNotification;
import com.srs.knox.execution.utils.Notifier;
import com.srs.knox.execution.utils.ORNotificationRequest;
import com.srs.knox.models.Action;
import com.srs.knox.models.Execution;
import com.srs.knox.models.FIU;
import com.srs.knox.models.Status;
import com.srs.knox.repositories.ActionRepo;
import com.srs.knox.repositories.ExecutionRepo;
import com.srs.knox.repositories.FIURepo;
import com.srs.knox.utils.HashMapConverter;
import com.srs.knox.utils.OWException;
import com.srs.knox.utils.RestTemplateGenerator;

@Component
public class ExecutionListener {
	
	@Value("${delay}")
	private long DELAY;
	
	@Autowired
	FIURepo fiuRepo;
	@Autowired
	ActionRepo actionRepo;
	@Autowired
	ExecutionRepo execRepo;
	@Autowired
	RestTemplateGenerator restTemplateGenerator;
	@Autowired
	HashMapConverter hashMapConverter;
	
	@Value("${openwhisk.baseurl}")
	private String OW_BASE_URL;
	
	@Value("${aa.baseurl}")
	private String AA_BASE_URL;
	
	@Value("${aa.apikey}")
	private String AA_API_KEY;
	
	private Execution execRecord;
	private FIU fiuRecord;
	private Action actionRecord;
	private String [] auth;
	private RestTemplate restTemplate;
	
	@Async
	@EventListener
	public void notificationListener(DataReadyEvent event) throws InterruptedException {
		this.execRecord = (Execution) event.getSource();
		this.fiuRecord = fiuRepo.findById(execRecord.getFiuid());
		this.actionRecord = actionRepo.findById(execRecord.getActionid());
		this.auth = fiuRecord.getApiKey().split(":");
		this.restTemplate = restTemplateGenerator.getRestTemplate();
		
		try {
			HashMap<String, Object> fiFetchResponse = FIFetch();
			ArrayList<Object> FI = (ArrayList<Object>) fiFetchResponse.get(new String("FI"));
			HashMap<String, Object> FIAtZeroIndex = (HashMap<String, Object>) FI.get(0);
			ArrayList<Object> data = (ArrayList<Object>) FIAtZeroIndex.get(new String("data"));
			HashMap<String, Object> dataAtZeroindex = (HashMap<String, Object>) data.get(0);
			HashMap<String, Object> encryptedfi = (HashMap<String, Object>) dataAtZeroindex.get("encryptedFI");
			HashMap<String, Object> decryptedfi = decrypt(encryptedfi);
			String activationID = invokeAction(decryptedfi);
			Thread.sleep(actionRecord.getTimeout() + 10);
			HashMap<String, Object> activationResponse = fetchOutput(activationID);
			execRecord.setStatus(Status.EXECUTED);
			execRecord.setLastupdated(LocalDateTime.now());
			execRepo.save(execRecord);
			Thread.sleep(DELAY);
			HashMap<String, Object> output = (HashMap<String, Object>) activationResponse.get(new String("response"));
			HashMap<String, Object> result = (HashMap<String, Object>) output.get(new String("result"));
			boolean isValidOutput = false;
			if((Boolean) output.get(new String("status")).equals("success")) {
				isValidOutput = isValidOutput(result);
				if(isValidOutput) {
					execRecord.setOutput(output);
				} else {
					HashMap<String, Object> invalidOutput = new HashMap<String, Object>();
					invalidOutput.put("status","action rule violation");
					invalidOutput.put("size", output.get("size"));
					HashMap<String, String> errorResult = new HashMap<String, String>();
					errorResult.put("errormsg", "Output JSON violates boolean only rule.");
					invalidOutput.put("result", errorResult);
					invalidOutput.put("success", false);
					execRecord.setOutput(invalidOutput);
				}
			} else {
				execRecord.setOutput(output);
			}
			HashMap<String, Object> metadata = new HashMap<String, Object>();
			metadata.put("start", activationResponse.get("start"));
			metadata.put("end", activationResponse.get("end"));
			metadata.put("duration", activationResponse.get("duration"));
			metadata.put("logs", activationResponse.get("logs"));
			metadata.put("cause", activationResponse.get("cause"));
			metadata.put("statusCode", activationResponse.get("statusCode"));
			execRecord.setMetadata(metadata);
			execRecord.setStatus(Status.OUTPUT_READY);
			execRecord.setTtl(86400);
			execRecord.setLastupdated(LocalDateTime.now());
			execRepo.save(execRecord);
			Thread.sleep(DELAY);
			notifyAA();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private HashMap<String, Object> FIFetch() throws Exception {
		HashMap<String, Object> successResponse = null;
		try {
			final String uri = AA_BASE_URL + "/FI/fetch/" + execRecord.getFisessionid();
			HttpHeaders headers = new HttpHeaders();
			headers.add("client_api_key", AA_API_KEY);
			HttpEntity<String> entity = new HttpEntity<String>("", headers);
			ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
			if(response.getStatusCode() == HttpStatus.OK) {
				successResponse = (HashMap<String, Object>) hashMapConverter.convertToEntityAttribute(response.getBody());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return successResponse;
	}
	
	private HashMap<String, Object> decrypt(HashMap<String, Object> encryptedfi) {
		//TODO Implement decryption logic
		return encryptedfi;
	}
	
	private String invokeAction(HashMap<String, Object> fi) throws Exception {
		String activationID = null;
		try {
			final String uri = OW_BASE_URL + "/namespaces/" + fiuRecord.getUsername() + "/actions/" + actionRecord.getName();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBasicAuth(auth[0], auth[1]);
			HttpEntity<String> entity = new HttpEntity<String>(new ObjectMapper().writeValueAsString(fi), headers);
			System.out.println(entity.getBody());
			ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
			if(response.getStatusCode() == HttpStatus.ACCEPTED) {
				activationID = (String) ((HashMap<String, Object>) hashMapConverter.convertToEntityAttribute(response.getBody())).get(new String("activationId"));
			}
		} catch (HttpClientErrorException ex) {
			throw new OWException(ex.getStatusCode(), ex.getResponseBodyAsString());
		} catch (HttpServerErrorException ex) {
			throw new OWException(ex.getStatusCode(), ex.getResponseBodyAsString());
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return activationID;
	}
	
	private HashMap<String, Object> fetchOutput(String activationID) throws Exception {
		HashMap<String, Object> successResponse = null;
		try {
			final String uri = OW_BASE_URL + "/namespaces/" + fiuRecord.getUsername() + "/activations/" + activationID;
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBasicAuth(auth[0], auth[1]);
			HttpEntity<String> entity = new HttpEntity<String>("", headers);
			ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
			if(response.getStatusCode() == HttpStatus.OK) {
				successResponse = (HashMap<String, Object>) hashMapConverter.convertToEntityAttribute(response.getBody());
			}
		} catch (HttpClientErrorException ex) {
			throw new OWException(ex.getStatusCode(), ex.getResponseBodyAsString());
		} catch (HttpServerErrorException ex) {
			throw new OWException(ex.getStatusCode(), ex.getResponseBodyAsString());
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return successResponse;
	}
	
	private boolean isValidOutput(HashMap<String, Object> output) {
		for (HashMap.Entry<String, Object> set : output.entrySet()) {
			Object value = set.getValue();
		    if(value instanceof Boolean) {
		    	continue;
		    } else if(value instanceof String) {
		    	try {
		    		Boolean.parseBoolean((String) value);
		    	} catch (Exception ex) {
		    		return false;
		    	}
		    } else {
		    	return false;
		    }
		}
		return true;
	}
	
	private void notifyAA() throws Exception {
		ExecutionStatusNotification notification = new ExecutionStatusNotification(execRecord.getFisessionid(), 
				execRecord.getId().toString(), execRecord.getStatus().toString());
		ORNotificationRequest body = new ORNotificationRequest(new Notifier(), notification);
		try {
			final String uri = AA_BASE_URL + "/output/notification";
			HttpHeaders headers = new HttpHeaders();
			headers.add("client_api_key", AA_API_KEY);
			HttpEntity<String> entity = new HttpEntity<String>(new ObjectMapper().writeValueAsString(body), headers);
			restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}
}
