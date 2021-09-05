package com.io.framey.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "spring.datasource")
@Getter
@Setter
public class PostgreSQLDataSourceProperties {
    private String url;
    private String username;
    private String password;
    private boolean initialize;
    private String schema;
    private String driverClassName;
}
