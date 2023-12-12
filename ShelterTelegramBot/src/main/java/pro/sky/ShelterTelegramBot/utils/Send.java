package pro.sky.ShelterTelegramBot.utils;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.ShelterTelegramBot.listener.TelegramBotUpdatesListener;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class Send {

    private final TelegramBot telegramBot;

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    public Send(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    /**
     * Отправляет полученное сообщение обратно в чат
     * <p>
     * //     * @param chatId
     *
     * @param message
     */
    public void sendMessage(SendMessage message) {
        SendResponse response = telegramBot.execute(message);
        if (!response.isOk()) {
            logger.error("Error during sending message: {}", response.description());
        }
    }

    public void sendPhoto(String s, Update update) {
        Path path= Paths.get(s);
        SendPhoto sendPhoto=new SendPhoto(update.message().chat().id(),path.toFile());
        SendResponse response=telegramBot.execute(sendPhoto);
        if (!response.isOk()) {
            logger.error("Error during sending message: {}", response.description());
        }
    }


}
