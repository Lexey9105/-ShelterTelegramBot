package pro.sky.ShelterTelegramBot.handlers.CallbackQuery;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.SendResponse;
import org.apache.tomcat.util.net.NioEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.ShelterTelegramBot.listener.TelegramBotUpdatesListener;
import pro.sky.ShelterTelegramBot.model.Volunteer;
import pro.sky.ShelterTelegramBot.service.AttachmentService;
import pro.sky.ShelterTelegramBot.service.VolunteerService;

import java.io.IOException;
import java.net.MalformedURLException;

import static pro.sky.ShelterTelegramBot.constants.Constants.*;
import static pro.sky.ShelterTelegramBot.handlers.Button.infoShelterCatButtons;
import static pro.sky.ShelterTelegramBot.handlers.Button.infoShelterDogButtons;

@Service
public class InfoShelterCallbackQuery {

    private final TelegramBot telegramBot;
    private final AttachmentService attachmentService;
    private final VolunteerService volunteerService;

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    public InfoShelterCallbackQuery(TelegramBot telegramBot, AttachmentService attachmentService,VolunteerService volunteerService) {
        this.telegramBot = telegramBot;
        this.attachmentService=attachmentService;
        this.volunteerService=volunteerService;
    }

    /**
     * обработка сallBackQuery для клавиатуры CAT
     *
     * @param update
     */
    public  void handlerCatButton(Update update) throws IOException {
        logger.info("method handlerCatButton is invoke");
        CallbackQuery callbackQuery = update.callbackQuery();
        long chatId = callbackQuery.message().chat().id();
        String shelterAddress=attachmentService.loadFileAsResource("attach__00.jpg").getURL().toString();

        switch (callbackQuery.data()) {
            case WORK_SCHEDULE:
                //SendMessage sendMessage = new SendMessage(chatId, shelterCat.getAddress());
                // SendResponse response = telegramBot.execute(sendMessage);
                SendPhoto sendPhoto =new SendPhoto(chatId,attachmentService.loadFile("attach__00.jpg"));
                SendResponse response = telegramBot.execute(sendPhoto);
                break;
            case REGISTRATION_CARCat:
                SendMessage sendMessage2 = new SendMessage(chatId, shelterCat.getContactDetails());
                SendResponse response2 = telegramBot.execute(sendMessage2);
                break;
            case SAFETYCat:
                //SendMessage sendMessage3 = new SendMessage(chatId, DOWNLOAD_LINK + "01.docx");
                // SendResponse response3 = telegramBot.execute(sendMessage3);
                SendDocument sendDoc =new SendDocument(chatId,attachmentService.loadFile("attach__01.docx"));
                SendResponse response3 = telegramBot.execute(sendDoc);
                break;
            case CREATECat:
                SendMessage sendMessage4 = new SendMessage(chatId, CREATE);
                SendResponse response4 = telegramBot.execute(sendMessage4);
                break;
            case CALLCat:
                Volunteer volunteer= volunteerService.findByStatus(0,2);
                String volunteerName="@"+volunteer.getUserName()+" -"+"ваш личный помошник. Готов помочь с любой проблемой)";
                SendMessage sendMessage5 = new SendMessage(chatId, volunteerName);
                SendResponse response5 = telegramBot.execute(sendMessage5);
                break;
        }
    }

    /**
     * обработка сallBackQuery для клавиатуры DOG
     *
     * @param update
     */
    private void handlerDogButton(Update update) throws IOException {
        logger.info("method handlerDogButton is invoke");
        CallbackQuery callbackQuery = update.callbackQuery();
        long chatId = callbackQuery.message().chat().id();
        String shelterAddress=attachmentService.loadFileAsResource("attach__00.jpg").getURL().toString();
        switch (callbackQuery.data()) {
            case WORK_SCHEDULEDog:
                SendPhoto sendPhoto =new SendPhoto(chatId, attachmentService.loadFile("attach__00.jpg"));
                SendResponse response = telegramBot.execute(sendPhoto);
                break;
            case REGISTRATION_CARDog:
                SendMessage sendMessage2 = new SendMessage(chatId, shelterDog.getContactDetails());
                SendResponse response2 = telegramBot.execute(sendMessage2);
                break;
            case SAFETYDog:
                SendDocument sendDoc =new SendDocument(chatId,attachmentService.loadFile("attach__01.docx"));
                SendResponse response3 = telegramBot.execute(sendDoc);
                break;
            case CREATEDog:
                SendMessage sendMessage4 = new SendMessage(chatId, CREATE);
                SendResponse response4 = telegramBot.execute(sendMessage4);
                break;
            case CALLDog:
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

            case CALL_ID_SHELTER_INFORMATION_CAT_MENU:
                handlerCatButton(update);
                break;
            case CALL_ID_SHELTER_INFORMATION_DOG_MENU:

                handlerDogButton(update);
                break;
        }
    }
}
