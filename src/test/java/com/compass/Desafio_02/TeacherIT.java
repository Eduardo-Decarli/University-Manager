package com.compass.Desafio_02;

import com.compass.Desafio_02.entities.enumeration.Role;
import com.compass.Desafio_02.web.dto.StudentCreateDto;
import com.compass.Desafio_02.web.dto.StudentResponseDto;
import com.compass.Desafio_02.web.dto.TeacherCreateDto;
import com.compass.Desafio_02.web.dto.TeacherResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

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
}
