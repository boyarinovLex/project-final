package com.javarush.jira;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
@ActiveProfiles("test")
abstract class BaseTests {

    private static final JdbcDatabaseContainer<?> POSTGRES_CONTAINER;
    private static final String DOCKER_IMAGE_NAME = "postgres:16.4";

    static {
        POSTGRES_CONTAINER = new PostgreSQLContainer<>(DOCKER_IMAGE_NAME);
        POSTGRES_CONTAINER.start();
    }

    //    https://stackoverflow.com/questions/55975798/how-to-overwrite-ports-defined-in-application-properties-in-integration-tests-af
    @DynamicPropertySource
    static void dataSourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRES_CONTAINER::getPassword);
    }
}
