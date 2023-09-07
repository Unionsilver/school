package ru.hogwarts.school.service.implement;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.excepcion.FacultyException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.service.FacultyService;

import java.util.List;
import java.util.Optional;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
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

    public List<Student>findById(long id) {
        return studentRepository.findByFaculty_id(id);
    }
}
