package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    public final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student create (@RequestBody Student student){
        return studentService.createStudent(student);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Student> read (@PathVariable long id){
        Student student = studentService.readStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }
    @PutMapping
    public ResponseEntity<Student> update (@RequestBody Student student){
        Student foundStudent = studentService.updateStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundStudent);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Student> delete (@PathVariable long id){
        return ResponseEntity.ok().body(studentService.deleteStudent(id));
    }
    @GetMapping()
    public ResponseEntity<List<Student>> allStudent(){
        return  new ResponseEntity<>(studentService.readAll(),HttpStatus.OK);
    }
    @GetMapping("/age")
    public  ResponseEntity<List<Student>> getByAge(@RequestParam int age){
        return new ResponseEntity<>(studentService.getAllByAge(age),HttpStatus.OK);
    }
    @GetMapping("/ageInRange")
    public ResponseEntity<List<Student>> ageInRange(@RequestParam int floor,
                                                    @RequestParam int ceiling) {
        return new ResponseEntity<>(studentService
                .getStudentsByAgeInRange(floor, ceiling), HttpStatus.OK);
    }
    @GetMapping("/count")
    public Integer findStudentCount(){
       return studentService.findStudentCount();
    }
    @GetMapping("/age-avg")
    public Integer findAvgAge(){
        return studentService.findAvgAge();
    }
    @GetMapping("/last-five-student")
    public List<Student> findLastFiveStudent(){
        return studentService.findFiveLastStudents();
    }


}
