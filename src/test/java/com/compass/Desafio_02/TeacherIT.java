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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql-teacher/InsertDataInSQL.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql-teacher/DeleteDataInSQL.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class TeacherIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void createStudent_WithValidArgs_ReturnStatus201() {
        TeacherResponseDto responseBody = testClient
                .post()
                .uri("/api/v1/teacher")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joe@example.com", "12345678Lucas@"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new TeacherCreateDto(
                        "Joe",
                        "Doe",
                        "student@email.com",
                        LocalDate.of(2000, 5, 15), "Coo4567@")).
                exchange().
                expectStatus().isCreated().
                expectBody(TeacherResponseDto.class).
                returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getEmail()).isEqualTo("student@email.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo(Role.ROLE_TEACHER);
    }

    @Test
    public void getById_WithValidArgs_ReturnStatus200() {
        TeacherResponseDto responseBody = testClient
                .get()
                .uri("/api/v1/teacher/1")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joe@example.com", "12345678Lucas@"))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TeacherResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(1);
    }

    @Test
    public void teacherList_WithValidStudentId_ReturnStatus200() {
        List<TeacherResponseDto> responseBody = testClient
                .get()
                .uri("/api/v1/teacher")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joe@example.com", "12345678Lucas@"))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TeacherResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.size()).isGreaterThan(0);
    }

    @Test
    public void updateStudent_WithValidArgs_ReturnStatus201() {
        testClient
                .put()
                .uri("/api/v1/teacher/modification/1")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joe@example.com", "12345678Lucas@"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new TeacherCreateDto(
                        "Jo2",
                        "Doe",
                        "student@email.com",
                        LocalDate.of(2000, 5, 15), "Coo4567@")).
                exchange().
                expectStatus().isNoContent();
    }

    @Test
    public void removeById_WithValidArgs_ReturnStatus200() {
        testClient
                .delete()
                .uri("/api/v1/teacher/1")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joe@example.com", "12345678Lucas@"))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void myData_WithValidArgs_ReturnStatus200() {
        TeacherResponseDto responseBody = testClient
                .get()
                .uri("/api/v1/teacher/me")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "doe@example.com", "12345678Lucas@"))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TeacherResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(1);
    }

    @Test
    public void myUpdate_WithValidArgs_ReturnStatus201() {
        testClient
                .put()
                .uri("/api/v1/teacher/me")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "doe@example.com", "12345678Lucas@"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new TeacherCreateDto(
                        "Jo2",
                        "Doe",
                        "student@email.com",
                        LocalDate.of(2000, 5, 15), "Coo4567@")).
                exchange().
                expectStatus().isNoContent();
    }

    @Test
    public void myDisciplineList_WithValidStudentId_ReturnStatus200() {
        List<DisciplineResponseDto> responseBody = testClient
                .get()
                .uri("/api/v1/teacher/me/disciplines")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "doe@example.com", "12345678Lucas@"))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(DisciplineResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.size()).isGreaterThan(0);
    }

    @Test
    public void disciplineStudentsList_WithValidStudentId_ReturnStatus200() {
        List<StudentResponseDto> responseBody = testClient
                .get()
                .uri("/api/v1/teacher/me/CS101/students")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "doe@example.com", "12345678Lucas@"))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(StudentResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.size()).isGreaterThan(0);
    }

    @Test
    public void myCourse_WithValidArgs_ReturnStatus200() {
        CourseResponseDto responseBody = testClient
                .get()
                .uri("/api/v1/teacher/me/course")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "doe@example.com", "12345678Lucas@"))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CourseResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("Computer_Science");
    }

    @Test
    public void addCourse_WithValidArgs_ReturnStatus200() {
        TeacherResponseDto responseBody = testClient
                .get()
                .uri("/api/v1/teacher/me/add/2/course/Computer")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joe@example.com", "12345678Lucas@"))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TeacherResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getCourse().getName()).isEqualTo("Computer");
    }

    @Test
    public void getAllRegistrationsByCourse_WithValidArgs_ReturnStatus200() {
        List<RegistrationResponseDto> responseBody = testClient
                .get()
                .uri("/api/v1/teacher/me/course/registrations")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "doe@example.com", "12345678Lucas@"))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(RegistrationResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.size()).isGreaterThan(0);
    }

    @Test
    public void getAllRegistrationsByDiscipline_WithValidArgs_ReturnStatus200() {
        List<RegistrationResponseDto> responseBody = testClient
                .get()
                .uri("/api/v1/teacher/me/CS101/registration")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "doe@example.com", "12345678Lucas@"))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(RegistrationResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.size()).isGreaterThan(0);
    }

}
