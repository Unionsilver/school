package ru.hogwarts.school.service.service;

import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentService {
    Student createStudent(Student student);
    Student readStudent (long id);

    Student updateStudent(Student student);

    Student deleteStudent (long id);
    List<Student> readAll(int age);
}
