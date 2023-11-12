package pro.sky.ShelterTelegramBot.service.impl;



import org.springframework.beans.factory.annotation.Value;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.ShelterTelegramBot.model.Attachment;
import pro.sky.ShelterTelegramBot.repository.AttachmentRepository;
import pro.sky.ShelterTelegramBot.service.AttachmentService;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;

    @Value("${attachments.dir.path}")
    private Path attachmentsDir;


    public AttachmentServiceImpl(AttachmentRepository attachmentRepository){
        this.attachmentRepository=attachmentRepository;
    }

    @Override
    public Attachment addAttachment(MultipartFile file) throws IOException {
        String absolutePath=attachmentsDir.toAbsolutePath().toString();
        // Создаем директорию если ее не существует
        File uploadDir = new File(absolutePath);
        // Если директория uploads не существует, то создаем ее
        if (!uploadDir.exists()) {
            //uploadDir.mkdirs();
            Files.createDirectories(attachmentsDir);
        }
        String curDate = LocalDateTime.now().toString();
        String fileExtension=file.getOriginalFilename();
        int dotIndex = fileExtension.lastIndexOf(".");
        // Создаем уникальное название для файла и загружаем файл
        String fileName =
                "attach_" + curDate + "_" + file.getOriginalFilename().toLowerCase().replaceAll(" ", "-");
        file.transferTo(new File(uploadDir + "/" + fileName));
        Attachment attachment = new Attachment
                (fileName,LocalDate.now(),fileExtension.substring(dotIndex),"/attachments/get/" + Year.now() + "/" + fileName);

        attachmentRepository.save(attachment);
        return attachment;
    }



    @Override
    public Attachment findAttachById(Long attachId){
        return attachmentRepository
                .findById(attachId)
                .orElseThrow(() -> new RuntimeException("Attachment not found!"));
    }



    @Override
    public Resource loadFileAsResource(String fileName)
            throws MalformedURLException {
        String absolutePath=attachmentsDir.toAbsolutePath().toString();
        Path fileStorageLocation =
                Paths.get(absolutePath).toAbsolutePath().normalize();
        Path filePath = fileStorageLocation.resolve(fileName).normalize();
        return new UrlResource(filePath.toUri());
    }

}
