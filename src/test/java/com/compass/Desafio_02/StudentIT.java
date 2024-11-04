package com.compass.Desafio_02;

import com.compass.Desafio_02.entities.enumeration.Role;
import com.compass.Desafio_02.web.dto.CoordinatorCreateDto;
import com.compass.Desafio_02.web.dto.CoordinatorResponseDto;
import com.compass.Desafio_02.web.dto.StudentCreateDto;
import com.compass.Desafio_02.web.dto.StudentResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;


@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/InsertDataInSQL.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/DeleteDataInSQL.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class StudentIT {

    @Autowired
    WebTestClient testStudent;

    @Test
    public void createStudent_WithValidArgs_ReturnStatus201() {
        StudentResponseDto responseBody = testStudent
                .post()
                .uri("/api/v1/students")
                .headers(JwtAuthentication.getHeaderAuthorization(testStudent, "joe@example.com", "12345678Lucas@"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new StudentCreateDto("Joe", "Doe", "student@email.com", LocalDate.of(2000, 5, 15), "Coo4567@", "88047550")).
                exchange().
                expectStatus().isCreated().
                expectBody(StudentResponseDto.class).
                returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getEmail()).isEqualTo("student@email.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo(Role.ROLE_STUDENT);
    }

    @Test
    public void updateStudent_WithValidParameters_ReturnStatus204() {
        testStudent
                .put()
                .uri("/api/v1/students/me")
                .headers(JwtAuthentication.getHeaderAuthorization(testStudent, "john@email.com", "12345678Lucas@"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new StudentCreateDto("Joe","Doe","student@email.com",LocalDate.of(2000,5,15),"Coo4567@","88047550"))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void getStudentById_WithValidId_ReturnStatus200() {
        StudentResponseDto responseBody = testStudent
                .get()
                .uri("/api/v1/students/1")
                .headers(JwtAuthentication.getHeaderAuthorization(testStudent, "john@email.com", "12345678Lucas@"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(StudentResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getFirstName()).isEqualTo("john");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo(Role.ROLE_STUDENT);
    }
}
