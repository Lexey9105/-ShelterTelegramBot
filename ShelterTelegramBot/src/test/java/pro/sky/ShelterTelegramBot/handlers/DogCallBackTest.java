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
import pro.sky.ShelterTelegramBot.handlers.CallbackQuery.DogCallbackQuery;
import pro.sky.ShelterTelegramBot.model.Client;
import pro.sky.ShelterTelegramBot.model.ClientStatus;
import pro.sky.ShelterTelegramBot.model.Pet;
import pro.sky.ShelterTelegramBot.model.Request;
import pro.sky.ShelterTelegramBot.service.ClientStatusService;
import pro.sky.ShelterTelegramBot.service.PetService;
import pro.sky.ShelterTelegramBot.service.RequestRepoService;

import java.io.IOException;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class DogCallBackTest {

    @Mock
    private PetService petService;
    @Mock
    private RequestRepoService requestRepoService;
    @Mock
    private ClientStatusService clientStatusService;
    @Mock
    private TelegramBot telegramBot;
    @InjectMocks
    private DogCallbackQuery dogCallBackQuery;
@Test
    public void createRequest() throws IOException {
        Pet pet= Mockito.mock(Pet.class);
        Request request=Mockito.mock(Request.class);
        ClientStatus clientStatus=Mockito.mock(ClientStatus.class);
        Client client=Mockito.mock(Client.class);
        Update update=Mockito.mock(Update.class);
        CallbackQuery callbackQuery=Mockito.mock(CallbackQuery.class);
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);

        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
        when(callbackQuery.data()).thenReturn("333,приютить,1,1");
        when(petService.get(1L)).thenReturn(pet);
        when(requestRepoService.findRequestByPetName(pet.getName())).thenReturn(null);
        when(requestRepoService.create(request)).thenReturn(request);
        when(clientStatusService.findClient(1L)).thenReturn(clientStatus);
        when(clientStatus.getClient()).thenReturn(client);

        dogCallBackQuery.getPetsCatButton(update);

    }
}
