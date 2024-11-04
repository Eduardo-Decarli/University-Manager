package com.compass.Desafio_02;

import com.compass.Desafio_02.entities.enumeration.Role;
import com.compass.Desafio_02.web.dto.CoordinatorCreateDto;
import com.compass.Desafio_02.web.dto.CoordinatorResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Sql(scripts = "/sql/InsertDataInSQL.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//@Sql(scripts = "/sql/DeleteDataInSQL.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CoordinatorIT {

    @Autowired
    WebTestClient testClient;


    @Test
    public void createCoordinator_WithValidArgs_ReturnStatus201() {
        CoordinatorResponseDto responseBody = testClient
                .post()
                .uri("/api/v1/coordinator").
                contentType(MediaType.APPLICATION_JSON).
                bodyValue(new CoordinatorCreateDto(
                        "Joe",
                        "Doe",
                        "coordinator@email.com",
                        LocalDate.of(2000,
                                5,
                                15),
                        "Coo4567@",
                        Role.ROLE_COORDINATOR,
                        null)).
                exchange().
                expectStatus().isCreated().
                expectBody(CoordinatorResponseDto.class).
                returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getEmail()).isEqualTo("coordinator@email.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo(Role.ROLE_COORDINATOR);
    }
}
