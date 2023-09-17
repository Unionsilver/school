package ru.hogwarts.school.service.implement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.excepcion.StudentException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements ru.hogwarts.school.service.service.StudentService {
    private final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
 private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(Student student) {
        logger.info("был вызван метод для create с данными" + student);
        if (studentRepository.findByNameAndAge(student.getName(),student.getAge()).isPresent()){
            throw new StudentException("студент в базе");
        }

        Student savedStudent = studentRepository.save(student);
        logger.info("из метода create вернули " + student);
        return savedStudent;
    }

    @Override
    public Student readStudent(long id) {
        logger.info("был вызван метод для read с данными" + id);

        Optional<Student> student = studentRepository.findById(id);
        logger.info("из метода read вернули " + id);
        if (student.isEmpty()) {
            throw new StudentException("студент не найден");
        }
        return student.get();

    }

    @Override
    public Student updateStudent(Student student) {
        logger.info("был вызван метод для update с данными" + student);

        if (studentRepository.findById(student.getId()).isEmpty()) {
            throw new StudentException("студент не найден");
        }
        Student saveStudent = studentRepository.save(student);
        logger.info("из метода update вернули " + student);
        return saveStudent;
    }

    @Override
    public Student deleteStudent(long id) {
        logger.info("был вызван метод для delete с данными" + id);
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()){
            throw new StudentException("студент не найден");
        }
       studentRepository.deleteById(id);
        logger.info("из метода delete вернули " + id);
        return student.get();
    }

    @Override
    public List<Student> readAll() {
        logger.info("был вызван метод для delete с данными всех");
        List<Student> students = studentRepository.findAll();
        logger.info("из метода delete вернули всех");
        return students;
    }

    @Override
    public List<Student> getAllByAge(int age) {
        logger.info("был вызван метод для getAllByAge  с данными" + age);
        List<Student> byAgeGet = studentRepository.findByAge(age);
        logger.info("из метода getAllByAge вернули " + age);
       return byAgeGet;
    }

    @Override
    public List<Student> getAllStudentByFacultyId(long id) {
        logger.info("был вызван метод для getAllStudentByFacultyId с данными" + id);
        List<Student> byFacultyId = studentRepository.findByFacultyId(id);
        logger.info("из метода getAllStudentByFacultyId вернули " + id);
        return byFacultyId;
    }

    @Override
    public List<Student> getStudentsByAgeInRange(int floor, int ceiling) {
        logger.info("был вызван метод для getStudentsByAgeInRange с данными" + floor + ceiling);
        List<Student> byAgeBetween = studentRepository.findByAgeBetween(floor, ceiling);
        logger.info("из метода getStudentsByAgeInRange вернули " + floor + ceiling);
        return byAgeBetween;
    }

    @Override
    public Integer findStudentCount() {
        logger.info("был вызван метод для findStudentCount с данными");
        Integer studentCount = studentRepository.findStudentCount();
        logger.info("из метода findStudentCount вернули ");
        return studentCount;
    }

    @Override
    public Integer findAvgAge() {
        logger.info("был вызван метод для findAvgAge с данными");
        Integer avgAge = studentRepository.findAvgAge();
        logger.info("из метода findAvgAge вернули ");
        return avgAge;
    }

    @Override
    public List<Student> findFiveLastStudents() {
        logger.info("был вызван метод для findFiveLastStudents с данными");
        List<Student> lastFiveStudent = studentRepository.getLast(5);
        logger.info("из метода findFiveLastStudents вернули ");
        return lastFiveStudent;
    }
}