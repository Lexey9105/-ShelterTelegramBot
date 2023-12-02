package pro.sky.ShelterTelegramBot.utils;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BotTestUtils {

    public static Update getUpdate(Long chatId, String command) {
        Update update = mock(Update.class);
        //when(update.message().text()).thenReturn(command);
        Chat chat = mock(Chat.class);
        Message message = mock(Message.class);
        when(chat.id()).thenReturn(chatId);
        when(message.text()).thenReturn(command);
        when(message.chat()).thenReturn(chat);
        when(update.message()).thenReturn(message);
        return update;
    }

    public static Update getCallbackQuery( String callback,String command) {
        Long chatId=2455L;
        Update update = mock(Update.class);
        Chat chat = mock(Chat.class);
        Message message = mock(Message.class);
        CallbackQuery callbackQuery = mock(CallbackQuery.class);
        when(chat.id()).thenReturn(chatId);
        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.message()).thenReturn(message);
        when(callbackQuery.data()).thenReturn(callback);
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(callbackQuery.message().chat().id()).thenReturn(chatId);
        when(message.text()).thenReturn(command);


        ;
        return update;
    }

    public static SendMessage getSendMessage(TelegramBot bot, UpdatesListener listener, Update update,Update update1) {
       SendMessage sendMessage=new SendMessage(update1.message().chat().id(),update1.message().text());
         //var requestCaptor = ArgumentCaptor.forClass(SendMessage.class);
        when(bot.execute(sendMessage)).thenReturn(null);
        //when(bot.execute(requestCaptor.capture())).thenReturn(null);
        listener.process(List.of(update));
        return sendMessage;
    }
}
