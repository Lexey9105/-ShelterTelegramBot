package pro.sky.ShelterTelegramBot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;


import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.ShelterTelegramBot.constants.Constants;

import java.util.List;

/**
 * Сервис отвечает за обработку и отправку сообщений в Telegram бота
 */
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final TelegramBot telegramBot;
    private final Constants constants;

    private static final String START_COMMAND = "/start";
    private static final String HELP_COMMAND = "/help";

    public TelegramBotUpdatesListener (TelegramBot telegramBot,Constants constants){
        this.telegramBot=telegramBot;
        this.constants=constants;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    /**
     *Получает спмсок обновлений от бота, отфильтровывает только текстовые сообщения
     * и обрабатывает методом {@code processUpdate(Update update)}
     * @param updates - сообщения
     * @return
     */
    @Override
    public int process(List<Update> updates) {
        try {
            updates.stream()
                    .filter(update -> update.message() != null)
                    .forEach(this::processUpdate);
        } catch (Exception e) {
            logger.error("Error during processing telegram update", e);
        }

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    /**
     * Получает информацию из обновления - идентификатор чата и текст сообщения
     * @param update
     */
    private void processUpdate(Update update) {
        logger.info("Processing update: {}", update);

        Long chatId = update.message().chat().id();
        String text = update.message().text();

        String returnText = handleCommand(text);
        sendMessage(chatId, returnText);
    }

    /**
     * Отправляет полученно сообщение обратно в чат
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
     * @param command
     * @return
     */
    private String handleCommand(String command) {
        return switch (command) {
            case START_COMMAND -> constants.SAY_HELLO;
            case HELP_COMMAND -> constants.ASK_HELP;
            default -> "Передаю вопрос волонтерам";
        };
    }
}
