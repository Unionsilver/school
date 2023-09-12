package ru.hogwarts.school.service.implement;

import nonapi.io.github.classgraph.utils.FileUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.service.service.StudentService;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

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
    void findAvatar() {
    }

    @Test
    void generationDataForDB() {
    }

    @Test
    void getExtension() {
    }

    @Test
    void testFindAvatar() {
    }

    @Test
    void getExtensions() {
    }
}