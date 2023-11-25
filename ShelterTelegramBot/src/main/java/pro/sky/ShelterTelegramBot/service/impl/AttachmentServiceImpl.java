package pro.sky.ShelterTelegramBot.service.impl;



import org.springframework.beans.factory.annotation.Value;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.ShelterTelegramBot.model.Attachment;
import pro.sky.ShelterTelegramBot.repository.AttachmentRepository;
import pro.sky.ShelterTelegramBot.service.AttachmentService;
import pro.sky.ShelterTelegramBot.utils.appProperties;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.Properties;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;


    private final String path="ShelterTelegramBot/attachments";
    private  final Path attachmentsDir=Paths.get(path);



    public AttachmentServiceImpl(AttachmentRepository attachmentRepository){
        this.attachmentRepository=attachmentRepository;
    }

    @Override
    public Attachment addAttachment(MultipartFile file) throws IOException {
// Создаем директорию если ее не существует
        if(attachmentsDir.toAbsolutePath().toString().equals(null)){Files.createDirectories(attachmentsDir);}
        //String absolutePath=attachmentsDir.toAbsolutePath().toString();
        File uploadDir = new File(attachmentsDir.toAbsolutePath().toString());
        String curDate = LocalDateTime.now().toString();
        String fileExtension=file.getOriginalFilename();
        int dotIndex = fileExtension.lastIndexOf(".");
        // Создаем уникальное название для файла и загружаем файл
        String fileName =
                "attach_"  + "_" + file.getOriginalFilename().toLowerCase().replaceAll(" ", "-");
        file.transferTo(new File(uploadDir + "/" + fileName));
        Attachment attachment = new Attachment
                (fileName,LocalDate.now(),fileExtension.substring(dotIndex),"/attachments" + Year.now() + "/" + fileName);

        attachmentRepository.save(attachment);
        return attachment;
    }






    @Override
    public Resource loadFileAsResource(String fileName)
            throws MalformedURLException {
        //String absolutePath=attachmentsDir.toAbsolutePath().toString();
        Path fileStorageLocation =
                Paths.get(attachmentsDir.toAbsolutePath().toString()).toAbsolutePath().normalize();
        Path filePath = fileStorageLocation.resolve(fileName).normalize();
        return new UrlResource(filePath.toUri());
    }

}
