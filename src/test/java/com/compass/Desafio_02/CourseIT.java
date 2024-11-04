package com.compass.Desafio_02;

import com.compass.Desafio_02.web.dto.CourseCreateDto;
import com.compass.Desafio_02.web.dto.CourseResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

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
}
