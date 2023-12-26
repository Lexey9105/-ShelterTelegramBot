package pro.sky.ShelterTelegramBot.handlers;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import pro.sky.ShelterTelegramBot.handlers.CallbackQuery.CatCallbackQuery;
import pro.sky.ShelterTelegramBot.handlers.CallbackQuery.VolunteerCallback;
import pro.sky.ShelterTelegramBot.model.*;
import pro.sky.ShelterTelegramBot.service.*;
import pro.sky.ShelterTelegramBot.utils.Send;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pro.sky.ShelterTelegramBot.constants.Constants.Report_Status;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class VolunteerHandlerTest {

    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    @Mock
    private TelegramBot telegramBot;
    @Mock
    private AttachmentService attachmentService;
    @Mock
    private ClientStatusService clientStatusService;
    @Mock
    private  ClientService clientService;
    @Mock
    private  ControlService controlService;
    @Mock
    private RequestRepoService requestRepoService;
    @Mock
    private UserStatementService userStatementService;
    @Mock
    private PetService petService;
    @Mock
    private  ReportService reportService;
    @Mock
    private  ReportStatusService reportStatusService;
    @Mock
    private  ReportBreachService reportBreachService;
    @Mock
    private ShelterService shelterService;
    @Mock
    private Send send;
    @InjectMocks
    private VolunteerCallback volunteerCallback;


    @Test
    public void RequestButtonTest() throws IOException {
        Client client = new Client(1L,1L, "13333", 20, "9990001122", "fff");
        ClientStatus clientStatus=new ClientStatus(1L,"Зареган",1,1);
        Pet pet=new Pet("кошка",",fhfff","ffff");
        Request request=new Request(client.getName(),pet.getName());
        String reportName = pet.getName() + "_" + pet.getDayInFamily();
        Report report=new Report(reportName, LocalDateTime.now().plusDays(1).format(FORMATTER),1,"На проверке");
        ReportBreach reportBreach=new ReportBreach();
        ReportStatus reportStatus=new ReportStatus("fff","fff");
        Long chatId=1L;
        List<Pet> petList=new ArrayList<>();
        petList.add(pet);
        client.setClientStatus(clientStatus);
        client.setPet(petList);





        Update update = Mockito.mock(Update.class);
        CallbackQuery callbackQuery = Mockito.mock(CallbackQuery.class);
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);
        Client client1=Mockito.mock(Client.class);
        Report report1=Mockito.mock(Report.class);

        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
        when(callbackQuery.data()).thenReturn("001_1_1_Accept");
        when(clientStatusService.findClient(1L)).thenReturn(clientStatus);
        when(requestRepoService.get(1L)).thenReturn(request);
        when(clientStatusService.updateStatus(1L,Report_Status)).thenReturn(clientStatus);
        when(clientStatusService.updateStatusWithReport(1L)).thenReturn(clientStatus);
        when(petService.findPetByName(request.getPetName())).thenReturn(pet);
        when(petService.update(pet)).thenReturn(pet);
        when(clientService.createWithPets(null)).thenReturn(client);
        when(clientService.updateWithPet(null, pet)).thenReturn(client);
        when(reportService.create(report)).thenReturn(report);
        when(reportStatusService.create(reportStatus)).thenReturn(reportStatus);
        when(reportBreachService.create(reportBreach)).thenReturn(reportBreach);
        when(petService.updateWithReportStatus(pet, reportStatus)).thenReturn(pet);
        when(petService.updateWithReportBreach(pet, reportBreach)).thenReturn(pet);
        when(clientService.createWithReport(null)).thenReturn(client);
        when(clientService.updateWithReport(null, report)).thenReturn(client);
        when(petService.createWithReports(pet)).thenReturn(pet);
        when(petService.updateWithReport(pet, report)).thenReturn(pet);
        when(reportService.updateWithPet(report, pet)).thenReturn(report);
        when(reportService.updateWithClient(null, report)).thenReturn(report);
        when(requestRepoService.delete(1L)).thenReturn(request);
        when(client1.getChatId()).thenReturn(chatId);
        //when(Long.parseLong("1")).thenReturn(1L);

        volunteerCallback.RequestButton(update);


    }
}
