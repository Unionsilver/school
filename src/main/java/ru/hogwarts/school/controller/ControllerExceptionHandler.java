package ru.hogwarts.school.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.hogwarts.school.excepcion.AvatarNotFoundException;
import ru.hogwarts.school.excepcion.FacultyException;
import ru.hogwarts.school.excepcion.StudentException;

@ControllerAdvice
//@ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "Exception")
public class ControllerExceptionHandler {
    Logger logger = LoggerFactory .getLogger(ControllerExceptionHandler.class);
    @ExceptionHandler({StudentException.class, FacultyException.class, AvatarNotFoundException.class})
    public ResponseEntity<String> handleStudentException (StudentException ex) {
        logger.warn(ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        logger.error(String.valueOf(ex));
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Ошибка сервера");
    }

}