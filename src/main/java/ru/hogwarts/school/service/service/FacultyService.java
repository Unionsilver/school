package ru.hogwarts.school.service.service;

import ru.hogwarts.school.model.Faculty;

import java.util.List;

public interface FacultyService {

    Faculty createFaculty(Faculty faculty);

    Faculty getByID (long id);
    Faculty updateFaculty (Faculty faculty);
    Faculty deleteFaculty (long id);
    List<Faculty> returnFacultyByNameAndColor(String color);


    Faculty create(Faculty faculty);

    Faculty read(long id);

    Faculty update(Faculty faculty);

    Faculty delete(long id);
}
