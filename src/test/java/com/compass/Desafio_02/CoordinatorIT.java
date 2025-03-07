package com.compass.Desafio_02;

import com.compass.Desafio_02.entities.enumeration.Role;
import com.compass.Desafio_02.web.dto.*;
import com.compass.Desafio_02.web.exception.handler.ErrorMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.List;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql-coordinator/InsertDataInSQL.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql-coordinator/DeleteDataInSQL.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
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
                        "Coo4567@")).
                exchange().
                expectStatus().isCreated().
                expectBody(CoordinatorResponseDto.class).
                returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getEmail()).isEqualTo("coordinator@email.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo(Role.ROLE_COORDINATOR);
    }

    @Test
    public void createCoordinator_WithInvalidEmail_ReturnStatus400() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/coordinator").
                contentType(MediaType.APPLICATION_JSON).
                bodyValue(new CoordinatorCreateDto(
                        "Joe",
                        "Doe",
                        "",
                        LocalDate.of(2000,
                                5,
                                15),
                        "Coo4567@")).
                exchange().
                expectStatus().isEqualTo(400).
                expectBody(ErrorMessage.class).
                returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);


        responseBody = testClient
                .post()
                .uri("/api/v1/coordinator").
                contentType(MediaType.APPLICATION_JSON).
                bodyValue(new CoordinatorCreateDto(
                        "Joe",
                        "Doe",
                        "asdasdasd",
                        LocalDate.of(2000,
                                5,
                                15),
                        "Coo4567@")).
                exchange().
                expectStatus().isEqualTo(400).
                expectBody(ErrorMessage.class).
                returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);

        responseBody = testClient
                .post()
                .uri("/api/v1/coordinator").
                contentType(MediaType.APPLICATION_JSON).
                bodyValue(new CoordinatorCreateDto(
                        "Joe",
                        "Doe",
                        "@email.com",
                        LocalDate.of(2000,
                                5,
                                15),
                        "Coo4567@")).
                exchange().
                expectStatus().isEqualTo(400).
                expectBody(ErrorMessage.class).
                returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);
    }

    @Test
    public void createCoordinator_WithInvalidPassword_ReturnStatus400() {
        ErrorMessage responseBody = testClient
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
                        "123")).
                exchange().
                expectStatus().isEqualTo(400).
                expectBody(ErrorMessage.class).
                returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);

        responseBody = testClient
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
                        "")).
                exchange().
                expectStatus().isEqualTo(400).
                expectBody(ErrorMessage.class).
                returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);
    }


    @Test
    public void getCoordinator_WithValidId_ReturnStatus200() {
        CoordinatorResponseDto responseBody = testClient
                .get()
                .uri("/api/v1/coordinator/me")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joe@example.com", "12345678Lucas@"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(CoordinatorResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getEmail()).isEqualTo("joe@example.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getFirstName()).isEqualTo("Joe");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo(Role.ROLE_COORDINATOR);
    }

    @Test
    public void getCoordinatorById_WithValidId_ReturnStatus200() {
        CoordinatorResponseDto responseBody = testClient
                .get()
                .uri("/api/v1/coordinator/1")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joe@example.com", "12345678Lucas@"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(CoordinatorResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getFirstName()).isEqualTo("Joe");
        org.assertj.core.api.Assertions.assertThat(responseBody.getEmail()).isEqualTo("joe@example.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo(Role.ROLE_COORDINATOR);
    }

    @Test
    public void getCoordinator_WithInvalidId_ReturnStatus404() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/coordinator/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joe@example.com", "12345678Lucas@"))
                .exchange()
                .expectStatus().isEqualTo(404)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void getCoordinatorCourse_WithValidId_ReturnStatus200() {
        CourseResponseDto responseBody = testClient
                .get()
                .uri("/api/v1/coordinator/me/course")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joe@example.com", "12345678Lucas@"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(CourseResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getCoordinator().getFirstName()).isEqualTo("Joe");
        org.assertj.core.api.Assertions.assertThat(responseBody.getCoordinator().getEmail()).isEqualTo("joe@example.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getCoordinator().getRole()).isEqualTo(Role.ROLE_COORDINATOR);
    }

    @Test
    public void getCoordinatorCourseDiscplines_WithValidId_ReturnStatus200() {
        List<DisciplineResponseDto> responseBody = testClient
                .get()
                .uri("/api/v1/coordinator/me/course/disciplines")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joe@example.com", "12345678Lucas@"))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(DisciplineResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.size()).isEqualTo(2);
    }

    @Test
    public void getCoordinatorDiscipline_WithValidId_ReturnStatus200() {
        List<DisciplineResponseDto> responseBody = testClient
                .get()
                .uri("/api/v1/coordinator/me/disciplines")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joe@example.com", "12345678Lucas@"))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(DisciplineResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody).size().isEqualTo(2);
    }


    @Test
    public void getCoordinatorList_WithoutParameters_ReturnStatus200() {
        List<CoordinatorResponseDto> responseBody = testClient
                .get()
                .uri("/api/v1/coordinator")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joe@example.com", "12345678Lucas@"))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CoordinatorResponseDto.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.size()).isEqualTo(2);
    }

    @Test
    public void updateCoordinator_WithValidParameters_ReturnStatus204() {
        testClient
                .put()
                .uri("/api/v1/coordinator/me")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joe@example.com", "12345678Lucas@"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CoordinatorCreateDto(
                "Doe",
                "Joe",
                "coordinator@email.com",
                LocalDate.of(2001,
                        5,
                        15),
                "Coo4567@$"))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void updateCoordinatorById_WithValidParameters_ReturnStatus204() {
        testClient
                .put()
                .uri("/api/v1/coordinator/1")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joe@example.com", "12345678Lucas@"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CoordinatorCreateDto(
                        "Doe",
                        "Joe",
                        "coordinator@email.com",
                        LocalDate.of(2001,
                                5,
                                15),
                        "Coo4567@$"))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void updateCoordinatorById_WithinValidParameters_ReturnStatus400() {
        testClient
                .put()
                .uri("/api/v1/coordinator/1")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joe@example.com", "12345678Lucas@"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CoordinatorCreateDto(
                        "Doe",
                        "Joe",
                        "coordinator",
                        LocalDate.of(2001,
                                5,
                                15),
                        "1251234"))
                .exchange()
                .expectStatus().isEqualTo(400);
    }

    @Test
    public void removeCoordinatorById_WithValidParameters_ReturnStatus204() {
        testClient
                .delete()
                .uri("/api/v1/coordinator/2")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joe@example.com", "12345678Lucas@"))
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void removeCoordinatorById_WithinValidParameters_ReturnStatus404() {
        testClient
                .delete()
                .uri("/api/v1/coordinator/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joe@example.com", "12345678Lucas@"))
                .exchange()
                .expectStatus().isEqualTo(404);
    }

}
