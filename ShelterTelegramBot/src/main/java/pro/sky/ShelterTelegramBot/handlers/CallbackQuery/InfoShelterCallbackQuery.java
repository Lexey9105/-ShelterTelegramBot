package pro.sky.ShelterTelegramBot.handlers.CallbackQuery;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.ShelterTelegramBot.listener.TelegramBotUpdatesListener;

import static pro.sky.ShelterTelegramBot.constants.Constants.*;

@Service
public class InfoShelterCallbackQuery {

    private final TelegramBot telegramBot;
    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    public InfoShelterCallbackQuery(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    /**
     * обработка сallBackQuery для клавиатуры CAT
     *
     * @param update
     */
    private void handlerCatButton(Update update) {
        logger.info("method handlerCatButton is invoke");
        CallbackQuery callbackQuery = update.callbackQuery();
        long chatId = callbackQuery.message().chat().id();
        switch (callbackQuery.data()) {
            case "021_WORK_SCHEDULE":
                SendMessage sendMessage = new SendMessage(chatId, shelterCat.getAddress());
                SendResponse response = telegramBot.execute(sendMessage);
                break;
            case "021_REGISTRATION_CAR":
                SendMessage sendMessage2 = new SendMessage(chatId, shelterCat.getContactDetails());
                SendResponse response2 = telegramBot.execute(sendMessage2);
                break;
            case "021_SAFETY":
                SendMessage sendMessage3 = new SendMessage(chatId, DOWNLOAD_LINK + "01.docx");
                SendResponse response3 = telegramBot.execute(sendMessage3);
                break;
            case "021_CREATE":
                SendMessage sendMessage4 = new SendMessage(chatId, CREATE);
                SendResponse response4 = telegramBot.execute(sendMessage4);
                break;
            case "021_CALL":
                SendMessage sendMessage5 = new SendMessage(chatId, CALLBACK);
                SendResponse response5 = telegramBot.execute(sendMessage5);
                break;
        }
    }

    /**
     * обработка сallBackQuery для клавиатуры DOG
     *
     * @param update
     */
    private void handlerDogButton(Update update) {
        logger.info("method handlerDogButton is invoke");
        CallbackQuery callbackQuery = update.callbackQuery();
        long chatId = callbackQuery.message().chat().id();
        switch (callbackQuery.data()) {
            case "022_WORK_SCHEDULE":
                SendMessage sendMessage = new SendMessage(chatId, shelterDog.getAddress());
                SendResponse response = telegramBot.execute(sendMessage);
                break;
            case "022_REGISTRATION_CAR":
                SendMessage sendMessage2 = new SendMessage(chatId, shelterDog.getContactDetails());
                SendResponse response2 = telegramBot.execute(sendMessage2);
                break;
            case "022_SAFETY":
                SendMessage sendMessage3 = new SendMessage(chatId, DOWNLOAD_LINK + "01");
                SendResponse response3 = telegramBot.execute(sendMessage3);
                break;
            case "022_CREATE":
                SendMessage sendMessage4 = new SendMessage(chatId, CREATE);
                SendResponse response4 = telegramBot.execute(sendMessage4);
                break;
            case "022_CALL":
                SendMessage sendMessage5 = new SendMessage(chatId, CALLBACK);
                SendResponse response5 = telegramBot.execute(sendMessage5);
                break;
        }
    }

    /**
     * Точка входа в блок обработки сallBackQuery
     *
     * @param update Извлекает из сallBackQuery.data() substring с индексом клавиатуры(21-CAT,22-DOG)
     */
    public void handlerSubMenuButton(Update update) {
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
