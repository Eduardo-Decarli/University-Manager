package com.compass.Desafio_02.web.controller;

import com.compass.Desafio_02.entities.Course;
import com.compass.Desafio_02.entities.Discipline;
import com.compass.Desafio_02.services.CourseServices;
import com.compass.Desafio_02.web.dto.CourseCreateDto;
import com.compass.Desafio_02.web.dto.CourseResponseDto;
import com.compass.Desafio_02.web.dto.DisciplineResponseDto;
import com.compass.Desafio_02.web.exception.handler.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Courses", description = "Contains all operations related to the course resource")
@RestController
@RequestMapping("/api/v1/course")
@Validated
public class CourseController {

    private final CourseServices services;

    public CourseController(CourseServices services){
        this.services = services;
    }

    @Operation(summary = "Create a new course", description = "Resource to create a new course. " +
            "Request requires use of a bearer token. Access restricted to Role='COORDINATOR'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Course created successfully"),
                    @ApiResponse(responseCode = "409", description = "Course already registered",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
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
    public ResponseEntity<CourseResponseDto> create(@Valid @RequestBody CourseCreateDto course) {
        CourseResponseDto response = services.createCourse(course);
        return ResponseEntity.status(201).body(response);
    }

    @Operation(summary = "Get list of all courses", description = "RResource to retrieve a list of all registered courses." +
            " Request requires use of a bearer token. Access restricted to Role='COORDINATOR'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of courses retrieved successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    array = @ArraySchema(schema = @Schema(implementation = CourseResponseDto.class)))),
                    @ApiResponse(responseCode = "403", description = "Feature not allowed for ROLE_STUDENT or ROLE_TEACHER",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "No data found",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping
    @PreAuthorize("hasAnyRole('COORDINATOR', 'TEACHER', 'STUDENT')")
    public ResponseEntity<List<CourseResponseDto>> getAllCourses(){
        List<CourseResponseDto> courses = services.getAllCourses();
        return ResponseEntity.ok().body(courses);
    }

    @Operation(summary = "Get course by ID", description = "Resource to retrieve a teacher's details by their ID. " +
            "Request requires use of a bearer token. Access restricted to Role='COORDINATOR'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Course found and returned successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = CourseResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Course not found",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Feature not allowed for ROLE_STUDENT or ROLE_TEACHER",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<CourseResponseDto> getCourseById(@PathVariable long id) {
        CourseResponseDto course = services.getCourseById(id);
        return ResponseEntity.ok().body(course);
    }

    @Operation(summary = "Update course by ID", description = "Resource to update an existing course's details. " +
            "Request requires use of a bearer token. Access restricted to Role='COORDINATOR'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Course updated successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = CourseResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Course not found",
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
    public ResponseEntity<CourseResponseDto> updateCourse(@Valid @PathVariable Long id, @RequestBody CourseCreateDto course) {
        CourseResponseDto updatedCourse = services.updateCourse(id, course);
        return ResponseEntity.ok().body(updatedCourse);
    }

    @Operation(summary = "Delete course by ID", description = "Resource to delete a course by their ID. " +
            "Request requires use of a bearer token. Access restricted to Role='COORDINATOR'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Course deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Course not found",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Feature not allowed for ROLE_STUDENT or ROLE_TEACHER",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<Void> deleteCourseById(@PathVariable long id) throws Exception {
        services.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Add a discipline to a course", description = "Add a discipline to an existing course by discipline name. " +
            "Request requires use of a bearer token. Access restricted to Role='COORDINATOR'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Discipline added to course successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = CourseResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Discipline or course not found",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Feature not allowed for ROLE_STUDENT or ROLE_TEACHER",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PatchMapping("/{courseName}/add/disciplines/{discipline}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<CourseResponseDto> addDiscipline(@PathVariable String courseName, @PathVariable String discipline) {
        CourseResponseDto response = services.addDiscipline(courseName, discipline);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Remove a discipline from a course", description = "Remove a discipline from an existing course by discipline name. " +
            "Request requires use of a bearer token. Access restricted to Role='COORDINATOR'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Discipline removed from course successfully"),
                    @ApiResponse(responseCode = "404", description = "Discipline or course not found",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Feature not allowed for ROLE_STUDENT or ROLE_TEACHER",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PatchMapping("/{courseName}/remove/disciplines/{discipline}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<CourseResponseDto> removeDiscipline(@PathVariable String courseName, @PathVariable String discipline) {
        CourseResponseDto response = services.removeDiscipline(courseName, discipline);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Change course coordinator", description = "Change the coordinator of a course by providing coordinator ID. " +
            "Request requires use of a bearer token. Access restricted to Role='COORDINATOR'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Coordinator changed successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = CourseResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Course or teacher not found",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Feature not allowed for ROLE_STUDENT or ROLE_TEACHER",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PatchMapping("/{courseName}/change/coordinators/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<CourseResponseDto> changeCoordinator(@PathVariable String courseName, @PathVariable Long id) {
        CourseResponseDto update = services.changeCoordinator(courseName, id);
        return ResponseEntity.ok().body(update);
    }
}
