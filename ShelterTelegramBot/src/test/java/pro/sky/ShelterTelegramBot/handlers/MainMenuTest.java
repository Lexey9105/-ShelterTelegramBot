package pro.sky.ShelterTelegramBot.handlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import pro.sky.ShelterTelegramBot.constants.ShelterType;
import pro.sky.ShelterTelegramBot.model.*;
import pro.sky.ShelterTelegramBot.service.*;
import pro.sky.ShelterTelegramBot.utils.Send;

import java.io.IOException;

import static org.mockito.Mockito.when;
import static pro.sky.ShelterTelegramBot.constants.Constants.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class MainMenuTest {
    @Mock
    private ClientStatusService clientStatusService;
    @Mock
    private VolunteerService volunteerService;
    @Mock
    private ShelterService shelterService;
    @Mock
    private UserStatementService statementService;
    @Mock
    private Send send;
    @Mock
    private PetService petService;
    @Mock
    private TelegramBot telegramBot;
    @InjectMocks
    private MainMenuHandler mainMenuHandler;
@Test
    public void processUpdateFirstStartTest() throws IOException {
        Long chatId = 1L;
        Shelter shelter1 = new Shelter(Cats_Shelter, ShelterType.CAT_SHELTER, "адрес", "часы работы", "контакт для связи");
        Shelter shelter2 = new Shelter(Dogs_Shelter, ShelterType.DOG_SHELTER, "адрес", "часы работы", "контакт для связи");
        ClientStatus clientStatus=Mockito.mock(ClientStatus.class);
        UserStatement userStatement=Mockito.mock(UserStatement.class);
        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);


        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
        when(message.text()).thenReturn("/start");
        when(clientStatusService.findClient(chatId)).thenReturn(null);
        when(clientStatusService.create(chatId)).thenReturn(clientStatus);
        when(statementService.create()).thenReturn(userStatement);
        when(clientStatusService.updateWithUserStatement(clientStatus,userStatement)).thenReturn(clientStatus);
        when(shelterService.create(shelter1)).thenReturn(shelter1);
        when(shelterService.create(shelter2)).thenReturn(shelter2);

        mainMenuHandler.processUpdate(update);
    }

    @Test
    public void processUpdateSaveVolunteerTest() throws IOException {
        Long chatId = 1L;
        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);
        User user=Mockito.mock(User.class);
        ClientStatus clientStatus=new ClientStatus(1L,"гость",0,0);
        Volunteer volunteer=new Volunteer("hhhh",0);
        Volunteer volunteer2=new Volunteer("zero",0);
//        String userName = update.message().from().username();

        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
        when(message.from()).thenReturn(user);
        when(message.from().username()).thenReturn("userName");
        when(volunteerService.findByUserName("userName")).thenReturn(volunteer2);
        when(clientStatusService.findClient(chatId)).thenReturn(clientStatus);
        when(volunteerService.create(volunteer)).thenReturn(volunteer);
        when(message.text()).thenReturn("$_volunteer");

        mainMenuHandler.processUpdate(update);
    }

    @Test
    public void processUpdateSavePetTest() throws IOException {
        Long chatId = 1L;
        Shelter shelter1 = new Shelter(Cats_Shelter, ShelterType.CAT_SHELTER, "адрес", "часы работы", "контакт для связи");
        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);
        Pet pet=Mockito.mock(Pet.class);
        ClientStatus clientStatus=new ClientStatus(1L,"гость",0,0);
        Volunteer volunteer=new Volunteer("hhhh",0);

        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
        when(message.text()).thenReturn("!_кошка_ffff_fffft");
        when(volunteerService.findByUserName(message.text())).thenReturn(volunteer);
        when(clientStatusService.findClient(chatId)).thenReturn(clientStatus);
        when(petService.create(pet)).thenReturn(pet);
        when(shelterService.findByName(Cats_Shelter)).thenReturn(shelter1);
        when(shelterService.createWithPets(shelter1)).thenReturn(shelter1);
        when(petService.updateWithShelter(shelter1,pet)).thenReturn(pet);


        mainMenuHandler.processUpdate(update);
    }
}
