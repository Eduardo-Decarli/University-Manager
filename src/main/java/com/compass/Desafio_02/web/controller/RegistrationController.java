package com.compass.Desafio_02.web.controller;

import com.compass.Desafio_02.services.RegistrationServices;
import com.compass.Desafio_02.web.dto.RegistrationCreateDto;
import com.compass.Desafio_02.web.dto.RegistrationResponseDto;
import com.compass.Desafio_02.web.exception.handler.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Registrations", description = "Operations related to the registration resource")
@RestController
@RequestMapping("/api/v1/registration")
@Validated
public class RegistrationController {

    private final RegistrationServices services;

    public RegistrationController(RegistrationServices services){
        this.services = services;
    }

    @Operation(summary = "Create a new registration", description = "Resource to create a new registration. " +
            " Request requires use of a bearer token. Access restricted to Role='COORDINATOR'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Registration created successfully",
                            headers = @Header(name = HttpHeaders.LOCATION, description = "URL of the created resource"),
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = RegistrationResponseDto.class))),
                    @ApiResponse(responseCode = "422", description = "Request not processed due to missing or invalid data",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Feature not allowed for ROLE_STUDENT or ROLE_TEACHER",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @PostMapping
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<RegistrationResponseDto> create(@Valid @RequestBody RegistrationCreateDto registration) {
        RegistrationResponseDto response = services.createRegistration(registration);
        return ResponseEntity.status(201).body(response);
    }

    @Operation(summary = "Get list of all registrations", description = "Resource to retrieve a list of all registrations. " +
            "Request requires use of a bearer token. Access restricted to Role='COORDINATOR'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of registrations retrieved successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    array = @ArraySchema(schema = @Schema(implementation = RegistrationResponseDto.class)))),
                    @ApiResponse(responseCode = "403", description = "Feature not allowed for ROLE_STUDENT or ROLE_TEACHER",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "No data found",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<List<RegistrationResponseDto>> getAllRegistrations(){
        List<RegistrationResponseDto> registrations = services.getAllRegistrations();
        return ResponseEntity.ok().body(registrations);
    }

    @Operation(summary = "Get registration by ID", description = "Resource to retrieve a registration's details by their ID. " +
            "Request requires use of a bearer token. Access restricted to Role='COORDINATOR'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Registration found and returned successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = RegistrationResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Registration not found",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Feature not allowed for ROLE_STUDENT or ROLE_TEACHER",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<RegistrationResponseDto> getRegistrationById(@PathVariable long id){
        RegistrationResponseDto registration = services.getRegistrationById(id);
        return ResponseEntity.ok().body(registration);
    }

    @Operation(summary = "Update registration by ID", description = "Resource to update an existing registration's details. " +
            "Request requires use of a bearer token. Access restricted to Role='COORDINATOR'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Registration updated successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = RegistrationResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Registration not found",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Request not processed due to missing or invalid data",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Feature not allowed for ROLE_STUDENT or ROLE_TEACHER",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<RegistrationResponseDto> updateRegistration(@Valid @PathVariable Long id, @RequestBody RegistrationCreateDto registration) {
        RegistrationResponseDto update = services.updateRegistration(id, registration);
        return ResponseEntity.ok().body(update);
    }

    @Operation(summary = "Delete registration by ID", description = "Resource to delete a registration by their ID. " +
            "Request requires use of a bearer token. Access restricted to Role='COORDINATOR'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Registration deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Registration not found",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Feature not allowed for ROLE_STUDENT or ROLE_TEACHER",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<Void> deleteRegistrationById(@PathVariable long id) {
        services.deleteRegistration(id);
        return ResponseEntity.noContent().build();
    }
}
