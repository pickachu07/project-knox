package com.srs.knox.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.srs.knox.events.DataReadyEvent;
import com.srs.knox.execution.utils.DHPublicKey;
import com.srs.knox.execution.utils.FINotificationRequest;
import com.srs.knox.execution.utils.InvokeExecutionRequest;
import com.srs.knox.execution.utils.InvokeExecutionResponse;
import com.srs.knox.execution.utils.KeyMaterial;
import com.srs.knox.execution.utils.OutputFetchResponse;
import com.srs.knox.models.Action;
import com.srs.knox.models.Execution;
import com.srs.knox.models.Status;
import com.srs.knox.repositories.ActionRepo;
import com.srs.knox.repositories.ExecutionRepo;
import com.srs.knox.utils.KnoxException;

@Service
public class ExecutionService {
	
	@Value("${delay}")
	private long DELAY;
	
	@Autowired
	ExecutionRepo execRepo;
	@Autowired
	ActionRepo actionRepo;
	@Autowired
	ApplicationEventPublisher appEventPublisher;
	
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
			if(action != null && action.getFiuid() == fiuid && !action.isDeleted()) {
				if(action.isActive()) {
					Execution execution = new Execution(Status.DATA_PENDING, fiuid, actionid, fisessionid, LocalDateTime.now());
					Execution executionRecord = execRepo.save(execution);
					Thread.sleep(DELAY);
					if(executionRecord != null) {
						response = new InvokeExecutionResponse();
						response.setVer("1.0");
						response.setTimestamp(executionRecord.getLastupdated().toString());
						response.setTxnid(UUID.randomUUID().toString());
						response.setSessionid(fisessionid);
						response.setExecutionid(executionRecord.getId().toString());
					}
				} else {
					throw new KnoxException(HttpStatus.BAD_REQUEST, "Action is inactive.");
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
				Thread.sleep(DELAY);
				DataReadyEvent event = new DataReadyEvent(executionRecord);
				appEventPublisher.publishEvent(event);
			} else {
				throw new KnoxException(HttpStatus.NOT_FOUND, "Execution Record not found.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return;
	}

	public OutputFetchResponse fetchOutput(String executionid, String key) throws Exception {
		OutputFetchResponse response = null;
		try {
			if(Strings.isNullOrEmpty(key)) {
				throw new KnoxException(HttpStatus.FORBIDDEN, "Invalid API Key.");
			}
			if(Strings.isNullOrEmpty(executionid)) {
				throw new KnoxException(HttpStatus.BAD_REQUEST, "Execution ID is required.");
			}
			Execution executionRecord = execRepo.findById(UUID.fromString(executionid));
			if(executionRecord != null) {
				if (executionRecord.getStatus() == Status.OUTPUT_READY) {
					HashMap<String, Object> output = new HashMap<String, Object>();
					output.put("response", executionRecord.getOutput());
					
					// Adding mock encryption information
					KeyMaterial keyMaterial = new KeyMaterial();
					keyMaterial.setCryptoAlg("ECDHE");
					keyMaterial.setCurve("Curve25519");
					keyMaterial.setParams("Some Params");
					DHPublicKey publicKey = new DHPublicKey();
					publicKey.setExpiry("2020-08-02T20:45:16.681Z");
					publicKey.setParameters("Some Params");
					publicKey.setKeyValue("-----BEGIN PUBLIC KEY-----MIIBMTCB6gYHKoZIzj0CATCB3gIBATArBgcqhkjOPQEB"
							+ "AiB/////////////////////////////////////////7TBEBCAqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq"
							+ "qqqqYSRShRAQge0Je0Je0Je0Je0Je0Je0Je0Je0Je0Je0JgtenHcQyGQEQQQqqqqqqqqqqqqqqqqqqqqqqq"
							+ "qqqqqqqqqqqqqqqq0kWiCuGaG4oIa04B7dLHdI0UySPU1+bXxhsinpxaJ+ztPZAiAQAAAAAAAAAAAAAAAAA"
							+ "AAAFN753qL3nNZYEmMaXPXT7QIBCANCAARXDhD4L9wYikmlHHybnW28Df57nuJkYNGiLvWbF/GsxlS0SkLs"
							+ "DVo7mdT0mYzygYlck5Sd9eJPhTRE2u9OABDS-----END PUBLIC KEY-----");
					keyMaterial.setDHPublicKey(publicKey);
					keyMaterial.setNonce("R4s6vNI7I/JfdeA3/6dMMQ==");
					keyMaterial.setSignature("YEmvHkhEAhJV2ljLj5GiDUbGjUBl034lF6QkfJp+HUbsXPBShN2LXns9XynPFwHGIpj5"
							+ "HxBUy8ZqbX0lllqFcbTDUViUCY6VxZ1ghXjx+fihtVuhgEfRBwWingWtT9rRXU1idH/22iwPkw8VPJ5yq1W"
							+ "0OeuNv4tZoK+Z2oYNDGXoG4KzrFJjKFKGeNpkScGPp4+eJ090yc6xeszX9s6zxyAiQKHpm3xuYYBCYeaG8F"
							+ "Yh9OCLYPASfMuCvAKQtxdXTYegE8XDAOFLOnteosj/3jMSb7afZN161lCNLYBCh4OWJY+LocaHflsNvxJn8"
							+ "y7BYwXiSjjjFfMHgA75rV7m");
					output.put("KeyMaterial", keyMaterial);
					
					response = new OutputFetchResponse(executionRecord.getFisessionid(), output);
					executionRecord.setStatus(Status.OUTPUT_FETCHED);
					executionRecord.setOutput(null);
					executionRecord.setArchived(true);
					executionRecord.setLastupdated(LocalDateTime.now());
					execRepo.save(executionRecord);
				} else {
					throw new KnoxException(HttpStatus.BAD_REQUEST, "Execution Record has not completed yet.");
				}
			} else {
				throw new KnoxException(HttpStatus.NOT_FOUND, "Execution Record not found.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return response;
	}

	public Execution getExecution(String executionId) {
		return execRepo.findById(UUID.fromString(executionId));
	}

	public List<Execution> getAllExecutionsByFiuid(String fiuId) {
		return execRepo.findByFiuid(Long.parseLong(fiuId));
	}

	public List<Execution> getAllExecutionsByActionid(String actionId) {
		return execRepo.findByActionid(Long.parseLong(actionId));
	}

	public List<Execution> getAllExecutionsByFiuid(String fiuId, boolean archived) {
		return execRepo.findByFiuidAndArchived(Long.parseLong(fiuId), archived);
	}
	
	public List<Execution> getAllExecutionsByActionid(String actionId, boolean archived) {
		return execRepo.findByActionidAndArchived(Long.parseLong(actionId), archived);
	}

	public long getAvgExecutionTimeByFiuid(String fiuid) {
		List<Execution> executions = getAllExecutionsByFiuid(fiuid);
		long avgTime = 0;
		if(executions != null && !executions.isEmpty()) {
			long totalTime = 0;
			for(Execution execution : executions) {
				try {
					int duration = (int) execution.getMetadata().get("duration");
					totalTime +=  duration;
				} catch (Exception ex) {
					totalTime += 0;
				}
			}
			avgTime = totalTime/executions.size();
		}
		return avgTime;
	}
}
