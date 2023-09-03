package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.service.FacultyService;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    public final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }


    @PostMapping
    public Student create (@RequestBody Student student){
        return facultyService.createStudent(student);
    }
    @GetMapping("/{id}")
    public Student read (@PathVariable long id){
        return facultyService.readStudent(id);
    }
    @PutMapping
    public Student update (@RequestBody Student student){
        return facultyService.updateStudent(student);
    }
    @DeleteMapping
    public Student delete (@PathVariable long id){
        return facultyService.deleteStudent(id);
    }

}
