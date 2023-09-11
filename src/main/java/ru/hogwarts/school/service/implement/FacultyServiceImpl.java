package ru.hogwarts.school.service.implement;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.excepcion.FacultyException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.service.FacultyService;

import java.util.List;
import java.util.Optional;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty getByID(long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new FacultyException("факультет не найден");
        }
        return faculty.get();
    }

    @Override
    public Faculty updateFaculty(Faculty faculty) {
        if (facultyRepository.findById(faculty.getId()).isEmpty())
            throw new FacultyException("факультет не найден");
        return facultyRepository.save(faculty);
    }


    @Override
    public Faculty deleteFaculty(long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new FacultyException("не найден фак");
        }
        facultyRepository.deleteById(id);
        return faculty.get();
    }
    @Override
    public List<Faculty> searchFacultyByNameOrColor(String searchString) {
        return facultyRepository.findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(searchString,searchString);
    }

    @Override
    public Faculty getFacultyByColor(String color) {
        return facultyRepository.findByColor(color).orElseThrow(() -> new FacultyException("не найден фак"));
    }

    @Override
    public Faculty create(Faculty faculty) {
        if (facultyRepository.findByName(faculty.getName()).isPresent()) {
            throw new FacultyException("такой факультет уже есть в базе");
        }
        return facultyRepository.save(faculty);
    }
    @Override
    public Faculty read(long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new FacultyException("не найден фак");
        }
        return faculty.get();
    }
    @Override
    public Faculty update(Faculty faculty) {
        if (facultyRepository.findById(faculty.getId()).isEmpty()) {
            throw new FacultyException("не найден фак");
        }
        return facultyRepository.save(faculty);
    }
    @Override
    public Faculty delete(long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new FacultyException("не найден фак");
        }
        facultyRepository.deleteById(id);
        return faculty.get();
    }
}
