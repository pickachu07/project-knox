package com.srs.knox.utils;

import java.io.IOException;
import java.util.Map;

import javax.persistence.AttributeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HashMapConverter implements AttributeConverter<Map<String, Object>, String> {
	 
	Logger logger = LoggerFactory.getLogger(HashMapConverter.class);
    @Override
    public String convertToDatabaseColumn(Map<String, Object> outputObject) {
 
        String outputJSON = null;
        try {
        	ObjectMapper objectMapper = new ObjectMapper();
        	outputJSON = objectMapper.writeValueAsString(outputObject);
        } catch (final JsonProcessingException e) {
            logger.error("HashMapConverter: JSON writing error", e);
        }
 
        return outputJSON;
    }
 
	@Override
    public Map<String, Object> convertToEntityAttribute(String outputJSON) {
 
        Map<String, Object> outputObject = null;
        try {
        	ObjectMapper objectMapper = new ObjectMapper();
        	outputObject = objectMapper.readValue(outputJSON, Map.class);
        } catch (final IOException e) {
            logger.error("HashMapConverter: JSON reading error", e);
        }
 
        return outputObject;
    }
}