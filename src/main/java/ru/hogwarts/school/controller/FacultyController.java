package ru.hogwarts.school.controller;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.service.FacultyService;
import ru.hogwarts.school.service.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;
    private final StudentService studentService;

    public FacultyController(FacultyService facultyService, StudentService studentService) {
        this.facultyService = facultyService;
        this.studentService = studentService;
    }


    @PostMapping
    public Faculty create (@RequestBody Faculty faculty){
        return facultyService.create(faculty);
    }
    @GetMapping("/{id}")
    public Faculty read (@PathVariable long id){
        return facultyService.getByID(id);
    }
    @PutMapping
    public Faculty update (@RequestBody Faculty faculty){
        return facultyService.updateFaculty(faculty);
    }
    @DeleteMapping("/{id}")
    public Faculty delete (@PathVariable long id){
        return facultyService.deleteFaculty(id);
    }
    @GetMapping("/{id}/students")
    public Collection<Student> findStudentByFacultyId (@PathVariable long id) {
        return studentService.getAllStudentByFacultyId(id);
    }
    @GetMapping("/colorOrName")
    public Collection<Faculty> findByColorName (@RequestParam String searchString) {
        return  facultyService.searchFacultyByNameOrColor(searchString);
    }
    @GetMapping("/longest-name")
    public String findLongestName (){
        return facultyService.findByLongestName();
    }
}