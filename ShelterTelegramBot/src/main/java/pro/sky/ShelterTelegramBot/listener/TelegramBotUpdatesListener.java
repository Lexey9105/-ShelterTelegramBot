package pro.sky.ShelterTelegramBot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;


import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import static pro.sky.ShelterTelegramBot.constants.Constants.*;

import java.util.List;

/**
 * Сервис отвечает за обработку и отправку сообщений в Telegram бота
 */
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final TelegramBot telegramBot;

    private static final String START_COMMAND = "/start";
    private static final String HELP_COMMAND = "/help";

    public TelegramBotUpdatesListener(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    /**
     * Получает спмсок обновлений от бота, отфильтровывает только текстовые сообщения
     * и обрабатывает методом {@code processUpdate(Update update)}
     *
     * @param updates - сообщения
     * @return
     */
    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {

            logger.info("Processing update: {}", update);

            if (update.message() != null) {
                processUpdate(update);
            } else {
                responseButton(update);
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    /**
     * Получает информацию из обновления - идентификатор чата и текст сообщения
     *
     * @param update
     */
    private void processUpdate(Update update) {

        Long chatId = update.message().chat().id();
        String text = update.message().text();

        String returnText = handleCommand(text);
        sendMessage(chatId, returnText);
    }

    /**
     * Отправляет полученное сообщение обратно в чат
     *
     * @param chatId
     * @param message
     */
    private void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        SendResponse response = telegramBot.execute(sendMessage);
        if (!response.isOk()) {
            logger.error("Error during sending message: {}", response.description());
        }
    }

    /**
     * Метод обрабатывает команду и возвращает соответствующий текст
     *
     * @param command
     * @return
     */
    private String handleCommand(String command) {
        return switch (command) {
            case START_COMMAND -> SAY_HELLO;
            case HELP_COMMAND -> ASK_HELP;
            default -> "Передаю вопрос волонтерам";
        };
    }

    private void responseButton(Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        if (callbackQuery != null) {
            long chatId = callbackQuery.message().chat().id();
            switch (callbackQuery.data()) {
                case DOG_SHELTER_CALLBACK:
                    // Dog shelter selected
                    sendMessage(chatId, DOG_SHELTER_CALLBACK);
                    break;
                case CAT_SHELTER_CALLBACK:
                    // Cat shelter selected
                    sendMessage(chatId, CAT_SHELTER_CALLBACK);
                    break;
            }
        }
    }

//    private void processDogShelterClick(long chatId) {
//        sendStage0Message(chatId, DOG_SHELTER_WELCOME_MSG_TEXT);
//    }
//    private void processCatShelterClick(long chatId) {
//        sendStage0Message(chatId, CAT_SHELTER_WELCOME_MSG_TEXT);
//    }
//    private void sendStage0Message(long chatId, String messageText) {
//        SendMessage message = new SendMessage(chatId, messageText);
//        sendMessage(chatId, String.valueOf(message));
//    }
}
