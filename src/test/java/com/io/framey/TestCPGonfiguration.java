package com.io.framey;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.io.framey.services.EmployeeRepository;
import com.io.framey.services.EmployeeRepositoryImpl;
import com.io.framey.common.IdGeneratorImpl;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.io.framey.Constants.IT;


@Profile({IT})
@TestConfiguration
@Testcontainers
public class TestCPGonfiguration {

    @Bean
    public HikariConfig pgHikariTestConfig(JdbcDatabaseContainer<?> jdbcDatabaseContainer) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(jdbcDatabaseContainer.getJdbcUrl());
        hikariConfig.setUsername(jdbcDatabaseContainer.getUsername());
        hikariConfig.setPassword(jdbcDatabaseContainer.getPassword());
        hikariConfig.setDriverClassName("org.postgresql.Driver");
        return hikariConfig;
    }

    @Bean
    public EmployeeRepository testRepository(@Qualifier("pgHikariTestConfig") HikariConfig hikariConfig) {
       return new EmployeeRepositoryImpl(new NamedParameterJdbcTemplate(new HikariDataSource(hikariConfig)),
                new IdGeneratorImpl());
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public JdbcDatabaseContainer<?> jdbcDatabaseContainer() {
        return new PostgreSQLContainer<>("postgres")
                .withUsername("test")
                .withPassword("postgres_test")
                .withDatabaseName("test")
                .withExposedPorts(5432)
                .withInitScript("init-postgresql.sql")
                .waitingFor(Wait.forListeningPort());
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return mapper;
    }
}
