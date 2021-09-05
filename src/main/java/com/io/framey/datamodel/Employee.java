package com.io.framey.datamodel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.io.framey.common.LocalDateDeserializer;
import com.io.framey.common.LocalDateSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Employee {
    private long id;
    private String name;
    private long position;
    private long superior;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate startDate;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate endDate;

    @JsonCreator
    public Employee(){}

    public Employee(long id,
                    String name,
                    long position,
                    long superior,
                    String startDate,
                    String endDate) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.superior = superior;
        this.startDate = LocalDate.parse(startDate);
        this.endDate = LocalDate.parse(endDate);
    }
}
