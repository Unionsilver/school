package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.service.StudentService;

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
    @DeleteMapping("idd")
    public ResponseEntity<Student> delete (@PathVariable long id){
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("{id}")
    public Student all (){
        return (Student) studentService.readAll(1);
    }

}
