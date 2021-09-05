package com.io.framey;

import com.io.framey.properties.PostgreSQLDataSourceProperties;
import com.io.framey.services.EmployeeRepository;
import com.io.framey.services.EmployeeRepositoryImpl;
import com.io.framey.common.IdGenerator;
import com.io.framey.common.IdGeneratorImpl;
import com.io.framey.services.PostgreSQLTablesInitializer;
import com.io.framey.common.ResourceReader;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

import static com.io.framey.Constants.PROD;

@Profile({PROD})
@Configuration
public class AppContext {

    @Bean
    public HikariConfig pgHikariConfig(PostgreSQLDataSourceProperties properties) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(properties.getUrl());
        config.setUsername(properties.getUsername());
        config.setPassword(properties.getPassword());
        config.setDriverClassName(properties.getDriverClassName());
        return config;
    }

    @Bean
    public DataSource pgDataSource(@Qualifier("pgHikariConfig") HikariConfig pgHikariConfig) {
        return new HikariDataSource(pgHikariConfig);
    }


    @Bean
    public EmployeeRepository employeeServices(DataSource pgDataSource, IdGenerator idGenerator) {
        return new EmployeeRepositoryImpl(new NamedParameterJdbcTemplate(pgDataSource), idGenerator);
    }

    @Bean(initMethod = "init")
    public PostgreSQLTablesInitializer postgreSQLTablesInitializer(
            ResourceReader resourceReader,
            NamedParameterJdbcTemplate pgNamedParameterJdbcTemplate,
            PostgreSQLDataSourceProperties properties) {
        return new PostgreSQLTablesInitializer(resourceReader, pgNamedParameterJdbcTemplate.getJdbcTemplate(),
                properties);
    }

    @Bean
    public IdGeneratorImpl idGenerator() {
        return new IdGeneratorImpl();
    }

}
