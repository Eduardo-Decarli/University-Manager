package com.compass.Desafio_02.web.controller;

import com.compass.Desafio_02.jwt.JwtUserDetails;
import com.compass.Desafio_02.services.StudentService;
import com.compass.Desafio_02.web.dto.*;
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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Students", description = "Contains all operations related to the student resource")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/students")
@Validated
public class StudentController {

    private final StudentService studentService;

    @Operation(summary = "Create a new student", description = "Resource to create a new student. " +
            " Request requires use of a bearer token. Access restricted to Role='COORDINATOR'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Student created successfully",
                            headers = @Header(name = HttpHeaders.LOCATION, description = "URL of the created resource"),
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = StudentResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Student already registered",
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
    @PostMapping
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<StudentResponseDto> create(@Valid @RequestBody StudentCreateDto studentDto) {
        StudentResponseDto response = studentService.create(studentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get student by ID", description = "Retrieve student details by ID." +
            " Request requires use of a bearer token. Access restricted to Role='COORDINATOR'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Student found and returned successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = StudentResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Student not found",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Feature not allowed for ROLE_STUDENT or ROLE_TEACHER",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<StudentResponseDto> getById(@PathVariable Long id) {
        StudentResponseDto response = studentService.getById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get list of all students", description = "Resource to retrieve a list of all registered students. " +
            " Request requires use of a bearer token. Access restricted to Role='COORDINATOR'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of students retrieved successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    array = @ArraySchema(schema = @Schema(implementation = StudentResponseDto.class)))),
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
    public ResponseEntity<List<StudentResponseDto>> list() {
        List<StudentResponseDto> responses = studentService.list();
        return ResponseEntity.ok().body(responses);
    }

    @Operation(summary = "Update student by ID", description = "Resource to update an existing student's details. " +
            " Request requires use of a bearer token. Access restricted to Role='COORDINATOR'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Student updated successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8")),
                    @ApiResponse(responseCode = "404", description = "Student not found",
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
    @PutMapping("/modification/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<Void> update(@Valid @PathVariable Long id, @RequestBody StudentCreateDto studentDto) {
        studentService.update(id, studentDto);
        return ResponseEntity.status(204).build();
    }

    @Operation(summary = "Delete student by ID", description = "Resource to delete a student by their ID. " +
            " Request requires use of a bearer token. Access restricted to Role='COORDINATOR'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Student deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Student not found",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Feature not allowed for ROLE_STUDENT or ROLE_TEACHER",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<Void> removeById(@PathVariable Long id) {
        studentService.remove(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Get student details by token", description = "Resource to retrieve the details of the authenticated student based on their token. " +
            " Request requires use of a bearer token. Access restricted to Role='STUDENT'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Student details retrieved successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = StudentResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Access denied. Only STUDENT role can access this resource",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized. Invalid or missing token",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/me")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<StudentResponseDto> myData(@AuthenticationPrincipal JwtUserDetails userDetails) {
        StudentResponseDto response = studentService.getById(userDetails.getId());
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Update student details by token", description = "Resource to update the details of the authenticated student based on their token." +
            " Request requires use of a bearer token. Access restricted to Role='STUDENT'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Student details updated successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = StudentResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Access denied. Only STUDENT role can access this resource",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized. Invalid or missing token",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Request not processed due to missing or invalid data",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PutMapping("/me")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<StudentResponseDto> myUpdate(@AuthenticationPrincipal JwtUserDetails userDetails, @RequestBody StudentCreateDto studentDto) {
        StudentResponseDto response = studentService.update(userDetails.getId(), studentDto);
        return ResponseEntity.status(204).body(response);
    }

    @Operation(summary = "Get student's course by token", description = "Resource to retrieve the course assigned to the authenticated student based on their token. " +
            " Request requires use of a bearer token. Access restricted to Role='STUDENT'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Student's course retrieved successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = CourseResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Access denied. Only STUDENT role can access this resource",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized. Invalid or missing token",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "No course assigned to the authenticated teacher",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/me/course")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<CourseResponseDto> myCourse(@AuthenticationPrincipal JwtUserDetails userDetails) {
        CourseResponseDto courseDto = studentService.getMyCourse(userDetails.getId());
        return ResponseEntity.ok().body(courseDto);
    }

    @Operation(summary = "Get student's disciplines by token", description = "Resource to retrieve the disciplines assigned to the authenticated student based on their token. " +
            " Request requires use of a bearer token. Access restricted to Role='STUDENT'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of disciplines retrieved successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    array = @ArraySchema(schema = @Schema(implementation = DisciplineResponseDto.class)))),
                    @ApiResponse(responseCode = "403", description = "Access denied. Only STUDENT role can access this resource",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized. Invalid or missing token",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/me/course/disciplines")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<DisciplineResponseDto>> myDisciplines(@AuthenticationPrincipal JwtUserDetails userDetails) {
        List<DisciplineResponseDto> responses = studentService.getStudentDisciplines(userDetails.getId());
        return ResponseEntity.ok().body(responses);
    }

    @Operation(summary = "Get student's registration by token", description = "Resource to retrieve the registration assigned to the authenticated student based on their token. " +
            "Request requires use of a bearer token. Access restricted to Role='STUDENT'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Student's registration retrieved successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = RegistrationResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Access denied. Only STUDENT role can access this resource",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/me/registration")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<RegistrationResponseDto> myRegistration(@AuthenticationPrincipal JwtUserDetails userDetails) {
        RegistrationResponseDto responseDto = studentService.getRegistration(userDetails.getId());
        return ResponseEntity.ok().body(responseDto);
    }
}
