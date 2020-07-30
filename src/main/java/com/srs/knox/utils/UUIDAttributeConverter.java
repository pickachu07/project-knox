package com.srs.knox.utils;

import java.util.UUID;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter()
public class UUIDAttributeConverter implements AttributeConverter<UUID, String> {

    @Override
    public String convertToDatabaseColumn(UUID uuid) {
        return (uuid == null ? null : uuid.toString());
    }

    @Override
    public UUID convertToEntityAttribute(String uuid) {
        return (uuid == null ? null : UUID.fromString(uuid));
    }
}
