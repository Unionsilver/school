package ru.hogwarts.school.controller;

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
    public Student read (@PathVariable long id){
        return studentService.readStudent(id);
    }
    @PutMapping
    public Student update (@RequestBody Student student){
        return studentService.updateStudent(student);
    }
    @DeleteMapping("idd")
    public Student delete (@PathVariable long id){
        return studentService.deleteStudent(id);
    }
    @DeleteMapping()
    public Student all (){
        return (Student) studentService.readAll(1);
    }

}
