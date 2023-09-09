package ru.hogwarts.school;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.excepcion.FacultyException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.implement.FacultyServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
            when(facultyRepository.findByNameAndColor(faculty.getName(), faculty.getColor()))
                    .thenReturn(Optional.of(faculty));
            FacultyException result = assertThrows(FacultyException.class, () -> underTest.createFaculty(faculty));
            assertEquals("уже такой фак есть", result.getMessage());
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
        void findByColor_areFacultyWithColorInDatabase_returnListWithFacultyByColor() {
            when(facultyRepository.findByColor(faculty.getColor())).thenReturn(List.of(faculty));
            List<Faculty> res = underTest.returnFacultyByNameAndColor(faculty.getColor());
            assertEquals(List.of(faculty), res);
        }
        @Test
        void findByColor_areNotFacultyWithColorInDatabase_returnListEmptyList() {
            when(facultyRepository.findByColor("белый")).thenReturn(new ArrayList<Faculty>());
            List<Faculty> result = underTest.returnFacultyByNameAndColor("белый");
            List<Faculty> expected = Collections.<Faculty>emptyList();
            assertEquals(expected, result);
        }
    }
}
