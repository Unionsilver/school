package ru.hogwarts.school.excepcion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "Exception")
public class FacultyException extends RuntimeException {
    public FacultyException(String message) {
        super(message);
    }
}
