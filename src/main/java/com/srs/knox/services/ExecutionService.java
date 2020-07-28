package com.srs.knox.services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.srs.knox.execution.utils.FINotificationRequest;
import com.srs.knox.execution.utils.InvokeExecutionRequest;
import com.srs.knox.execution.utils.InvokeExecutionResponse;
import com.srs.knox.models.Action;
import com.srs.knox.models.Execution;
import com.srs.knox.models.Status;
import com.srs.knox.repositories.ActionRepo;
import com.srs.knox.repositories.ExecutionRepo;
import com.srs.knox.utils.KnoxException;

@Service
public class ExecutionService {
	
	@Autowired
	ExecutionRepo execRepo;
	@Autowired
	ActionRepo actionRepo;
	
	Logger logger = LoggerFactory.getLogger(ExecutionService.class);

	public InvokeExecutionResponse invokeExecution(InvokeExecutionRequest requestBody, String key) throws Exception {
		InvokeExecutionResponse response = null;
		try {
			if(Strings.isNullOrEmpty(key)) {
				throw new KnoxException(HttpStatus.FORBIDDEN, "Invalid API Key.");
			}
			if(Strings.isNullOrEmpty(requestBody.getActionid())) {
				throw new KnoxException(HttpStatus.BAD_REQUEST, "Action ID is required.");
			} else if(Strings.isNullOrEmpty(requestBody.getFiuid())) {
				throw new KnoxException(HttpStatus.BAD_REQUEST, "FIU ID is required.");
			} else if(Strings.isNullOrEmpty(requestBody.getSessionId())) {
				throw new KnoxException(HttpStatus.BAD_REQUEST, "Session ID  is required.");
			}
			long actionid = Long.parseLong(requestBody.getActionid());
			long fiuid = Long.parseLong(requestBody.getFiuid());
			String fisessionid = requestBody.getSessionId();
			Action action = actionRepo.findById(actionid);
			if(action != null && action.getFiuid() == fiuid) {
				Execution execution = new Execution(Status.DATA_PENDING, fiuid, actionid, fisessionid, LocalDateTime.now());
				Execution executionRecord = execRepo.save(execution);
				if(executionRecord != null) {
					response = new InvokeExecutionResponse();
					response.setVer("1.0");
					response.setTimestamp(executionRecord.getLastUpdated().toString());
					response.setTxnid(UUID.randomUUID().toString());
					response.setExecutionid(executionRecord.getId().toString());
				}
			} else {
				throw new KnoxException(HttpStatus.NOT_FOUND, "Action not found.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return response;
	}

	public void notify(FINotificationRequest requestBody, String key) throws Exception {
		try {
			if(Strings.isNullOrEmpty(key)) {
				throw new KnoxException(HttpStatus.FORBIDDEN, "Invalid API Key.");
			}
			if(Strings.isNullOrEmpty(requestBody.getExecutionid())) {
				throw new KnoxException(HttpStatus.BAD_REQUEST, "Execution ID is required.");
			}
			String executionid = requestBody.getExecutionid();
			Execution executionRecord = execRepo.findById(UUID.fromString(executionid));
			if(executionRecord != null) {
				executionRecord.setStatus(Status.DATA_READY);
				execRepo.save(executionRecord);
			} else {
				throw new KnoxException(HttpStatus.NOT_FOUND, "Execution Record not found.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return;
	}
}
