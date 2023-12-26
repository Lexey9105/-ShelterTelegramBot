package pro.sky.ShelterTelegramBot.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import pro.sky.ShelterTelegramBot.model.*;
import pro.sky.ShelterTelegramBot.service.impl.ControlServiceImpl;
import pro.sky.ShelterTelegramBot.utils.Send;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.mockito.Mockito.when;
import static pro.sky.ShelterTelegramBot.constants.Constants.Report_Status;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ControlServiceTest {

    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    @Mock
    private  ClientService clientService;
    @Mock
    private  ClientStatusService clientStatusService;
    @Mock
    private  ReportService reportService;
    @Mock
    private  ReportStatusService reportStatusService;
    @Mock
    private  ReportBreachService reportBreachService;
    @Mock
    private  PetService petService;
    @Mock
    private  Send send;
    @InjectMocks
    private ControlServiceImpl controlService;
    @Test
    public void createReportTest(){
        Long chatId=1L;
        Report report2=new Report("Pet_2", LocalDate.now().plusDays(1).format(FORMATTER),2,"На проверке");
        Report report=Mockito.mock(Report.class);
        String name="Pet_1";
        String localDate=LocalDate.now().format(FORMATTER);
        ReportBreach reportBreach=Mockito.mock(ReportBreach.class);
        ReportStatus reportStatus=Mockito.mock(ReportStatus.class);
        Client client=Mockito.mock(Client.class);
        Pet pet=Mockito.mock(Pet.class);
        ClientStatus clientStatus=Mockito.mock(ClientStatus.class);


        when((report.getClient())).thenReturn(client);
        when((report.getClient().getChatId())).thenReturn(chatId);
        when(report.getName()).thenReturn(name);
        when(report.getLocalDateTime()).thenReturn(localDate);
        when(clientStatusService.updateStatusWithReport(report.getClient().getChatId())).thenReturn(clientStatus);
        when(report.getPet()).thenReturn(pet);
        when(petService.update(pet)).thenReturn(pet);
        when(clientService.createWithReport(client)).thenReturn(client);
        when(reportService.create(report2)).thenReturn(report2);
        when(reportService.updateWithClient(client,report2)).thenReturn(report2);
        when(petService.createWithReports(pet)).thenReturn(pet);
        when(petService.updateWithReport(pet,report2)).thenReturn(pet);
        when(reportService.updateWithPet(report2,pet)).thenReturn(report);

        controlService.createReport(report);
    }
    @Test
    public void handTest(){
        Report report=Mockito.mock(Report.class);

        when(reportService.update(report)).thenReturn(report);
    }

@Test
    public void checkTest(){
    Long chatId=1L;
        Report report=Mockito.mock(Report.class);
        Client client=Mockito.mock(Client.class);
        Attachment attachment=Mockito.mock(Attachment.class);
        String text="text";

    when((report.getClient())).thenReturn(client);
    when((report.getClient().getChatId())).thenReturn(chatId);
    when(report.getAttachment()).thenReturn(attachment);
    when(report.getTextReport()).thenReturn(text);
    when(reportService.update(report)).thenReturn(report);

    controlService.check(report);

}

@Test
    public void acceptTest(){
    Long chatId=1L;
    Report report2=new Report("Pet_2", LocalDate.now().plusDays(1).format(FORMATTER),2,"На проверке");
    Report report=Mockito.mock(Report.class);
    String name="Pet_1";
    String localDate=LocalDate.now().format(FORMATTER);
    ReportBreach reportBreach=Mockito.mock(ReportBreach.class);
   // reportBreach.setRepoAttachDay1(1);
   // reportBreach.setRepoAttachDay2(1);
    ReportStatus reportStatus=Mockito.mock(ReportStatus.class);
    Client client=Mockito.mock(Client.class);
    Pet pet=Mockito.mock(Pet.class);
    ClientStatus clientStatus=Mockito.mock(ClientStatus.class);
    int a=1;
    int b=1;

    when((report.getClient())).thenReturn(client);
    when(report.getPet()).thenReturn(pet);
    when(pet.getReportBreach()).thenReturn(reportBreach);
    when(pet.getReportStatus()).thenReturn(reportStatus);
    when(reportBreach.getRepoAttachDay1()).thenReturn(a);
    when(reportBreach.getRepoAttachDay2()).thenReturn(b);
    when((report.getClient().getChatId())).thenReturn(chatId);
    when(report.getName()).thenReturn(name);
    when(report.getLocalDateTime()).thenReturn(localDate);
    when(clientStatusService.updateStatusWithReport(report.getClient().getChatId())).thenReturn(clientStatus);
    when(reportBreachService.update(reportBreach)).thenReturn(reportBreach);
    when(reportStatusService.update(reportStatus)).thenReturn(reportStatus);
    when(reportService.update(report)).thenReturn(report);
    when(petService.update(pet)).thenReturn(pet);
    when(clientService.createWithReport(client)).thenReturn(client);
    when(reportService.create(report2)).thenReturn(report2);
    when(reportService.updateWithClient(client,report2)).thenReturn(report2);
    when(petService.createWithReports(pet)).thenReturn(pet);
    when(petService.updateWithReport(pet,report2)).thenReturn(pet);
    when(reportService.updateWithPet(report2,pet)).thenReturn(report);

    controlService.accept(client,report);

}

public void refusal(){
    Long chatId=1L;
    Report report2=new Report("Pet_2", LocalDate.now().plusDays(1).format(FORMATTER),2,"На проверке");
    Report report=Mockito.mock(Report.class);
    String name="Pet_1";
    String localDate=LocalDate.now().format(FORMATTER);
    ReportBreach reportBreach=Mockito.mock(ReportBreach.class);
    // reportBreach.setRepoAttachDay1(1);
    // reportBreach.setRepoAttachDay2(1);
    ReportStatus reportStatus=Mockito.mock(ReportStatus.class);
    Client client=Mockito.mock(Client.class);
    Pet pet=Mockito.mock(Pet.class);
    ClientStatus clientStatus=Mockito.mock(ClientStatus.class);
    int a=1;
    int b=1;

    when((report.getClient())).thenReturn(client);
    when(report.getPet()).thenReturn(pet);
    when(pet.getReportBreach()).thenReturn(reportBreach);
    when(pet.getReportStatus()).thenReturn(reportStatus);
    when(reportBreach.getRepoAttachDay1()).thenReturn(a);
    when(reportBreach.getRepoAttachDay2()).thenReturn(b);
    when((report.getClient().getChatId())).thenReturn(chatId);
    when(report.getName()).thenReturn(name);
    when(report.getLocalDateTime()).thenReturn(localDate);
    when(clientStatusService.updateStatusWithReport(report.getClient().getChatId())).thenReturn(clientStatus);
    when(reportBreachService.update(reportBreach)).thenReturn(reportBreach);
    when(reportStatusService.update(reportStatus)).thenReturn(reportStatus);
    when(reportService.update(report)).thenReturn(report);
    when(petService.update(pet)).thenReturn(pet);
    when(clientService.createWithReport(client)).thenReturn(client);
    when(reportService.create(report2)).thenReturn(report2);
    when(reportService.updateWithClient(client,report2)).thenReturn(report2);
    when(petService.createWithReports(pet)).thenReturn(pet);
    when(petService.updateWithReport(pet,report2)).thenReturn(pet);
    when(reportService.updateWithPet(report2,pet)).thenReturn(report);
}

}
