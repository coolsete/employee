package com.io.framey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class})
@Import(AppContext.class)
@ConfigurationPropertiesScan(basePackages = "com.io.framey.properties")
public class FrameyApplication {
	public static void main(String[] args) {
		SpringApplication.run(FrameyApplication.class, args);
	}

}
