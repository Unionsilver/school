package ru.hogwarts.school.service.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface FacultyService {

    Faculty createStudent(Faculty faculty);

    Faculty readStudent (long id);
    Faculty updateStudent (Faculty student);
    Faculty deleteStudent (long id);

    List<Faculty> readAll(String color);
}
