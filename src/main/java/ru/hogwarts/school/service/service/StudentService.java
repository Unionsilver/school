package ru.hogwarts.school.service.service;

import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentService {
    Student createStudent(Student student);
    Student readStudent (long id);

    Student updateStudent(Student student);

    Student deleteStudent (long id);
    List<Student> readAll();
    List<Student> getAllByAge(int age);
    List<Student> getAllStudentByFacultyId(long id);
    List<Student> getStudentsByAgeInRange(int floor, int ceiling);
    Integer findStudentCount();

    Integer findAvgAge ();
    List<Student> findFiveLastStudents();

    List<String> findNameWithFirstLetterIsA();

    Double findAvgAgeByStream();
}
