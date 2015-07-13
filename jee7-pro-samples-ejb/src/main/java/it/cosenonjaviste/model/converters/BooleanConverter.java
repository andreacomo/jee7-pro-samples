package it.cosenonjaviste.model.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class BooleanConverter implements AttributeConverter<Boolean, String> {
    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return attribute != null ? (attribute ? "Y" : "N") : null;
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return dbData != null ? "Y".equals(dbData) : null;
    }
}
