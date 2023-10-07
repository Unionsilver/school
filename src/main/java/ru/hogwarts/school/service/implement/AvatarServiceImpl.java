package ru.hogwarts.school.service.implement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.service.service.AvatarService;
import ru.hogwarts.school.service.service.StudentService;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static com.datical.liquibase.ext.init.InitProjectUtil.getExtension;
import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarServiceImpl implements AvatarService {
    private final Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);
    private final String avatarDir;
    private final AvatarRepository avatarRepository;
    private final StudentService studentService;


    public AvatarServiceImpl(@Value("${path.to.avatars.folder}") String avatarDir, AvatarRepository avatarRepository,
                             StudentService studentService) {
        this.avatarDir = avatarDir;
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }

    @Override
    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        logger.info("был вызван метод для uploadAvatar с данными" + studentId + " and " + file);
        Student student = studentService.readStudent(studentId);
        Path filePath = Path.of(avatarDir, studentId + "." +
                getExtension(Objects.requireNonNull(file.getOriginalFilename())));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (InputStream is = file.getInputStream()) {
            OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
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
            logger.info("аватар был успешно сохранен в БД и папку");

            avatarRepository.save(avatar);
        }
    }

    @Override
    public Avatar findAvatar(Long studentId) {
        logger.info("был вызван метод для findAvatar с данными" + studentId);
        Avatar avatar = avatarRepository.findByStudentId(studentId).orElse(new Avatar());
        logger.info("из метода findAvatar вернули  " + avatar);
        return avatar;
    }

    @Override
    public byte[] generationDataForDB(Path filePath) throws IOException {
        logger.info("был вызван метод для generationDataForDB с данными" + filePath);

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
            byte[] bytes = baos.toByteArray();
            logger.info("для generationDataForDB получены данные " + bytes);

            return bytes;
        }
    }

    @Override
    public List<Avatar> getPage(int pageNumber, int size) {
        logger.info("был вызван метод для getPage с данными" + pageNumber + " and " + size);
        PageRequest request = PageRequest.of(pageNumber, size);
        List<Avatar> content = avatarRepository.findAll(request).getContent();
        logger.info("из метода getPage вернули " + content);
        return content;
    }

    @Override
    public String getExtensions(String fileName) {
        logger.info("был вызван метод для getExtensions с данными" + fileName);
        String substring = fileName.substring(fileName.lastIndexOf(".") + 1);
        logger.info("из метода getExtensions вернули " + substring);
        return substring;
    }
}
