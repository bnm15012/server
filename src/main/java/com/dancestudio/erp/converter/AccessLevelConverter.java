package com.dancestudio.erp.converter;

import com.dancestudio.erp.enums.AccessLevel;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class AccessLevelConverter implements AttributeConverter<AccessLevel, Integer> {

    @Override
    public Integer convertToDatabaseColumn(AccessLevel attribute) {
        return attribute != null ? attribute.getCode() : 0;
    }

    @Override
    public AccessLevel convertToEntityAttribute(Integer dbData) {
        return dbData != null ? AccessLevel.fromCode(dbData) : AccessLevel.NONE;
    }
}
