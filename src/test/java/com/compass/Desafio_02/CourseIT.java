package com.compass.Desafio_02;

import com.compass.Desafio_02.web.dto.CoordinatorCreateDto;
import com.compass.Desafio_02.web.dto.CourseCreateDto;
import com.compass.Desafio_02.web.dto.CourseResponseDto;
import com.compass.Desafio_02.web.exception.handler.ErrorMessage;
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
@Sql(scripts = "/sql-course/DeleteDataInSQL.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = "/sql-course/InsertDataInSQL.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CourseIT {

    @Autowired
    WebTestClient testCourse;

    @Test
    public void createCourse_withValidData_returnStatus201() {
        CourseResponseDto responseBody = testCourse
                .post()
                .uri("/api/v1/course")
                .headers(JwtAuthentication.getHeaderAuthorization(testCourse, "joe@example.com", "12345678Lucas@"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(new CourseCreateDto("Matematica", "muita matemática", 1L))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CourseResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("Matematica");
    }

    @Test
    public void createCourse_withInvalidData_returnStatus400() {
        ErrorMessage responseBody = testCourse
                .post()
                .uri("/api/v1/course")
                .headers(JwtAuthentication.getHeaderAuthorization(testCourse, "joe@example.com", "12345678Lucas@"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(new CourseCreateDto("", "muita matemática", 1L))
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);

        responseBody = testCourse
                .post()
                .uri("/api/v1/course")
                .headers(JwtAuthentication.getHeaderAuthorization(testCourse, "joe@example.com", "12345678Lucas@"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(new CourseCreateDto("Matematica", "", 1L))
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);

        responseBody = testCourse
                .post()
                .uri("/api/v1/course")
                .headers(JwtAuthentication.getHeaderAuthorization(testCourse, "joe@example.com", "12345678Lucas@"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(new CourseCreateDto("Matematica", "muita matemática", null))
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);
    }

    @Test
    public void updateCourse_withValidData_returnStatus200() {
        CourseResponseDto responseBody = testCourse
                .put()
                .uri("/api/v1/course/1")
                .headers(JwtAuthentication.getHeaderAuthorization(testCourse, "joe@example.com", "12345678Lucas@"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(new CourseCreateDto("Quimica", "muita quimica", 1L))
                .exchange()
                .expectStatus().isOk()
                .expectBody(CourseResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("Quimica");
    }

    @Test
    public void updateCourse_withInvalidData_returnStatus400() {
        testCourse
                .put()
                .uri("/api/v1/course/1")
                .headers(JwtAuthentication.getHeaderAuthorization(testCourse, "joe@example.com", "12345678Lucas@"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(new CourseCreateDto("", "", 1L))
                .exchange()
                .expectStatus().isEqualTo(400);
    }

    @Test
    public void getCourseById_withValidData_returnStatus200() {
        CourseResponseDto responseBody = testCourse
                .get()
                .uri("/api/v1/course/1")
                .headers(JwtAuthentication.getHeaderAuthorization(testCourse, "joe@example.com", "12345678Lucas@"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(CourseResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("Computer_Science");
        org.assertj.core.api.Assertions.assertThat(responseBody.getDescription()).isEqualTo("Course_description_1");
        org.assertj.core.api.Assertions.assertThat(responseBody.getCoordinator().getId()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getDisciplines().size()).isEqualTo(2);
    }

    @Test
    public void getCourseById_withInvalidId_returnStatus404() {
        ErrorMessage responseBody = testCourse
                .get()
                .uri("/api/v1/course/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testCourse, "joe@example.com", "12345678Lucas@"))
                .exchange()
                .expectStatus().isEqualTo(404)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void listCourses_withValidData_returnStatus200() {
        List<CourseResponseDto> responseBody = testCourse
                .get()
                .uri("/api/v1/course")
                .headers(JwtAuthentication.getHeaderAuthorization(testCourse, "joe@example.com", "12345678Lucas@"))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CourseResponseDto.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody.size()).isEqualTo(2);
    }

    @Test
    public void addDiscipline_withValidData_returnStatus200() {
        CourseResponseDto responseBody = testCourse
                .patch()
                .uri("/api/v1/course/Biology/add/disciplines/CS104")
                .headers(JwtAuthentication.getHeaderAuthorization(testCourse, "joe@example.com", "12345678Lucas@"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(CourseResponseDto.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody.getDisciplines().size()).isEqualTo(2);
    }

    @Test
    public void addDiscipline_withInvalidCourse_returnStatus404() {

        ErrorMessage responseBody = testCourse
                .patch()
                .uri("/api/v1/course/Biology/add/disciplines/CS404")
                .headers(JwtAuthentication.getHeaderAuthorization(testCourse, "joe@example.com", "12345678Lucas@"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void changeCourseCoordinatorById_WithInvalidParameters_ReturnStatus400() {
        CourseResponseDto responseBody = testCourse
                .patch()
                .uri("/api/v1/course/Biology/change/coordinators/3")
                .headers(JwtAuthentication.getHeaderAuthorization(testCourse, "joe@example.com", "12345678Lucas@"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(CourseResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getCoordinator().getId()).isEqualTo(3);
    }

    @Test
    public void removeDiscipline_withValidData_returnStatus204() {
        CourseResponseDto responseBody = testCourse
                .patch()
                .uri("/api/v1/course/Computer_Science/remove/disciplines/CS102")
                .headers(JwtAuthentication.getHeaderAuthorization(testCourse, "joe@example.com", "12345678Lucas@"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(CourseResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody.getDisciplines().size()).isEqualTo(1);
    }

    @Test
    public void removeDiscipline_withInvalidCourse_returnStatus404() {
        ErrorMessage responseBody = testCourse
                .patch()
                .uri("/api/v1/course/Computer_Science/remove/disciplines/CS10")
                .headers(JwtAuthentication.getHeaderAuthorization(testCourse, "joe@example.com", "12345678Lucas@"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void removeCourseById_WithValidParameters_ReturnStatus204() {
        testCourse
                .delete()
                .uri("/api/v1/course/2")
                .headers(JwtAuthentication.getHeaderAuthorization(testCourse, "joe@example.com", "12345678Lucas@"))
                .exchange()
                .expectStatus().isNoContent();
    }

}
