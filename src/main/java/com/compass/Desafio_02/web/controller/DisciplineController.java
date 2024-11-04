package com.compass.Desafio_02.web.controller;

import com.compass.Desafio_02.services.DisciplineServices;
import com.compass.Desafio_02.web.dto.DisciplineCreateDto;
import com.compass.Desafio_02.web.dto.DisciplineResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Disciplines", description = "Contains all operations related to the disciplines resource")
@RestController
@RequestMapping("/api/v1/discipline")
@Validated
public class DisciplineController {

    private final DisciplineServices services;

    public DisciplineController(DisciplineServices services){
        this.services = services;
    }

    @Operation(summary = "Create a new discipline", description = "Resource to create a new discipline. " +
            " Request requires use of a bearer token. Access restricted to Role='COORDINATOR'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Registration created successfully",
                            headers = @Header(name = HttpHeaders.LOCATION, description = "URL of the created resource"),
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = DisciplineResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Discipline already exists",
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
    public ResponseEntity<DisciplineResponseDto> create(@Valid @RequestBody DisciplineCreateDto discipline) {
        DisciplineResponseDto response = services.createDiscipline(discipline);
        return ResponseEntity.status(201).body(response);
    }

    @Operation(summary = "Get list of all disciplines", description = "Resource to retrieve a list of all registered disciplines. " +
            "Request requires use of a bearer token. Access restricted to Role='COORDINATOR'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of disciplines retrieved successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = DisciplineResponseDto.class))),
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
    public ResponseEntity<List<DisciplineResponseDto>> getAllRDisciplines(){
        List<DisciplineResponseDto> disciplines = services.getAllDisciplines();
        return ResponseEntity.ok().body(disciplines);
    }

    @Operation(summary = "Get a discipline by ID", description = "Resource to retrieve a discipline's details by their ID. " +
            "Request requires use of a bearer token. Access restricted to Role='COORDINATOR'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Discipline found and returned successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = DisciplineResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Discipline not found",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Feature not allowed for ROLE_STUDENT or ROLE_TEACHER",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<DisciplineResponseDto> getDisciplineById(@PathVariable long id){
        DisciplineResponseDto discipline = services.getDisciplineById(id);
        return ResponseEntity.ok().body(discipline);
    }

    @Operation(summary = "Update a discipline by ID", description = "Resource to update an existing discipline's details. " +
            "Request requires use of a bearer token. Access restricted to Role='COORDINATOR'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Discipline updated successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = DisciplineResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Discipline not found",
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
    @PreAuthorize("hasRole('COORDINATOR') OR hasRole('TEACHER')")
    public ResponseEntity<DisciplineResponseDto> updateDiscipline(@Valid @PathVariable Long id, @RequestBody DisciplineCreateDto discipline) {
        DisciplineResponseDto update = services.updateDiscipline(id, discipline);
        return ResponseEntity.ok().body(update);
    }

    @Operation(summary = "Delete a discipline by ID", description = "Resource to delete a teacher by their ID. " +
            "Request requires use of a bearer token. Access restricted to Role='COORDINATOR'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Discipline deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Discipline not found",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Feature not allowed for ROLE_STUDENT or ROLE_TEACHER",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<Void> deleteDisciplineById(@PathVariable long id) {
        services.deleteDiscipline(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Add a student to a discipline", description = "Add a student to an existing discipline by student's id and discipline's name. " +
            "Request requires use of a bearer token. Access restricted to Role='COORDINATOR'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Student added to discipline successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = DisciplineResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Discipline or student not found",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Feature not allowed for ROLE_STUDENT or ROLE_TEACHER",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PatchMapping("/{disciplineName}/add/student/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<DisciplineResponseDto> addStudentToDiscipline(@Valid @PathVariable String disciplineName, @PathVariable Long id) {
        DisciplineResponseDto discipline = services.addStudentInDisciplineByName(disciplineName, id);
        return ResponseEntity.ok().body(discipline);
    }

    @Operation(summary = "Remove a student from a discipline", description = "Remove a student from an existing discipline by student's id and discipline's name. " +
            "Request requires use of a bearer token. Access restricted to Role='COORDINATOR'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Student removed successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = DisciplineResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Discipline or student not found",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Feature not allowed for ROLE_STUDENT or ROLE_TEACHER",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PatchMapping("/{disciplineName}/remove/student/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<DisciplineResponseDto> removeStudentDiscipline(@Valid @PathVariable String disciplineName, @PathVariable Long id) {
        DisciplineResponseDto discipline = services.removeStudentInDisciplineByName(disciplineName, id);
        return ResponseEntity.ok().body(discipline);
    }

    @Operation(summary = "Add a titular teacher to a discipline", description = "Add a titular teacher to an existing discipline by teacher's or coordinator's email and discipline's name. " +
            "Request requires use of a bearer token. Access restricted to Role='COORDINATOR' or a Teacher managing their own disciplines.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Titular teacher added successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = DisciplineResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Discipline or teacher not found",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Feature not allowed for ROLE_STUDENT or ROLE_TEACHER",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PatchMapping("/{disciplineName}/add/teacher/titular/{emailTeacher}")
    @PreAuthorize("hasRole('COORDINATOR') OR (#emailTeacher == authentication.principal.email)")
    public ResponseEntity<DisciplineResponseDto> addTitularTeacherDiscipline(@Valid @PathVariable String disciplineName, @PathVariable String emailTeacher) {
        DisciplineResponseDto update = services.addTitularTeacherByDiscipline(disciplineName, emailTeacher);
        return ResponseEntity.ok().body(update);
    }

    @Operation(summary = "Remove a titular teacher from a discipline", description = "Removes a titular teacher from an existing discipline by teacher's or coordinator's email and discipline's name. " +
            "Request requires use of a bearer token. Access restricted to Role='COORDINATOR' or a Teacher managing their own disciplines.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Titular teacher removed successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = DisciplineResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Discipline or teacher not found",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Feature not allowed for ROLE_STUDENT or ROLE_TEACHER",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PatchMapping("/{disciplineName}/remove/teacher/titular/{emailTeacher}")
    @PreAuthorize("hasRole('COORDINATOR') OR (#emailTeacher == authentication.principal.email)")
    public ResponseEntity<DisciplineResponseDto> removeTitularTeacherDiscipline(@Valid @PathVariable String disciplineName, @PathVariable String emailTeacher) {
        DisciplineResponseDto update = services.removeTitularTeacherDiscipline(disciplineName, emailTeacher);
        return ResponseEntity.ok().body(update);
    }

    @Operation(summary = "Add a substitute teacher to a discipline", description = "Add a substitute teacher to an existing discipline by teacher's or coordinator's email and discipline's name. " +
            "Request requires use of a bearer token. Access restricted to Role='COORDINATOR' or a Teacher managing their own disciplines.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Substitute teacher added successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = DisciplineResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Discipline or teacher not found",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Feature not allowed for ROLE_STUDENT or ROLE_TEACHER",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PatchMapping("/{disciplineName}/add/teacher/substitute/{emailTeacher}")
    @PreAuthorize("hasRole('COORDINATOR') OR (#emailTeacher == authentication.principal.email)")
    public ResponseEntity<DisciplineResponseDto> addSubstituteTeacherDiscipline(@Valid @PathVariable String disciplineName, @PathVariable String emailTeacher) {
        DisciplineResponseDto update = services.addSubstituteTeacherDiscipline(disciplineName, emailTeacher);
        return ResponseEntity.ok().body(update);
    }

    @Operation(summary = "Remove a substitute teacher from a discipline", description = "remove a substitute teacher from an existing discipline by teacher's or coordinator's email and discipline's name. " +
            "Request requires use of a bearer token. Access restricted to Role='COORDINATOR' or a Teacher managing their own disciplines.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Substitute teacher removed successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = DisciplineResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Discipline or teacher not found",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Feature not allowed for ROLE_STUDENT or ROLE_TEACHER",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PatchMapping("/{disciplineName}/remove/teacher/substitute/{emailTeacher}")
    @PreAuthorize("hasRole('COORDINATOR') OR (#emailTeacher == authentication.principal.email)")
    public ResponseEntity<DisciplineResponseDto> removeSubstituteTeacherDiscipline(@Valid @PathVariable String disciplineName, @PathVariable String emailTeacher) {
        DisciplineResponseDto update = services.removeSubstituteTeacherDiscipline(disciplineName, emailTeacher);
        return ResponseEntity.ok().body(update);
    }

    @Operation(summary = "Add a substitute teacher to a discipline outside of their courser", description = "Add a substitute teacher to an existing discipline by teacher's or coordinator's email and discipline's name. " +
            "Request requires use of a bearer token. Access restricted to Role='COORDINATOR' or a Teacher managing their own disciplines.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Substitute teacher added successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = DisciplineResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Discipline or teacher not found",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Feature not allowed for ROLE_STUDENT or ROLE_TEACHER",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PatchMapping("/{disciplineName}/add/teacher/substitute/off/course/{emailTeacher}")
    @PreAuthorize("hasRole('COORDINATOR') OR (#emailTeacher == authentication.principal.email)")
    public ResponseEntity<DisciplineResponseDto> addSubstituteTeacherOffCourseDiscipline(@Valid @PathVariable String disciplineName, @PathVariable String emailTeacher) {
        DisciplineResponseDto update = services.addSubstituteTeacherOffCourseDiscipline(disciplineName, emailTeacher);
        return ResponseEntity.ok().body(update);
    }

    @Operation(summary = "Remove a substitute teacher from a discipline outside their course", description = "remove a substitute teacher from an existing discipline by teacher's or coordinator's email and discipline's name. " +
            "Request requires use of a bearer token. Access restricted to Role='COORDINATOR' or a Teacher managing their own disciplines.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Substitute teacher removed successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = DisciplineResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Discipline or teacher not found",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Feature not allowed for ROLE_STUDENT or ROLE_TEACHER",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PatchMapping("/{disciplineName}/remove/teacher/substitute/off/course/{emailTeacher}")
    @PreAuthorize("hasRole('COORDINATOR') OR (#emailTeacher == authentication.principal.email)")
    public ResponseEntity<DisciplineResponseDto> removeSubstituteTeacherOffCourseDiscipline(@Valid @PathVariable String disciplineName, @PathVariable String emailTeacher) {
        DisciplineResponseDto update = services.removeSubstituteTeacherOffCourseDiscipline(disciplineName, emailTeacher);
        return ResponseEntity.ok().body(update);
    }
}
