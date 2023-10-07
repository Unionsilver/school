package ru.hogwarts.school.excepcion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "Ошибка")
public class AvatarNotFoundException extends Throwable {
    public AvatarNotFoundException(String message) {
        super(message);
    }
}
