package com.io.framey.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

public class LocalDateSerializer extends JsonSerializer<LocalDate> {
    @Override
    public void serialize(LocalDate value, JsonGenerator gen,
                          SerializerProvider serializerProvider) throws IOException {
        if (value == null) {
            serializerProvider.defaultSerializeNull(gen);
        } else {
            serializerProvider.defaultSerializeValue(convertLocalDateToString(value), gen);
        }
    }

    private String convertLocalDateToString(LocalDate localDate) {
        return localDate == null ? null : ISO_LOCAL_DATE.format(localDate);
    }
}
