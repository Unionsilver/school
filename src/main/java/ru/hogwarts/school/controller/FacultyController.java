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
        return facultyService.createFaculty(faculty);
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
    public Faculty find_by_id (@PathVariable String color) {
        return (Faculty) facultyService.returnFacultyByNameAndColor(color);
    }
}
