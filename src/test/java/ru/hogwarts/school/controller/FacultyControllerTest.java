package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import liquibase.pro.packaged.C;
import org.checkerframework.checker.nullness.Opt;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.implement.FacultyServiceImpl;
import ru.hogwarts.school.service.implement.StudentServiceImpl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {FacultyController.class})
public class FacultyControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    FacultyController facultyController;
    @SpyBean
    FacultyServiceImpl facultyService;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    FacultyRepository facultyRepository;
    @MockBean
    StudentRepository studentRepository;
    @SpyBean
    StudentServiceImpl studentService;
    Faculty faculty = new Faculty(1L,"Love", "Red");
    Faculty faculty2 = new Faculty(1L,"Friendship","Green");
    void create__status200AndSavedToDb() throws Exception {
         when(facultyRepository.findByName(faculty.getName()))
                 .thenReturn(Optional.empty());
         when(facultyRepository.save(faculty)).thenReturn(faculty);
         mockMvc.perform(post("/faculty")
                 .content(objectMapper.writeValueAsString(faculty))
                 .contentType(MediaType.APPLICATION_JSON)
                 .accept(MediaType.APPLICATION_JSON))
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$.name").value((faculty.getName())))
                 .andExpect(jsonPath("$.color").value(faculty.getColor()));
     }
     @Test
    void findByColorName__ReturnListOfFacultiesWithColorOrNameMatchingProvideSearchString () throws Exception{
        when(facultyRepository.findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(anyString(), anyString()))
                .thenReturn(List.of(faculty));

        mockMvc.perform(get(
                "/faculty/colorOrName?searchString=r"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name")
                        .value(faculty.getName()))
                .andExpect(jsonPath("$[0].color")
                        .value(faculty.getColor()));
     }
     @Test
    void update__status200AndUpdateToDb() throws Exception {
        when(facultyRepository.findById(faculty.getId()))
                .thenReturn(Optional.of(faculty));
        when(facultyRepository.save(faculty)).thenReturn(faculty);
        mockMvc.perform(post("/faculty")
                        .content(objectMapper.writeValueAsString(faculty))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()));
    }
    @Test
    void delete__status200AndDeleteToDb() throws Exception{
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));
        mockMvc.perform(delete("/faculty/" + faculty.getId())).andExpect(status().isOk());
//                        .content(objectMapper.writeValueAsString(faculty))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").value(faculty.getId()));
    }
    @Test
     public void findLongestName__andReturnLongestName () throws Exception{
        List<Faculty> test = List.of(faculty);
          when(facultyRepository.findAll()).thenReturn(test);
          mockMvc.perform(MockMvcRequestBuilders.get("/faculty/longest-name"))
                  .andExpect(status().isOk())
                  .andExpect(jsonPath("$").value(faculty.getName()));
    }
}
