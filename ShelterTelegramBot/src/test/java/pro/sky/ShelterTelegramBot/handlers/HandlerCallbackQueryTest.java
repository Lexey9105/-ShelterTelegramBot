package pro.sky.ShelterTelegramBot.handlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import jakarta.persistence.Id;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.ShelterTelegramBot.handlers.CallbackQuery.CatCallbackQuery;
import pro.sky.ShelterTelegramBot.handlers.CallbackQuery.DogCallbackQuery;
import pro.sky.ShelterTelegramBot.handlers.CallbackQuery.HandlerCallbackQuery;
import pro.sky.ShelterTelegramBot.service.AttachmentService;
import pro.sky.ShelterTelegramBot.service.ClientStatusService;
import pro.sky.ShelterTelegramBot.service.VolunteerService;
import pro.sky.ShelterTelegramBot.utils.Send;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.Date;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HandlerCallbackQueryTest {
    @Mock
    private TelegramBot telegramBot;
    @Mock
    private AttachmentService attachmentService;
    @Mock
    private VolunteerService volunteerService;
    @Mock
    private ClientStatusService clientStatusService;
    @Mock
    private DogCallbackQuery dogCallbackQuery;
    @Mock
    private CatCallbackQuery catCallbackQuery;
    @Mock
    private Send send;

    @InjectMocks
    private HandlerCallbackQuery handlerCallbackQuery;

    @Test
    public void handlerMenuCatButtonTest() throws IOException {
        Update update = Mockito.mock(Update.class);
        CallbackQuery callbackQuery = Mockito.mock(CallbackQuery.class);
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);

        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
        when(callbackQuery.data()).thenReturn("011_CatShelterInfo");

        handlerCallbackQuery.handlerMenuCatButton(update);

        verify(clientStatusService).clickCat(1L, 1);



    }



}
