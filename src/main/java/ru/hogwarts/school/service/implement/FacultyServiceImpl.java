package ru.hogwarts.school.service.implement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.excepcion.FacultyException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.service.FacultyService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);
    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty getByID(long id) {
        logger.info("был вызван метод для getByID с данными" + id);
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new FacultyException("факультет не найден");
        }
        Faculty faculty1 = faculty.get();
        logger.info("из метода getByID вернули " + faculty1);
        return faculty1;
    }

    @Override
    public Faculty updateFaculty(Faculty faculty) {
        logger.info("был вызван метод для updateFaculty с данными" + faculty);
        if (facultyRepository.findById(faculty.getId()).isEmpty())
            throw new FacultyException("факультет не найден");
        Faculty save = facultyRepository.save(faculty);
        logger.info("из метода updateFaculty сохранили " + save);
        return save;
    }


    @Override
    public Faculty deleteFaculty(long id) {
        logger.info("был вызван метод для deleteFaculty с данными" + id);
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new FacultyException("не найден фак");
        }
        facultyRepository.deleteById(id);
        Faculty faculty1 = faculty.get();
        logger.info("из метода deleteFaculty удалили  " + faculty1);
        return faculty1;
    }
    @Override
    public List<Faculty> searchFacultyByNameOrColor(String searchString) {
        logger.info("был вызван метод для searchFacultyByNameOrColor с данными" + searchString);
        List<Faculty> searchFacultyByNameOrColor =
                facultyRepository.findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(searchString, searchString);
        logger.info("из метода searchFacultyByNameOrColor поиск по имени и  цвету " + searchFacultyByNameOrColor);
        return searchFacultyByNameOrColor;
    }

    @Override
    public Faculty getFacultyByColor(String color) {
        logger.info("был вызван метод для getFacultyByColor с данными" + color);
        Faculty getFacultyByColor = facultyRepository.findByColor(color).orElseThrow(() -> new FacultyException("не найден фак"));
        logger.info("из метода getFacultyByColor вернули " + getFacultyByColor);
        return getFacultyByColor;
    }

    @Override
    public Faculty create(Faculty faculty) {
        logger.info("был вызван метод для create с данными" + faculty);
        if (facultyRepository.findByName(faculty.getName()).isPresent()) {
            throw new FacultyException("такой факультет уже есть в базе");
        }
        Faculty createFaculty = facultyRepository.save(faculty);
        logger.info("из метода create вернули " + createFaculty);
        return createFaculty;
    }
    @Override
    public Faculty read(long id) {
        logger.info("был вызван метод для read с данными" + id);
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new FacultyException("не найден фак");
        }
        Faculty readFacultyId = faculty.get();
        logger.info("из метода read вернули " + readFacultyId);
        return readFacultyId;
    }
    @Override
    public Faculty update(Faculty faculty) {
        logger.info("был вызван метод для update с данными" + faculty);
        if (facultyRepository.findById(faculty.getId()).isEmpty()) {
            throw new FacultyException("не найден фак");
        }
        Faculty updateFaculty = facultyRepository.save(faculty);
        logger.info("из метода update вернули " + updateFaculty);
        return updateFaculty;
    }
    @Override
    public Faculty delete(long id) {
        logger.info("был вызван метод для delete с данными" + id);
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new FacultyException("не найден фак");
        }
        facultyRepository.deleteById(id);
        Faculty deleteById = faculty.get();
        logger.info("из метода delete удалили по айди " + deleteById);
        return deleteById;
    }
    @Override
    public String findByLongestName(){
        return facultyRepository.findAll().stream()
                .map(faculty -> faculty.getName())
                .max(Comparator.comparingInt(name -> name.length()))
                .orElseThrow(() -> new FacultyException("нет имени факультета в БД"));
    }
}
