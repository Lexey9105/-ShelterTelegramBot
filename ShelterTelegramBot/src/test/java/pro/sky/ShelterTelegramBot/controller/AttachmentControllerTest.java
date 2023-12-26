package pro.sky.ShelterTelegramBot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import pro.sky.ShelterTelegramBot.model.Attachment;
import pro.sky.ShelterTelegramBot.repository.AttachmentRepository;
;
import pro.sky.ShelterTelegramBot.service.*;
import pro.sky.ShelterTelegramBot.service.impl.AttachmentServiceImpl;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Year;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AttachmentController.class)
public class AttachmentControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AttachmentRepository attachmentRepository;
    @MockBean
    private ClientStatusService clientStatusService;
    @MockBean
    private  ClientService clientService;
    @MockBean
    private  ReportService reportService;
    @MockBean
    private  ReportStatusService reportStatusService;
    @MockBean
    private  ReportBreachService reportBreachService;
    @MockBean
    private PetService petService;
    @SpyBean
    private AttachmentServiceImpl attachmentService;
    @InjectMocks
    private AttachmentController attachmentController;
    private ObjectMapper mapper=new ObjectMapper();

    @Test
    public void  createTest()throws Exception {
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );

        String fileName =
               "attach_"  + "_" + file.getOriginalFilename().toLowerCase().replaceAll(" ", "-");
       String fileExtension=file.getOriginalFilename();
        int dotIndex = fileExtension.lastIndexOf(".");
        Attachment attachment=new Attachment(fileName, LocalDate.now(),fileExtension.substring(dotIndex),"/attachments" + Year.now() + "/" + fileName);



         mockMvc.perform(MockMvcRequestBuilders
                         .multipart("/attachments/add")
                         .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void  getTest()throws Exception {
        String fileName="attach__hello.txt";
        String fil1="C:\\Users\\ука\\IdeaProjects\\ShelterTelegramBot\\ShelterTelegramBot\\ShelterTelegramBot\\attachments\\";
        String url=fil1+fileName;
        Path file1= Paths.get(url);
        Resource resource =new UrlResource(file1.toUri());
        String contentType="image/jpeg";


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/attachments/get/" + fileName)
                        .contentType(MediaType.parseMediaType(contentType))
                        .accept(HttpHeaders.CONTENT_DISPOSITION))
                .andExpect(status().isOk());



    }
}
