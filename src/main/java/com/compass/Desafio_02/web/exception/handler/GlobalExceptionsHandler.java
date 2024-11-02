package com.compass.Desafio_02.web.exception.handler;

import com.compass.Desafio_02.web.exception.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionsHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                     HttpServletRequest request,
                                                                     BindingResult result){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "The fields entered are invalid", result));

    }

    @ExceptionHandler(EntityUniqueViolationException.class)
    public ResponseEntity<ErrorMessage> entityUniqueViolationException(EntityUniqueViolationException ex,
                                                                        HttpServletRequest request){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.CONFLICT, ex.getMessage()));

    }

    @ExceptionHandler(EmptyListException.class)
    public ResponseEntity<ErrorMessage> emptyListException(EmptyListException ex,
                                                           HttpServletRequest request){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> entityNotFoundException(EntityNotFoundException ex,
                                                           HttpServletRequest request){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(CourseNotNullException.class)
    public ResponseEntity<ErrorMessage> courseNotNullException(CourseNotNullException ex,
                                                                HttpServletRequest request){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(CoordinatorInCourseUniqueViolationException.class)
    public ResponseEntity<ErrorMessage> coordinatorInCourseUniqueViolationException(CoordinatorInCourseUniqueViolationException ex,
                                                               HttpServletRequest request){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler(QuantityDisciplinesViolationException.class)
    public ResponseEntity<ErrorMessage> quantityDisciplinesViolationException(QuantityDisciplinesViolationException ex,
                                                                                    HttpServletRequest request){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler(InvalidTeacherEmailException.class)
    public ResponseEntity<ErrorMessage> invalidTeacherEmailException(InvalidTeacherEmailException ex,
                                                                              HttpServletRequest request){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler(InvalidStudentEmailException.class)
    public ResponseEntity<ErrorMessage> invalidStudentEmailException(InvalidStudentEmailException ex,
                                                                     HttpServletRequest request){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.CONFLICT, ex.getMessage()));
    }
}
