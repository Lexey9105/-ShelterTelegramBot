package pro.sky.ShelterTelegramBot.service;



import org.apache.tomcat.util.http.fileupload.FileUtils;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.rules.TemporaryFolder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.ShelterTelegramBot.model.Attachment;
import pro.sky.ShelterTelegramBot.repository.AttachmentRepository;
import pro.sky.ShelterTelegramBot.service.impl.AttachmentServiceImpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Properties;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class AttachmentServiceImplTest {


    @Mock
    private AttachmentRepository attachmentRepository;

    @InjectMocks
    private AttachmentServiceImpl out;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();




    @Test
    public void addTest() throws IOException {
        MockMultipartFile file1
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );

        String fileName =
                "attach_"  + "_" + file1.getOriginalFilename().toLowerCase().replaceAll(" ", "-");
        String fileExtension=file1.getOriginalFilename();
        int dotIndex = fileExtension.lastIndexOf(".");
        Attachment attachment=new Attachment(fileName,LocalDate.now(),fileExtension.substring(dotIndex),"/attachments" + Year.now() + "/" + fileName);

        when(attachmentRepository.save(attachment)).thenReturn(attachment);
        //when(out.addAttachment(file1)).thenReturn(attachment);
        assertEquals(attachment, out.addAttachment((MultipartFile) file1));
    }

    @Test
    public void loadFileAsResourceTest() throws IOException {
        String fileName="attach__hello.txt";
String file1="C:\\Users\\ука\\IdeaProjects\\ShelterTelegramBot\\ShelterTelegramBot\\ShelterTelegramBot\\attachments\\attach__hello.txt";
        Path file= Paths.get(file1);
        Resource resource =new UrlResource(file.toUri());
        assertEquals(resource, out.loadFileAsResource(fileName));
    }







}
