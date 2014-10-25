package net.foodmanager.jpa;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author fort
 */
@Converter
public class LocalDateStringConverter implements AttributeConverter<LocalDate, String> {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/uuuu");

    @Override
    public String convertToDatabaseColumn(LocalDate attribute) {
        return attribute.format(formatter);
    }

    @Override
    public LocalDate convertToEntityAttribute(String dbData) {
        return LocalDate.parse(dbData, formatter);
    }
}
