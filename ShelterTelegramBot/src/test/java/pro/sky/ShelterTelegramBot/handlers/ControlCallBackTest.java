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
import pro.sky.ShelterTelegramBot.handlers.CallbackQuery.ControlCallbackQuery;
import pro.sky.ShelterTelegramBot.model.ClientStatus;
import pro.sky.ShelterTelegramBot.model.UserStatement;
import pro.sky.ShelterTelegramBot.service.ClientStatusService;
import pro.sky.ShelterTelegramBot.service.ControlService;
import pro.sky.ShelterTelegramBot.service.UserStatementService;

import java.io.IOException;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ControlCallBackTest {

    @Mock
    private ControlService controlService;
    @Mock
    private UserStatementService userStatementService;
    @Mock
    private ClientStatusService clientStatusService;
    @Mock
    private TelegramBot telegramBot;

    @InjectMocks
    private ControlCallbackQuery controlCallbackQuery;
    @Test
    public void ControlCallbackTest() throws IOException {
        Long chatId = 1L;
        Update update = Mockito.mock(Update.class);
        CallbackQuery callbackQuery = Mockito.mock(CallbackQuery.class);
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);
        ClientStatus clientStatus=Mockito.mock(ClientStatus.class);
        UserStatement userStatement2=new UserStatement();
        clientStatus.setUserStatement(userStatement2);
        userStatement2.setStatement("&");


        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
        when(callbackQuery.data()).thenReturn("1984_ReportControl");
        when(clientStatusService.findClient(chatId)).thenReturn(clientStatus);
        when(userStatementService.update(userStatement2)).thenReturn(userStatement2);
        when(clientStatus.getUserStatement()).thenReturn(userStatement2);

        controlCallbackQuery.ControlCallBack(update);



    }
}
