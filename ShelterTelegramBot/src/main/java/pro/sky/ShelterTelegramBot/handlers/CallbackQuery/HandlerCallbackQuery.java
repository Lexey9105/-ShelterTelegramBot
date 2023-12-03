package pro.sky.ShelterTelegramBot.handlers.CallbackQuery;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.ShelterTelegramBot.listener.TelegramBotUpdatesListener;
import pro.sky.ShelterTelegramBot.service.AttachmentService;
import pro.sky.ShelterTelegramBot.service.ClientStatusService;
import pro.sky.ShelterTelegramBot.service.VolunteerService;
import pro.sky.ShelterTelegramBot.utils.Send;

import java.io.IOException;

import static pro.sky.ShelterTelegramBot.constants.Constants.*;
import static pro.sky.ShelterTelegramBot.handlers.Button.infoShelterCatButtons;
import static pro.sky.ShelterTelegramBot.handlers.Button.infoShelterDogButtons;

@Service
public class HandlerCallbackQuery {

    private final TelegramBot telegramBot;
    private final AttachmentService attachmentService;
    private final VolunteerService volunteerService;
    private final ClientStatusService clientStatusService;
    private final DogCallbackQuery dogCallbackQuery;
    private final CatCallbackQuery catCallbackQuery;
    private final Send send;


    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    public HandlerCallbackQuery(TelegramBot telegramBot, ClientStatusService clientStatusService,Send send, DogCallbackQuery dogCallbackQuery, CatCallbackQuery catCallbackQuery, AttachmentService attachmentService, VolunteerService volunteerService) {
        this.telegramBot = telegramBot;
        this.attachmentService=attachmentService;
        this.volunteerService=volunteerService;
        this.dogCallbackQuery=dogCallbackQuery;
        this.catCallbackQuery=catCallbackQuery;
        this.send = send;
        this.clientStatusService=clientStatusService;

    }

    /**
     * Метод обрабатывает сообщения от клавиатуры replyMarkup.Точка входа в InfoShelterCallbackQuery.Класс обработки кнопок клавиатуры
     *
     * @param update
     * @return
     */
    public void responseButton(Update update) throws IOException {
        CallbackQuery callbackQuery = update.callbackQuery();
        long chatId = callbackQuery.message().chat().id();
        switch (callbackQuery.data()) {
            case DOG_SHELTER_CALLBACK:
                // Dog shelter selected
                clientStatusService.clickDog(chatId,1);
                SendMessage message1 = new SendMessage(chatId, DOG_SHELTER_HELLO);
                send.sendMessage(message1.replyMarkup(infoShelterDogButtons()));
                break;
            case CAT_SHELTER_CALLBACK:
                // Cat shelter selected
                clientStatusService.clickCat(chatId,1);
                SendMessage message2 = new SendMessage(chatId, CAT_SHELTER_HELLO);
                send.sendMessage(message2.replyMarkup(infoShelterCatButtons()));

                break;
            default:
               handlerSubMenuButton(update);
        }
    }

    /**
     * Точка входа в блок обработки сallBackQuery
     *
     * @param update Извлекает из сallBackQuery.data() substring с индексом клавиатуры(21-CAT,22-DOG)
     */



    public void handlerSubMenuButton(Update update) throws IOException {
        logger.info("method handlerSubMenuButton is invoke");
        CallbackQuery callbackQuery = update.callbackQuery();
        String a = callbackQuery.data().substring(0, 3);
        switch (a) {

            case CALL_ID_SHELTER_INFORMATION_CAT_MENU:
                catCallbackQuery.handlerCatButton(update);
                break;
            case CALL_ID_SHELTER_INFORMATION_DOG_MENU:

                dogCallbackQuery.handlerDogButton(update);
                break;
        }
    }
}
