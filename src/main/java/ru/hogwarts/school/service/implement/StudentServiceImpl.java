package ru.hogwarts.school.service.implement;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.excepcion.StudentException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements ru.hogwarts.school.service.service.StudentService {
 private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(Student student) {
        if (studentRepository.findByNameAndAge(student.getName(),student.getAge()).isPresent()){
            throw new StudentException("студент в базе");
        }
        return studentRepository.save(student);
    }

    @Override
    public Student readStudent(long id) {

        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new StudentException("студент не найден");
        }
        return student.get();

    }

    @Override
    public Student updateStudent(Student student) {

        if (studentRepository.findById(student.getId()).isEmpty()) {
            throw new StudentException("студент не найден");
        }
        return studentRepository.save(student);
    }

    @Override
    public Student deleteStudent(long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()){
            throw new StudentException("студент не найден");
        }
       studentRepository.deleteById(id);
        return student.get();
    }

    @Override
    public List<Student> readAll() {
        return studentRepository.findAll();
    }

    @Override
    public List<Student> getAllByAge(int age) {
       return  studentRepository.findByAge(age);
    }

    @Override
    public List<Student> getAllStudentByFacultyId(long id) {
        return studentRepository.findByFacultyId(id);
    }

    @Override
    public List<Student> getStudentsByAgeInRange(int floor, int ceiling) {
        return studentRepository.findByAgeBetween(floor, ceiling);
    }

    @Override
    public Integer findStudentCount() {
        return studentRepository.findStudentCount();
    }

    @Override
    public Integer findAvgAge() {
        return studentRepository.findAvgAge();
    }

    @Override
    public List<Student> findFiveLastStudents() {
        return studentRepository.getLast(5);
    }
}