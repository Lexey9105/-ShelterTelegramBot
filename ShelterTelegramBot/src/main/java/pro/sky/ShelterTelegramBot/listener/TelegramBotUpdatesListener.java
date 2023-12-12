package pro.sky.ShelterTelegramBot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;


import jakarta.annotation.PostConstruct;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.ShelterTelegramBot.handlers.CallbackQuery.HandlerCallbackQuery;
import pro.sky.ShelterTelegramBot.handlers.MainMenuHandler;
import pro.sky.ShelterTelegramBot.model.Client;
import pro.sky.ShelterTelegramBot.model.ClientStatus;
import pro.sky.ShelterTelegramBot.model.Volunteer;
import pro.sky.ShelterTelegramBot.service.ClientService;
import pro.sky.ShelterTelegramBot.service.ClientStatusService;
import pro.sky.ShelterTelegramBot.service.VolunteerService;
import pro.sky.ShelterTelegramBot.utils.TelegramFileService;

import static pro.sky.ShelterTelegramBot.constants.Constants.*;
import static pro.sky.ShelterTelegramBot.handlers.Button.*;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Сервис отвечает за обработку и отправку сообщений в Telegram бота
 */
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private static final Logger LOG = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final TelegramBot telegramBot;
    private final MainMenuHandler mainMenuHandler;
    private final HandlerCallbackQuery handlerCallbackQuery;
    private final TelegramFileService telegramFileService;



    private static final String START_COMMAND = "/start";
    private static final String HELP_COMMAND = "/help";
    Pattern pattern = Pattern.compile("\\d{3}-\\d{3}-\\d{2}-\\d{2}");

    public TelegramBotUpdatesListener(TelegramBot telegramBot,MainMenuHandler mainMenuHandler, HandlerCallbackQuery handlerCallbackQuery,TelegramFileService telegramFileService) {
        this.telegramBot = telegramBot;
        this.mainMenuHandler=mainMenuHandler;
        this.handlerCallbackQuery = handlerCallbackQuery;
        this.telegramFileService=telegramFileService;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    /**
     * Получает список обновлений от бота, разделяет update по блокам  processUpdate(обработка сообщений от пользователя)
     * и  responseButton(update) обработка клавиатуры
     *
     * @param updates - сообщения
     * @return
     */
    @Override
    public int process(List<Update> updates) {
        try {
            updates.forEach(update -> {
                if (update.message() != null) {
                    try {
                        mainMenuHandler.processUpdate(update);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    try {
                        handlerCallbackQuery.responseButton(update);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            });

        } catch (Exception e) {
            logger.error("Error during processing telegram update", e);
        }

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
    }





