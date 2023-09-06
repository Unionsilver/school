package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.service.FacultyService;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    public final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }


    @PostMapping
    public Faculty create (@RequestBody Faculty faculty){
        return facultyService.createStudent(faculty);
    }
    @GetMapping("/{id}")
    public Faculty read (@PathVariable long id){
        return facultyService.readStudent(id);
    }
    @PutMapping
    public Faculty update (@RequestBody Faculty faculty){
        return facultyService.updateStudent(faculty);
    }
    @DeleteMapping
    public Faculty delete (@PathVariable long id){
        return facultyService.deleteStudent(id);
    }

}
