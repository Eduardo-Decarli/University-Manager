package com.compass.Desafio_02.web.controller;

import com.compass.Desafio_02.jwt.JwtUserDetails;
import com.compass.Desafio_02.services.RegistrationServices;
import com.compass.Desafio_02.services.TeacherService;
import com.compass.Desafio_02.web.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Teaches", description = "Contains all operations related to the teacher resource")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/teacher")
@Validated
public class TeacherController {

    private final TeacherService teacherService;
    private final RegistrationServices registrationServices;

    @Operation(summary = "Create a new teacher", description = "Resource to create a new teacher. Request requires use of a bearer token. " +
            "Access restricted to Role='COORDINATOR'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Teacher created successfully",
                            headers = @Header(name = HttpHeaders.LOCATION, description = "URL of the created resource"),
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = TeacherResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Teacher already registered",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Request not processed due to missing or invalid data",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Feature not allowed for ROLE_STUDENT or ROLE_TEACHER",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @PostMapping
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<TeacherResponseDto> create(@Valid @RequestBody TeacherCreateDto teacherDto) {
        TeacherResponseDto response = teacherService.create(teacherDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get teacher by ID", description = "Resource to retrieve a teacher's details by their ID. "
            + "Request requires use of a bearer token. Access restricted to Role='COORDINATOR'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Teacher found and returned successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = TeacherResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Teacher not found",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Feature not allowed for ROLE_STUDENT or ROLE_TEACHER",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<TeacherResponseDto> getById(@PathVariable Long id) {
        TeacherResponseDto response = teacherService.getById(id);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Get list of all teachers", description = "Resource to retrieve a list of all registered teachers. "
            + "Request requires use of a bearer token. Access restricted to Role='COORDINATOR'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of teachers retrieved successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    array = @ArraySchema(schema = @Schema(implementation = TeacherResponseDto.class)))),
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
    public ResponseEntity<List<TeacherResponseDto>> list() {
        List<TeacherResponseDto> responses = teacherService.list();
        return ResponseEntity.ok().body(responses);
    }

    @Operation(summary = "Update teacher by ID", description = "Resource to update an existing teacher's details. "
            + "Request requires use of a bearer token. Access restricted to Role='COORDINATOR'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Teacher updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Teacher not found",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Feature not allowed for ROLE_STUDENT or ROLE_TEACHER",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Request not processed due to missing or invalid data",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PutMapping("/modification/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<Void> update(@Valid @PathVariable Long id, @RequestBody TeacherCreateDto update) {
        teacherService.update(id, update);
        return ResponseEntity.status(204).build();
    }

    @Operation(summary = "Delete teacher by ID", description = "Resource to delete a teacher by their ID. "
            + "Request requires use of a bearer token. Access restricted to Role='COORDINATOR'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Teacher deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Teacher not found",
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
        teacherService.remove(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Get teacher details by token", description = "Resource to retrieve the details of the authenticated teacher based on their token. "
            + "Request requires use of a bearer token. Access restricted to Role='TEACHER'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Teacher details retrieved successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = TeacherResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Access denied. Only TEACHER role can access this resource",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized. Invalid or missing token",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/me")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<TeacherResponseDto> myData(@AuthenticationPrincipal JwtUserDetails userDetails) {
        TeacherResponseDto response = teacherService.getById(userDetails.getId());
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Update teacher details by token", description = "Resource to update the details of the authenticated teacher based on their token. "
            + "Request requires use of a bearer token. Access restricted to Role='TEACHER'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Teacher details updated successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = TeacherResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Access denied. Only TEACHER role can access this resource",
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
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<TeacherResponseDto> myUpdate(@AuthenticationPrincipal JwtUserDetails userDetails, @RequestBody TeacherCreateDto update) {
        TeacherResponseDto response = teacherService.update(userDetails.getId(), update);
        return ResponseEntity.status(204).body(response);
    }

    @Operation(summary = "Get teacher's discipline by token", description = "Resource to retrieve a list of discipline assigned to the authenticated teacher based on their token. "
            + "Request requires use of a bearer token. Access restricted to Role='TEACHER'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of disciplines retrieved successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    array = @ArraySchema(schema = @Schema(implementation = DisciplineResponseDto.class)))),
                    @ApiResponse(responseCode = "403", description = "Access denied. Only TEACHER role can access this resource",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized. Invalid or missing token",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/me/disciplines")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<List<DisciplineResponseDto>> myDisciplines(@AuthenticationPrincipal JwtUserDetails userDetails) {
        List<DisciplineResponseDto> response = teacherService.getDisciplines(userDetails.getId());
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Get students of a specific discipline by teacher token",
            description = "Resource to retrieve a list of students enrolled in a specific discipline taught by the authenticated teacher. "
                    + "Request requires use of a bearer token. Access restricted to Role='TEACHER'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of students retrieved successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    array = @ArraySchema(schema = @Schema(implementation = StudentResponseDto.class)))),
                    @ApiResponse(responseCode = "403", description = "Access denied. Only TEACHER role can access this resource",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized. Invalid or missing token",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Disciplines not found for the authenticated teacher",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/me/{nameDiscipline}/students")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<List<StudentResponseDto>> getStudentsByDiscipline(@AuthenticationPrincipal JwtUserDetails userDetails, @PathVariable String nameDiscipline) {
        List<StudentResponseDto> responses = teacherService.getStudentsByDiscipline(userDetails.getId(), nameDiscipline);
        return ResponseEntity.ok().body(responses);
    }

    @Operation(summary = "Get teacher's course by token",
            description = "Resource to retrieve the course assigned to the authenticated teacher based on their token. "
                    + "Request requires use of a bearer token. Access restricted to Role='TEACHER'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Teacher's course retrieved successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = CourseResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Access denied. Only TEACHER role can access this resource",
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
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<CourseResponseDto> myCourse(@AuthenticationPrincipal JwtUserDetails userDetails) {
        CourseResponseDto response = teacherService.getMyCourse(userDetails.getId());
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Add a course to a teacher",
            description = "Resource to add a course to the authenticated teacher based on their token. "
                    + "Request requires use of a bearer token. Access restricted to Role='TEACHER'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Course added to teacher successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = CourseResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Access denied. Only TEACHER role can access this resource",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized. Invalid or missing token",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/me/add/{id}/course/{courseName}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<TeacherResponseDto> addCourse(@PathVariable Long id, @PathVariable String courseName) {
        TeacherResponseDto response = teacherService.addCourse(id, courseName);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "List student registrations", description = "Resource to list all student registrations in the course taught by the teacher associated with the provided bearer token. " +
            "Request requires use of a bearer token. Access restricted to Role='TEACHER'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Registrations retrieved successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    array = @ArraySchema(schema = @Schema(implementation = RegistrationResponseDto.class)))),
                    @ApiResponse(responseCode = "403", description = "Access denied. Only TEACHER role can access this resource",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "No registrations found",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Request not processed due to missing or invalid data",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/me/course/registrations")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<List<RegistrationResponseDto>> getAllRegistrationsByCourse(@AuthenticationPrincipal JwtUserDetails userDetails) {
        TeacherResponseDto teacherResponseDto = teacherService.getById(userDetails.getId());
        List<RegistrationResponseDto> responses = registrationServices.getRegistrationsByCourse(teacherResponseDto.getCourse());
        return ResponseEntity.ok().body(responses);
    }

    @Operation(summary = "List student registrations by the teacher's disciplines", description = "Resource to list all student registrations in the disciplines taught by the teacher associated with the provided bearer token. " +
            "Request requires use of a bearer token. Access restricted to Role='TEACHER'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Registrations retrieved successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    array = @ArraySchema(schema = @Schema(implementation = RegistrationResponseDto.class)))),
                    @ApiResponse(responseCode = "403", description = "Access denied. Only TEACHER role can access this resource",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "No registrations found",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Request not processed due to missing or invalid data",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/me/{nameDiscipline}/registration")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<List<RegistrationResponseDto>> getAllRegistrationsByDiscipline(@AuthenticationPrincipal JwtUserDetails userDetails, @PathVariable String nameDiscipline) {
        List<RegistrationResponseDto> responses = teacherService.getAllRegistrationsByDiscipline(userDetails.getId(), nameDiscipline);
        return ResponseEntity.ok().body(responses);
    }
}
