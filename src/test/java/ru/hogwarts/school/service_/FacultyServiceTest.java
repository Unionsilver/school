package ru.hogwarts.school.service_;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.excepcion.FacultyException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.implement.FacultyServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public interface FacultyServiceTest {
    @ExtendWith(MockitoExtension.class)
    class FacultyServiceImplTest {
        @Mock
        FacultyRepository facultyRepository;
        @InjectMocks
        FacultyServiceImpl underTest;
        Faculty faculty = new Faculty(1L, "Воздух", "белый");
        @Test
        void create_newFaculty_addAndReturn() {
            when(facultyRepository.save(faculty)).thenReturn(faculty);
            Faculty res = underTest.create(faculty);
            assertEquals(faculty, res);
        }
        @Test
        void create_FacultyInDatabase_throwFacultyCRUDException() {
            when(facultyRepository.findByName(faculty.getName()))
                    .thenReturn(Optional.of(faculty));
            FacultyException result = assertThrows(FacultyException.class, () -> underTest.create(faculty));
            assertEquals("такой факультет уже есть в базе", result.getMessage());
        }
        @Test
        void read_FacultyInDatabase_addAndReturn() {
            when(facultyRepository.findById(faculty.getId())).thenReturn(Optional.ofNullable(faculty));
            Faculty res = underTest.read(faculty.getId());
            assertEquals(faculty, res);
        }
        @Test
        void read_FacultyNotInDatabase_throwFacultyCRUDException() {
            when(facultyRepository.findById(1L)).thenReturn(Optional.empty());
            FacultyException result = assertThrows(FacultyException.class, () -> underTest.read(1L));
            assertEquals("не найден фак", result.getMessage());
        }
        @Test
        void update_FacultyInDatabase_addAndReturn() {
            when(facultyRepository.findById(faculty.getId())).thenReturn(Optional.of(faculty));
            when(facultyRepository.save(faculty)).thenReturn(faculty);
            Faculty result = underTest.update(faculty);
            assertEquals(faculty, result);
        }
        @Test
        void update_FacultyNotInDatabase_throwFacultyCRUDException() {
            when(facultyRepository.findById(faculty.getId())).thenReturn(Optional.empty());
            FacultyException result = assertThrows(FacultyException.class, () -> underTest.update(faculty));
            assertEquals("не найден фак", result.getMessage());
        }
        @Test
        void delete_FacultyInDatabase_deleteAndReturn() {
            when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));
            doNothing().when(facultyRepository).deleteById(1L);
            Faculty result = underTest.delete(1L);
            assertEquals(faculty, result);
        }
        @Test
        void delete_FacultyNotInDatabase_throwFacultyCRUDException() {
            when(facultyRepository.findById(1L)).thenReturn(Optional.empty());
            FacultyException result = assertThrows(FacultyException.class, () -> underTest.read(1L));
            assertThrows(FacultyException.class, () -> underTest.read(1L));
            assertEquals("не найден фак", result.getMessage());
        }
        @Test
        void getFacultyByColor_areFacultyWithColorInDatabase_returnListWithFacultyByColor() {
            when(facultyRepository.findByColor(faculty.getColor())).thenReturn(Optional.of(faculty));
            var res = underTest.getFacultyByColor(faculty.getColor());
            assertEquals(faculty, res);
        }
        @Test
        void getFacultyByColor_areNotFacultyWithColorInDatabase_throwsFacultyException() {
            when(facultyRepository.findByColor("белый")).thenThrow(new FacultyException("не найден фак"));

            var ex = assertThrows(FacultyException.class, ()-> underTest.getFacultyByColor("белый"));

            assertEquals("не найден фак", ex.getMessage());
        }
    }
}
