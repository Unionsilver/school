package ru.hogwarts.school.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    @LocalServerPort
    int port;
    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    private FacultyRepository facultyRepository;

    @AfterEach
    void afterEach(){
        studentRepository.deleteAll();

    }
    Faculty faculty = new Faculty(4L, "Love", "Red");
    Student student = new Student(7L, "Ihor", 30);
    Student student1 = new Student(8L, "Angelina", 27);

    @Test
    void create__returnStatus200AndStudent(){

        ResponseEntity<Student> studentResponseEntity = restTemplate.postForEntity("http://localhost:"
                + port + "/student", student, Student.class);
        assertEquals(HttpStatus.OK, studentResponseEntity.getStatusCode());
        assertEquals(student.getName(),studentResponseEntity.getBody().getName());
        assertEquals(student.getAge(),studentResponseEntity.getBody().getAge());
    }
    @Test
    void read__studentNotInDb_returnStatus400AndExceptionTest(){
        ResponseEntity<String> studentResponseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/" + student.getId(),String.class);
        assertEquals(HttpStatus.BAD_REQUEST,studentResponseEntity.getStatusCode());
        assertEquals("студент не найден", studentResponseEntity.getBody());

    }

    @Test
    void getByAge__returnStatus200AndStudentList(){
        studentRepository.save(student);

        ResponseEntity<List<Student>> exchange = restTemplate.exchange(
                "http://localhost:" + port + "/student/age?age="
                        + student.getAge(),
                HttpMethod.GET, null,
                new ParameterizedTypeReference <>() {
                });
        assertEquals(HttpStatus.OK, exchange.getStatusCode());
        assertEquals(List.of(student), exchange.getBody());
    }
    @Test
    void update__returnStatus200AndStudent() {
        Student save = studentRepository.save(student);
        ResponseEntity<Student> studentResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/student/" ,
                HttpMethod.PUT,
                new HttpEntity<>(save),
                Student.class);
        assertEquals(HttpStatus.OK, studentResponseEntity.getStatusCode());
        assertEquals(student.getName(), studentResponseEntity.getBody().getName());
        assertEquals(student.getAge(), studentResponseEntity.getBody().getAge());
    }
    @Test
    void delete__returnStatus200AndStudent() {
        Student save = studentRepository.save(student);
        ResponseEntity<Student> studentResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/student/" + save.getId(),
                HttpMethod.DELETE, null, Student.class);
        assertEquals(HttpStatus.OK, studentResponseEntity.getStatusCode());
        assertEquals(student.getName(), studentResponseEntity.getBody().getName());
        assertEquals(student.getAge(), studentResponseEntity.getBody().getAge());
    }

    @Test
    void readAllByAgeBetween__returnStatus200AndStudentList() {
        Student save = studentRepository.save(student);
        ResponseEntity<List<Student>> exchange = restTemplate.exchange(
                "http://localhost:" + port + "/student/ageInRange?floor=1&ceiling=30",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Student>>() {
                });
        assertEquals(HttpStatus.OK, exchange.getStatusCode());
        assertEquals(List.of(save), exchange.getBody());

    }
    @Test
    public void findNamesStartingWithTheLetterIsA__returnStatus200AndStringNameList() {

        String url = "http://localhost:" + port + "/student/name-start-with-a";

        ResponseEntity<String[]> responseEntity = restTemplate.getForEntity(url, String[].class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        String[] names = responseEntity.getBody();
        assertNotNull(names);

    }

    @Test
    public void findAvgAgeByStream_returnStatus200AndAvgAgeDoubleNum() {

        facultyRepository.save(faculty);
        studentRepository.save(student);
        studentRepository.save(student1);

        String url = "http://localhost:" + port + "/student/age-avg-steam";
        ResponseEntity<Double> responseEntity = restTemplate.getForEntity(url, Double.class);
        Double result = (double) (student.getAge() + student1.getAge()) / 2;

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Double avgAge = responseEntity.getBody();
        assertEquals(result, avgAge);
        assertNotNull(avgAge);

    }
}
