package ru.hogwarts.school.service.implement;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.io.IOException;

import static org.mockito.Mockito.mock;

class AvatarServiceImplTest {
    StudentRepository studentRepository = mock(StudentRepository.class);
    AvatarRepository avatarRepository = mock(AvatarRepository.class);
    String avatarDir = "./src/test/resources/avatar";
    AvatarServiceImpl avatarService = new AvatarServiceImpl(avatarDir, avatarRepository,
             studentRepository);


    @Test
    void uploadAvatar_avatarSavedToDbAndDirectory() throws IOException {
        MultipartFile file = new MockMultipartFile("11.pdf", "11.pdf",
                "pdf",new byte[]{});
        avatarService.uploadAvatar(1L,file);
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