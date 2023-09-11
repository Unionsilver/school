package ru.hogwarts.school.service.implement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentService;
import ru.hogwarts.school.service.service.AvatarService;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;
@Service
public class AvatarServiceImpl implements AvatarService {
    private final String avatarDir;
    private final AvatarRepository avatarRepository;
    private final StudentService studentService;


    public AvatarServiceImpl(String avatarDir, AvatarRepository avatarRepository,
                             @Value("${path.to.avatars.folder}") StudentService studentRepository) {
        this.avatarDir = avatarDir;
        this.avatarRepository = avatarRepository;
        this.studentService = studentRepository;
    }

    @Override
    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        Student student = studentService.findById(studentId).orElseThrow();
        Path filePath = Path.of(avatarDir, studentId + "." +
                getExtension(Objects.requireNonNull(file.getOriginalFilename())));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (InputStream is = file.getInputStream()){
            OutputStream os = Files.newOutputStream(filePath,CREATE_NEW);
            BufferedInputStream bis = new BufferedInputStream(is, 1024);
            BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
            {
                bis.transferTo(bos);
            }
            Avatar avatar = findAvatar(studentId);
            avatar.setStudent(student);
            avatar.setFilePath(filePath.toString());
            avatar.getFileSize(file.getSize());
            avatar.setMediaType(file.getContentType());
            avatar.setData(generationDataForDB(filePath));

            avatarRepository.save(avatar);
        }
    }

    @Override
    public Avatar findAvatar(Long studentId) {
        return avatarRepository.findByStudentId(studentId).orElse(new Avatar());
    }
    @Override
    public byte[] generationDataForDB(Path filePath) throws IOException {
        try (
                OutputStream is = Files.newOutputStream(filePath);
                BufferedOutputStream bis = new BufferedOutputStream(is, 1024);
                ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read((ImageInputStream) bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics2D = preview.createGraphics();
            graphics2D.drawImage(image, 0, 0, 100, height, null);
            graphics2D.dispose();
            ImageIO.write(preview, getExtensions(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }
    }
    @Override
    public String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
    @Override
    public Avatar findAvatar(long studentId) {
        return avatarRepository.findByStudentId(studentId).orElseThrow();
    }
    @Override
    public String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") +1);

    }
}
