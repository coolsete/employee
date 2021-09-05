package com.io.framey.services;

import com.io.framey.common.ResourceReader;
import com.io.framey.properties.PostgreSQLDataSourceProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
public class PostgreSQLTablesInitializer {
    private final ResourceReader resourceReader;
    private final JdbcTemplate pgJdbcTemplate;
    private final PostgreSQLDataSourceProperties properties;

    public PostgreSQLTablesInitializer(ResourceReader resourceReader, JdbcTemplate pgJdbcTemplate,
                                       PostgreSQLDataSourceProperties properties) {
        this.resourceReader = resourceReader;
        this.pgJdbcTemplate = pgJdbcTemplate;
        this.properties = properties;
    }

    public void init() throws RuntimeException {
        if (properties.isInitialize()) {
            final String query = resourceReader.read(properties.getSchema());
            executeScript(query, "Init");
        }
    }

    private void executeScript(String query, String name) {
        try {
            log.debug("{} script:\n{}", name, query);
            pgJdbcTemplate.execute(query);
        } catch (RuntimeException e) {
            log.error(String.format("%s of postgresSQL employee table was failed", name), e);
        }
    }
}
