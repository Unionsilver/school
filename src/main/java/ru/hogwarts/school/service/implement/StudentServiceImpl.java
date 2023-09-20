package ru.hogwarts.school.service.implement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.hogwarts.school.excepcion.StudentException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        if (studentRepository.findByNameAndAge(student.getName(), student.getAge()).isPresent()) {
            throw new StudentException("студент в базе");
        }

        Student savedStudent = studentRepository.save(student);
        logger.info("из метода create создали студента " + savedStudent);
        return savedStudent;
    }

    @Override
    public Student readStudent(long id) {
        logger.info("был вызван метод для read с данными" + id);

        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new StudentException("студент не найден");
        }
        Student student1 = student.get();
        logger.info("из метода read получили " + student1);
        return student1;

    }

    @Override
    public Student updateStudent(Student student) {
        logger.info("был вызван метод для update с данными" + student);

        if (studentRepository.findById(student.getId()).isEmpty()) {
            throw new StudentException("студент не найден");
        }
        Student saveStudent = studentRepository.save(student);
        logger.info("из метода update вернули " + saveStudent);
        return saveStudent;
    }

    @Override
    public Student deleteStudent(long id) {
        logger.info("был вызван метод для delete с данными" + id);
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new StudentException("студент не найден");
        }
        studentRepository.deleteById(id);
        Student student1 = student.get();
        logger.info("из метода delete удалила студента " + student1);
        return student1;
    }

    @Override
    public List<Student> readAll() {
        logger.info("был вызван метод для readAll всех студентов ");
        List<Student> students = studentRepository.findAll();
        logger.info("из метода readAll вернули всех" + students);
        return students;
    }

    @Override
    public List<Student> getAllByAge(int age) {
        logger.info("был вызван метод для getAllByAge  с данными" + age);
        List<Student> byAgeGet = studentRepository.findByAge(age);
        logger.info("из метода getAllByAge Нашли по возрасту " + byAgeGet);
        return byAgeGet;
    }

    @Override
    public List<Student> getAllStudentByFacultyId(long id) {
        logger.info("был вызван метод для getAllStudentByFacultyId с данными" + id);
        List<Student> byFacultyId = studentRepository.findByFacultyId(id);
        logger.info("из метода getAllStudentByFacultyId нашли по айди факультет " + byFacultyId);
        return byFacultyId;
    }

    @Override
    public List<Student> getStudentsByAgeInRange(int floor, int ceiling) {
        logger.info("был вызван метод для getStudentsByAgeInRange с данными минимум и максимум" + floor + ceiling);
        List<Student> byAgeBetween = studentRepository.findByAgeBetween(floor, ceiling);
        logger.info("из метода getStudentsByAgeInRange вернули по  возрасту студента   " + byAgeBetween);
        return byAgeBetween;
    }

    @Override
    public Integer findStudentCount() {
        logger.info("был вызван метод для findStudentCount");
        Integer studentCount = studentRepository.findStudentCount();
        logger.info("из метода findStudentCount вернули " + studentCount);
        return studentCount;
    }

    @Override
    public Integer findAvgAge() {
        logger.info("был вызван метод для findAvgAge");
        Integer avgAge = studentRepository.findAvgAge();
        logger.info("из метода findAvgAge вернули средний возвраст студента " + avgAge);
        return avgAge;
    }

    @Override
    public List<Student> findFiveLastStudents() {
        logger.info("был вызван метод для findFiveLastStudents с данными");
        List<Student> lastFiveStudent = studentRepository.getLast(5);
        logger.info("из метода findFiveLastStudents нашли последних 5 студентов " + lastFiveStudent);
        return lastFiveStudent;
    }

    @Override
    public List<String> findNameWithFirstLetterIsA() {
        return  studentRepository.findAll().stream()
                .map(Student::getName)
                .filter(name -> StringUtils.startsWithIgnoreCase(name, "a"))
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.toList());
    }
    @Override
    public Double findAvgAgeByStream(){
        return studentRepository.findAll().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0);

    }
}


