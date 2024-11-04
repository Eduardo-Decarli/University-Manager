package com.compass.Desafio_02;

import com.compass.Desafio_02.web.dto.CourseCreateDto;
import com.compass.Desafio_02.web.dto.CourseResponseDto;
import com.compass.Desafio_02.web.dto.RegistrationCreateDto;
import com.compass.Desafio_02.web.dto.RegistrationResponseDto;
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
@Sql(scripts = "/sql/InsertDataInSQL.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/DeleteDataInSQL.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class RegistrationIT {

    @Autowired
    WebTestClient testRegistration;

    @Test
    public void createRegistration_withValidData_returnStatus201() {
        RegistrationResponseDto responseBody = testRegistration
                .post()
                .uri("/api/v1/registration")
                .headers(JwtAuthentication.getHeaderAuthorization(testRegistration, "joe@example.com", "12345678Lucas@"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(new RegistrationCreateDto(40L, 1L))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(RegistrationResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
    }


    @Test
    public void getAllRegistrations_returnStatus200AndListOfRegistrations() {
        List<RegistrationResponseDto> responseBody = testRegistration
                .get()
                .uri("/api/v1/registration")
                .headers(JwtAuthentication.getHeaderAuthorization(testRegistration, "joe@example.com", "12345678Lucas@"))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(RegistrationResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotEmpty();
    }

    @Test
    public void getRegistrationById_withValidId_returnStatus200AndRegistration() {
        RegistrationResponseDto responseBody = testRegistration
                .get()
                .uri("/api/v1/registration/{id}", 1)
                .headers(JwtAuthentication.getHeaderAuthorization(testRegistration, "joe@example.com", "12345678Lucas@"))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(RegistrationResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(1);
    }

    @Test
    public void updateRegistration_withValidData_returnStatus200AndUpdatedRegistration() {
        RegistrationCreateDto updateData = new RegistrationCreateDto(2L, 40L);

        RegistrationResponseDto responseBody = testRegistration
                .put()
                .uri("/api/v1/registration/1")
                .headers(JwtAuthentication.getHeaderAuthorization(testRegistration, "joe@example.com", "12345678Lucas@"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(new RegistrationCreateDto(40L, 2L))
                .exchange()
                .expectStatus().isOk()
                .expectBody(RegistrationResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getCourse().getId()).isEqualTo(2L);
    }
}
