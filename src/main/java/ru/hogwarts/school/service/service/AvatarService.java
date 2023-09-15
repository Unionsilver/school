package ru.hogwarts.school.service.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

import java.io.IOException;
import java.nio.file.Path;

public interface AvatarService {
    void uploadAvatar (Long studentID, MultipartFile avatarFile) throws IOException;
    Avatar findAvatar(Long studentId);

    byte[] generationDataForDB(Path filePath) throws IOException;

    String getExtension(String fileName);

    Avatar findAvatar(long studentId);

    String getExtensions(String fileName);
}
