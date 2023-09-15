package ru.hogwarts.school.service.implement;

import nonapi.io.github.classgraph.utils.FileUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.service.service.StudentService;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class AvatarServiceImplTest {
    StudentService studentService = mock(StudentService.class);
    AvatarRepository avatarRepository = mock(AvatarRepository.class);
    String avatarDir = "./src/test/resources/avatar";
    AvatarServiceImpl avatarService = new AvatarServiceImpl(avatarDir, avatarRepository,
             studentService);
    Student student = new Student(1L,"Harry", 10);


    @Test
    void uploadAvatar_avatarSavedToDbAndDirectory() throws IOException {
        MultipartFile file = new MockMultipartFile("11.pdf", "11.pdf",
                "pdf",new byte[]{});
        when(studentService.readStudent(student.getId())).thenReturn(student);
        avatarService.uploadAvatar(1L,file);
        when(avatarRepository.findById(student.getId())).thenReturn(Optional.empty());
        avatarService.uploadAvatar(student.getId(), file);
        verify(avatarRepository,times(1)).save(any());
        assertTrue(FileUtils.canRead(new File(avatarDir
                + "/" +student.getId() + "." + file.getContentType())));
    }

    @Test
    void getPage__returnListOfAvatar(){
        when(avatarRepository.findAll((PageRequest)any()))
                .thenReturn(new PageImpl<>(List.of()));
        Collection<Avatar> result = avatarService.getPage(0,10);
        assertEquals(List.of(),result);
    }
}