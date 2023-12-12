package pro.sky.ShelterTelegramBot.service.impl;



import com.pengrad.telegrambot.model.Update;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.ShelterTelegramBot.model.*;
import pro.sky.ShelterTelegramBot.repository.AttachmentRepository;
import pro.sky.ShelterTelegramBot.service.*;
import pro.sky.ShelterTelegramBot.utils.appProperties;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static pro.sky.ShelterTelegramBot.constants.Constants.Registration_Status;
import static pro.sky.ShelterTelegramBot.constants.Constants.Report_Status;

@Service
public class AttachmentServiceImpl<CommonsMultipartFile> implements AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final ClientStatusService clientStatusService;
    private final ClientService clientService;
    private final ReportService reportService;
    private final ReportStatusService reportStatusService;
    private final ReportBreachService reportBreachService;

    private final String path="ShelterTelegramBot/attachments";
    private  final Path attachmentsDir=Paths.get(path);



    public AttachmentServiceImpl(AttachmentRepository attachmentRepository,ClientStatusService clientStatusService,ClientService clientService,ReportService reportService,ReportStatusService reportStatusService,ReportBreachService reportBreachService){
        this.attachmentRepository=attachmentRepository;
        this.clientStatusService=clientStatusService;
        this.clientService=clientService;
        this.reportService=reportService;
        this.reportStatusService=reportStatusService;
        this.reportBreachService=reportBreachService;
    }
    public Attachment update(Attachment attachment){
        return attachmentRepository.save(attachment);
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

        return attachmentRepository.save(attachment);
    }

    @Override
    public String addAttachmentRepo(File file, Update update) throws IOException {
        // Создаем директорию если ее не существует
        if(attachmentsDir.toAbsolutePath().toString().equals(null)){Files.createDirectories(attachmentsDir);}
        //String absolutePath=attachmentsDir.toAbsolutePath().toString();
        File uploadDir = new File(attachmentsDir.toAbsolutePath().toString());

        String curDate = LocalDateTime.now().toString();
        String fileExtension=file.getName();
        int dotIndex = fileExtension.lastIndexOf(".");
        Client client=clientStatusService.findClient(update.message().chat().id()).getClient();
        //добавление функции проверки и работы с reports

        // Создаем уникальное название для файла и загружаем файл
        int index=reportService.findReportsByClient(client).size()-1;
        Report report=reportService.findReportsByClient(client).get(index);

         String expectedDay=Integer.toString(report.getDayReport());
        //String dayRepo="1";
        String fileName =
               "AttachReport"+"_"+expectedDay+"_"+ client.getId().toString()+"_"+client.getName()  + "_" + file.getName().toLowerCase().replaceAll(" ", "-");
        String path1=file.getParent();
String path2=  path1+ System.getProperties().getProperty("file.separator")+fileName;

File rename=new File(path2);
file.renameTo(rename);
Attachment attachment=new Attachment(rename.getName(),LocalDate.now(),fileExtension.substring(dotIndex),rename.getAbsolutePath());
        update(attachment);
reportService.updateWithReport(attachment,report);
        return attachment.getDownloadLink();
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
@Override
    public File loadFile (String fileName) throws IOException{
    Path fileStorageLocation =
            Paths.get(attachmentsDir.toAbsolutePath().toString()).toAbsolutePath().normalize();
    Path filePath = fileStorageLocation.resolve(fileName).normalize();
   String path=filePath.toString();
    File f = new File(path);

        return f;
}



}
