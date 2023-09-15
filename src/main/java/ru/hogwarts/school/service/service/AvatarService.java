package ru.hogwarts.school.service.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

public interface AvatarService {
    void uploadAvatar (Long studentID, MultipartFile avatarFile) throws IOException;
    Avatar findAvatar(Long studentId);

    byte[] generationDataForDB(Path filePath) throws IOException;

    List<Avatar> getPage(int size, int pageNumber);

    String getExtension(String fileName);

    Avatar findAvatar(long studentId);

    String getExtensions(String fileName);
}
