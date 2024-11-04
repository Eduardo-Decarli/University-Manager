package com.compass.Desafio_02.web.controller;

import com.compass.Desafio_02.jwt.JwtUserDetails;
import com.compass.Desafio_02.services.CoordinatorServices;
import com.compass.Desafio_02.web.dto.CoordinatorCreateDto;
import com.compass.Desafio_02.web.dto.CoordinatorResponseDto;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Coordinators", description = "Contains all operations related to the coordinator resource")
@RestController
@RequestMapping("/api/v1/coordinator")
@Validated
public class CoordinatorController {

    private final CoordinatorServices services;

    public CoordinatorController(CoordinatorServices services) {
        this.services = services;
    }

    @Operation(summary = "Create a new coordinator", description = "Resource to create a new coordinator. " +
            "Request requires use of a bearer token. Access restricted to Role='COORDINATOR'",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Coordinator created successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = CoordinatorResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Coordinator already registered",
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
    //@PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<CoordinatorResponseDto> create(@Valid @RequestBody CoordinatorCreateDto coordinator) {
        CoordinatorResponseDto response = services.createCoordinator(coordinator);
        return ResponseEntity.status(201).body(response);
    }

    @Operation(summary = "Get list of all coordinators", description = "Resource to retrieve a list of all registered coordinators. " +
            "Request requires use of a bearer token. Access restricted to Role='COORDINATOR'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of coordinators retrieved successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    array = @ArraySchema(schema = @Schema(implementation = CoordinatorResponseDto.class)))),
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
    public ResponseEntity<List<CoordinatorResponseDto>> getAllCoordinator() {
        List<CoordinatorResponseDto> Coordinators = services.getAllCoordinators();
        return ResponseEntity.ok().body(Coordinators);
    }

    @Operation(summary = "Get coordinator by ID", description = "Resource to retrieve a coordinator's details by their ID. "
            + "Request requires use of a bearer token. Access restricted to Role='COORDINATOR'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Coordinator found and returned successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = CoordinatorResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Coordinator not found",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Feature not allowed for ROLE_STUDENT or ROLE_TEACHER",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<CoordinatorResponseDto> getCoordinatorById(@PathVariable long id){
        CoordinatorResponseDto coordinator = services.getCoordinatorById(id);
        return ResponseEntity.ok().body(coordinator);
    }

    @Operation(summary = "Update coordinator details by ID", description = "Resource to update the details of the coordinator by ID. "
            + "Request requires use of a bearer token. Access restricted to Role='COORDINATOR'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Coordinator details updated successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = CoordinatorResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Access denied. Only COORDINATOR role can access this resource",
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
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<CoordinatorResponseDto> updateCoordinator(@Valid @PathVariable Long id, @RequestBody CoordinatorCreateDto coordinator) {
        CoordinatorResponseDto updatedCoordinator = services.updateCoordinator(id, coordinator);
        return ResponseEntity.ok().body(updatedCoordinator);
    }

    @Operation(summary = "Delete coordinator by ID", description = "Resource to delete a coordinator by their ID. "
            + "Request requires use of a bearer token. Access restricted to Role='COORDINATOR'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "coordinator deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "coordinator not found",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Feature not allowed for ROLE_STUDENT or ROLE_TEACHER",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<Void> deleteCoordinatorById(@PathVariable long id) {
        services.deleteCoordinator(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get coordinator details by token", description = "Resource to retrieve the details of the authenticated coordinator based on their token. "
            + "Request requires use of a bearer token. Access restricted to Role='COORDINATOR'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Coordinator details retrieved successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = DisciplineResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Access denied. Only COORDINATOR role can access this resource",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized. Invalid or missing token",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/me")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<CoordinatorResponseDto> myData(@AuthenticationPrincipal JwtUserDetails userDetails) {
        CoordinatorResponseDto response = services.getMyData(userDetails.getId());
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Update coordinator details by token", description = "Resource to update the details of the authenticated coordinator based on their token. "
            + "Request requires use of a bearer token. Access restricted to Role='COORDINATOR'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Coordinator details updated successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = CoordinatorResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Access denied. Only COORDINATOR role can access this resource",
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
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<CoordinatorResponseDto> updateMyData(@AuthenticationPrincipal JwtUserDetails userDetails, @RequestBody CoordinatorCreateDto coordinator) {
        CoordinatorResponseDto updatedCoordinator = services.updateMyData(userDetails.getId(), coordinator);
        return ResponseEntity.ok().body(updatedCoordinator);
    }

    @Operation(summary = "Get coordinator's course by token",
            description = "Resource to retrieve the course assigned to the authenticated coordinator based on their token. "
                    + "Request requires use of a bearer token. Access restricted to Role='COORDINATOR'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Coordinator's course retrieved successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = CourseResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Access denied. Only COORDINATOR role can access this resource",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized. Invalid or missing token",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "No course assigned to the authenticated coordinator",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/me/course")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<CourseResponseDto> myCourse(@AuthenticationPrincipal JwtUserDetails userDetails) {
        CourseResponseDto course = services.getMyCourse(userDetails.getId());
        return ResponseEntity.ok().body(course);
    }

    @Operation(summary = "Get students of a specific discipline by coordinator token",
            description = "Resource to retrieve a list of students enrolled in a specific discipline taught by the authenticated coordinator. "
                    + "Request requires use of a bearer token. Access restricted to Role='COORDINATOR'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of students retrieved successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    array = @ArraySchema(schema = @Schema(implementation = DisciplineResponseDto.class)))),
                    @ApiResponse(responseCode = "403", description = "Access denied. Only COORDINATOR role can access this resource",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized. Invalid or missing token",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Disciplines not found for the authenticated coordinator",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/me/course/disciplines")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<List<DisciplineResponseDto>> myCourseDisciplines(@AuthenticationPrincipal JwtUserDetails userDetails) {
        List<DisciplineResponseDto> response = services.getDisciplinesInCourse(userDetails.getId());
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Get coordinator's discipline by token", description = "Resource to retrieve a list of discipline assigned to the authenticated coordinator based on their token. "
            + "Request requires use of a bearer token. Access restricted to Role='COORDINATOR'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of disciplines retrieved successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    array = @ArraySchema(schema = @Schema(implementation = DisciplineResponseDto.class)))),
                    @ApiResponse(responseCode = "403", description = "Access denied. Only COORDINATOR role can access this resource",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized. Invalid or missing token",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/me/disciplines")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<List<DisciplineResponseDto>> myDisciplines(@AuthenticationPrincipal JwtUserDetails userDetails) {
        List<DisciplineResponseDto> response = services.getMyDisciplines(userDetails.getId());
        return ResponseEntity.ok().body(response);
    }

}
