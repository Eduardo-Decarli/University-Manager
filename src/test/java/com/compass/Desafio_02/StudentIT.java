package com.compass.Desafio_02;

import com.compass.Desafio_02.web.dto.StudentCreateDto;
import com.compass.Desafio_02.web.dto.StudentResponseDto;
import com.compass.Desafio_02.entities.enumeration.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/InsertDataInSQL.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/DeleteDataInSQL.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class StudentIT {

    @Autowired
    WebTestClient testClient;

//    @Test
//    public void testCreateStudent() {
//        StudentResponseDto responseBody = testClient
//                .post()
//                .uri("/api/v1/students").
//                contentType(MediaType.APPLICATION_JSON).
//                bodyValue().exchange().
//                expectStatus().isCreated().
//                expectBody(StudentResponseDto.class).
//                returnResult().getResponseBody();
//
//        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
//        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
//        org.assertj.core.api.Assertions.assertThat(responseBody.getEmail()).isEqualTo("student1@school.edu");
//        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("ROLE_STUDENT");
//    }

}
