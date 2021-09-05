package com.io.framey.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.io.framey.exception.DeserializerException;

import java.io.IOException;
import java.time.LocalDate;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
    static final private String DATE_COULD_NOT_BE_PARSED_EXCEPTION =
            "Date field [%s] with value [%s] could not be parsed";

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        LocalDate value;
        try {
            value = parse(jsonParser.getValueAsString());
        } catch (IOException e) {
            throw new DeserializerException(String.format(DATE_COULD_NOT_BE_PARSED_EXCEPTION,
                    jsonParser.getCurrentName(), jsonParser.getValueAsString()));
        }

        return value;
    }

    private LocalDate parse(String value) {
        return LocalDate.parse(value, ISO_LOCAL_DATE);
    }

}
