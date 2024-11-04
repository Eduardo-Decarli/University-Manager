package com.compass.Desafio_02;

import com.compass.Desafio_02.entities.enumeration.Role;
import com.compass.Desafio_02.web.dto.*;
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
    public void deleteStudentById_WithValidId_ReturnsStatus204() {
        testStudent
                .delete()
                .uri("/api/v1/students/1")
                .headers(JwtAuthentication.getHeaderAuthorization(testStudent, "joe@example.com", "12345678Lucas@"))
                .exchange()
                .expectStatus().isNoContent();
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
                .expectStatus().isNoContent();
    }

    @Test
    public void getStudentById_WithValidId_ReturnStatus200() {
        StudentResponseDto responseBody = testStudent
                .get()
                .uri("/api/v1/students/1")
                .headers(JwtAuthentication.getHeaderAuthorization(testStudent, "joe@example.com", "12345678Lucas@"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(StudentResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getFirstName()).isEqualTo("John");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo(Role.ROLE_STUDENT);
    }

    @Test
    public void getCourseByCourse_withValidData_returnStatus200() {
        CourseResponseDto responseBody = testStudent
                .get()
                .uri("/api/v1/students/me/course")
                .headers(JwtAuthentication.getHeaderAuthorization(testStudent, "john@email.com", "12345678Lucas@"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(CourseResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("Computer_Science");
    }

    @Test
    public void getRegistration_withValidData_returnStatus200() {
        RegistrationResponseDto responseBody = testStudent
                .get()
                .uri("/api/v1/students/me/registration")
                .headers(JwtAuthentication.getHeaderAuthorization(testStudent, "john@email.com", "12345678Lucas@"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(RegistrationResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getStudent().getFirstName()).isEqualTo("John");
    }

    @Test
    public void myDisciplines_WithValidStudentId_ReturnStatus200() {
        List<DisciplineResponseDto> responseBody = testStudent
                .get()
                .uri("/api/v1/students/me/course/disciplines")
                .headers(JwtAuthentication.getHeaderAuthorization(testStudent, "john@example.com", "12345678Lucas@"))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(DisciplineResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.size()).isGreaterThan(0); // Verifica se há disciplinas associadas
    }
}

