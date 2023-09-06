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
    public Faculty createStudent(Faculty faculty) {
        if (facultyRepository.findByNameAndColor(faculty.getName(), faculty.getColor()).isPresent()) {
            throw new FacultyException("факультет в базе");
        }
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty readStudent(long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new FacultyException("факультет не найден");
        }
        return faculty.get();
    }

    @Override
    public Faculty updateStudent(Faculty faculty) {
        if (facultyRepository.findById(faculty.getId()).isEmpty())
            throw new FacultyException("факультет не найден");
        return facultyRepository.save(faculty);
    }


    @Override
    public Faculty deleteStudent(long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new FacultyException("факультет не найдет");
        }
        facultyRepository.deleteById(id);
        return faculty.get();
    }

    @Override
    public List<Faculty> readAll(String color) {
        return facultyRepository.findByColor(color);
    }
}
