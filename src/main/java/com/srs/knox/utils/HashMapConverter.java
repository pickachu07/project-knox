package com.srs.knox.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Converter
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
    public HashMap<String, Object> convertToEntityAttribute(String outputJSON) {
 
        HashMap<String, Object> outputObject = null;
        try {
        	ObjectMapper objectMapper = new ObjectMapper();
        	outputObject = objectMapper.readValue(outputJSON, HashMap.class);
        } catch (final IOException e) {
            logger.error("HashMapConverter: JSON reading error", e);
        }
 
        return outputObject;
    }
}