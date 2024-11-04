package com.compass.Desafio_02;

import com.compass.Desafio_02.web.dto.CourseCreateDto;
import com.compass.Desafio_02.web.dto.CourseResponseDto;
import com.compass.Desafio_02.web.dto.DisciplineCreateDto;
import com.compass.Desafio_02.web.dto.DisciplineResponseDto;
import com.compass.Desafio_02.web.exception.handler.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql-disciplines/DeleteDataInSQL.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = "/sql-disciplines/InsertDataInSQL.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class DisciplinesIT {

    @Autowired
    WebTestClient testDisciplines;

    @Test
    public void createDiscipline_withValidData_returnStatus201() {
        DisciplineResponseDto responseBody = testDisciplines
                .post()
                .uri("/api/v1/discipline")
                .headers(JwtAuthentication.getHeaderAuthorization(testDisciplines, "joe@example.com", "12345678Lucas@"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(new DisciplineCreateDto("calculo1", "um pouco de matematica", null, null))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(DisciplineResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("calculo1");
    }


    @Test
    public void getAllDisciplines_returnStatus200() {
        DisciplineResponseDto[] responseBody = testDisciplines
                .get()
                .uri("/api/v1/discipline")
                .headers(JwtAuthentication.getHeaderAuthorization(testDisciplines, "joe@example.com", "12345678Lucas@"))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(DisciplineResponseDto[].class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
    }

    @Test
    public void getDisciplineById_existingId_returnStatus200() {
        DisciplineResponseDto responseBody = testDisciplines
                .get()
                .uri("/api/v1/discipline/1")
                .headers(JwtAuthentication.getHeaderAuthorization(testDisciplines, "joe@example.com", "12345678Lucas@"))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(DisciplineResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(1);
    }

    @Test
    public void updateDiscipline_withValidData_returnStatus200() {
        DisciplineResponseDto responseBody = testDisciplines
                .put()
                .uri("/api/v1/discipline/1")
                .headers(JwtAuthentication.getHeaderAuthorization(testDisciplines, "joe@example.com", "12345678Lucas@"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(new DisciplineCreateDto("Matematica Avançada", "Matematica para estudantes avançados", null, null))
                .exchange()
                .expectStatus().isOk()
                .expectBody(DisciplineResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("Matematica Avançada");
    }

    @Test
    public void deleteDiscipline_existingId_returnStatus204() {
        testDisciplines
                .delete()
                .uri("/api/v1/discipline/3")
                .headers(JwtAuthentication.getHeaderAuthorization(testDisciplines, "joe@example.com", "12345678Lucas@"))
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void addStudent_withValidData_returnStatus200() {
        DisciplineResponseDto responseBody = testDisciplines
                .patch()
                .uri("/api/v1/discipline/CS101/add/student/2")
                .headers(JwtAuthentication.getHeaderAuthorization(testDisciplines, "joe@example.com", "12345678Lucas@"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(DisciplineResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStudents().size()).isEqualTo(2);
    }

    @Test
    public void removeStudent_withValidData_returnStatus200() {
        testDisciplines
                .patch()
                .uri("/api/v1/discipline/CS101/remove/student/2")
                .headers(JwtAuthentication.getHeaderAuthorization(testDisciplines, "joe@example.com", "12345678Lucas@"))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void addSubsTeacher_withValidData_returnStatus200() {
        testDisciplines
                .patch()
                .uri("/api/v1/discipline/CS101/add/teacher/substitute/binto@example.com")
                .headers(JwtAuthentication.getHeaderAuthorization(testDisciplines, "joe@example.com", "12345678Lucas@"))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void addTitularTeacher_withValidData_returnStatus200() {
        testDisciplines
                .patch()
                .uri("/api/v1/discipline/CS103/add/teacher/titular/binto@example.com")
                .headers(JwtAuthentication.getHeaderAuthorization(testDisciplines, "joe@example.com", "12345678Lucas@"))
                .exchange()
                .expectStatus().isOk();
    }


}
