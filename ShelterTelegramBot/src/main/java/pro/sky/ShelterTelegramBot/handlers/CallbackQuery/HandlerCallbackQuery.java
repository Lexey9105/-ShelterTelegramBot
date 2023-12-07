package pro.sky.ShelterTelegramBot.handlers.CallbackQuery;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.ShelterTelegramBot.listener.TelegramBotUpdatesListener;
import pro.sky.ShelterTelegramBot.model.Volunteer;
import pro.sky.ShelterTelegramBot.service.AttachmentService;
import pro.sky.ShelterTelegramBot.service.ClientStatusService;
import pro.sky.ShelterTelegramBot.service.VolunteerService;
import pro.sky.ShelterTelegramBot.utils.Send;

import java.io.IOException;

import static pro.sky.ShelterTelegramBot.constants.Constants.*;
import static pro.sky.ShelterTelegramBot.handlers.Button.*;

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
                send.sendMessage(message1.replyMarkup(MenuShelterDogButtons()));
                break;
            case CAT_SHELTER_CALLBACK:
                // Cat shelter selected
                clientStatusService.clickCat(chatId,1);
                SendMessage message2 = new SendMessage(chatId, CAT_SHELTER_HELLO);
                send.sendMessage(message2.replyMarkup(MenuShelterCatButtons()));

                break;
            default:
               handlerSubMenuButton(update);
        }
    }

    public  void handlerMenuCatButton(Update update) throws IOException {
        logger.info("method handlerCatButton is invoke");
        CallbackQuery callbackQuery = update.callbackQuery();
        long chatId = callbackQuery.message().chat().id();

        switch (callbackQuery.data()) {
            case CatShelterInfo:
                clientStatusService.clickCat(chatId, 1);
                SendMessage sendMessage = new SendMessage(chatId, CAT_SHELTER_HELLO);
                SendResponse response = telegramBot.execute(sendMessage.replyMarkup(infoShelterCatButtons()));
                break;
            case CatsPetsInfo:
                clientStatusService.clickCat(chatId, 3);
                SendMessage sendMessage2 = new SendMessage(chatId, CAT_SHELTER_HELLO);
                SendResponse response2 = telegramBot.execute(sendMessage2.replyMarkup(infoPetsCatButtons()));
                break;
            case CatsControlService:
                //SendMessage sendMessage3 = new SendMessage(chatId, DOWNLOAD_LINK + "01.docx");
                // SendResponse response3 = telegramBot.execute(sendMessage3);
                clientStatusService.clickCat(chatId, 1);
                SendMessage sendMessage3 = new SendMessage(chatId, "Тут вы будете отчитываться в скором времени");
                SendResponse response3 = telegramBot.execute(sendMessage3);
                break;
            case CALLCats_12:
                clientStatusService.clickCat(chatId,1);
                Volunteer volunteer= volunteerService.findByStatus(0,2);
                String volunteerName="@"+volunteer.getUserName()+" -"+"ваш личный помошник. Готов помочь с любой проблемой)";
                SendMessage sendMessage5 = new SendMessage(chatId, volunteerName);
                SendResponse response5 = telegramBot.execute(sendMessage5);
                break;
        }
    }


    public  void handlerMenuDogButton(Update update) throws IOException {
        logger.info("method handlerCatButton is invoke");
        CallbackQuery callbackQuery = update.callbackQuery();
        long chatId = callbackQuery.message().chat().id();

        switch (callbackQuery.data()) {
            case DogShelterInfo:
                clientStatusService.clickDog(chatId, 1);
                SendMessage sendMessage = new SendMessage(chatId, DOG_SHELTER_HELLO);
                SendResponse response = telegramBot.execute(sendMessage.replyMarkup(infoShelterDogButtons()));
                break;
            case DogsPetsInfo:
                clientStatusService.clickDog(chatId, 3);
                SendMessage sendMessage2 = new SendMessage(chatId, DOG_SHELTER_HELLO);
                SendResponse response2 = telegramBot.execute(sendMessage2.replyMarkup(infoPetsDogButtons()));
                break;
            case DogsControlService:
                //SendMessage sendMessage3 = new SendMessage(chatId, DOWNLOAD_LINK + "01.docx");
                // SendResponse response3 = telegramBot.execute(sendMessage3);
                clientStatusService.clickDog(chatId, 1);
                SendMessage sendMessage3 = new SendMessage(chatId, "Тут вы будете отчитываться в скором времени");
                SendResponse response3 = telegramBot.execute(sendMessage3);
                break;
            case CALLDogs_12:
                clientStatusService.clickDog(chatId,1);
                Volunteer volunteer= volunteerService.findByStatus(0,2);
                String volunteerName="@"+volunteer.getUserName()+" -"+"ваш личный помошник. Готов помочь с любой проблемой)";
                SendMessage sendMessage5 = new SendMessage(chatId, volunteerName);
                SendResponse response5 = telegramBot.execute(sendMessage5);
                break;
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

            case CALL_MAIN_MENU_CAT:
                handlerMenuCatButton(update);
                break;
            case CALL_MAIN_MENU_DOG:
                handlerMenuDogButton(update);
                break;
            case CALL_ID_SHELTER_INFORMATION_CAT_MENU:
                catCallbackQuery.handlerCatButton(update);
                break;
            case CALL_Pet_MENU_CAT:
                catCallbackQuery.handlerCatButton(update);
                break;
            case CALL_ID_SHELTER_INFORMATION_DOG_MENU:
                dogCallbackQuery.handlerDogButton(update);
                break;
            case CALL_Pet_MENU_DOG:
                dogCallbackQuery.handlerDogButton(update);
                break;
        }
    }
}
