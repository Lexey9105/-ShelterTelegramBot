package pro.sky.ShelterTelegramBot.handlers.CallbackQuery;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;


import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.SendResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import pro.sky.ShelterTelegramBot.listener.TelegramBotUpdatesListener;
import pro.sky.ShelterTelegramBot.model.*;
import pro.sky.ShelterTelegramBot.service.*;
import pro.sky.ShelterTelegramBot.utils.Send;
import pro.sky.ShelterTelegramBot.utils.TelegramFileService;


import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static pro.sky.ShelterTelegramBot.constants.Constants.*;
import static pro.sky.ShelterTelegramBot.handlers.Button.*;

@Service
public class HandlerCallbackQuery {

    private final TelegramBot telegramBot;
    private final VolunteerService volunteerService;
    private final ClientStatusService clientStatusService;
    private final DogCallbackQuery dogCallbackQuery;
    private final CatCallbackQuery catCallbackQuery;
    private final TelegramFileService telegramFileService;
    private final VolunteerCallback volunteerCallback;
    private final ClientService clientService;
    private final PetService petService;
    private final Send send;
    private final ControlCallbackQuery controlCallbackQuery;
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    /**
     * Класс для обработки и распределения CallBackQuery
     */
    public HandlerCallbackQuery(TelegramBot telegramBot,
                                ClientStatusService clientStatusService, Send send,
                                DogCallbackQuery dogCallbackQuery, CatCallbackQuery catCallbackQuery,
                                VolunteerService volunteerService,
                                TelegramFileService telegramFileService,
                                VolunteerCallback volunteerCallback,
                                ControlCallbackQuery controlCallbackQuery,
                                ClientService clientService, PetService petService

    ) {
        this.telegramBot = telegramBot;
        this.volunteerService = volunteerService;
        this.dogCallbackQuery = dogCallbackQuery;
        this.catCallbackQuery = catCallbackQuery;
        this.send = send;
        this.clientStatusService = clientStatusService;
        this.telegramFileService = telegramFileService;
        this.volunteerCallback = volunteerCallback;
        this.controlCallbackQuery = controlCallbackQuery;
        this.clientService = clientService;
        this.petService = petService;
    }

    /**
     * Метод обрабатывает сообщения от клавиатуры replyMarkup.
     * Точка входа в handlerSubMenuButton.Метод для распределения CallBack по адресу клавиатуры
     * Адресс - первые три цифры CallBackQuery
     *
     * @param update
     */
    public void responseButton(Update update) throws IOException {
        Optional<ClientStatus> clientStatusError = Optional.ofNullable(clientStatusService.findClient(update.callbackQuery().message().chat().id()));
        if (clientStatusError.get().getClientStatus().equals(Failed_Status)) {
            String error = "Вы провалили проверку, бот для вас заблокирован. Ожидайте приезда волонтеров";
            SendMessage sendMessage = new SendMessage(update.callbackQuery().message().chat().id(), error);
            SendResponse sendResponse = telegramBot.execute(sendMessage);
        } else {
            CallbackQuery callbackQuery = update.callbackQuery();
            long chatId = callbackQuery.message().chat().id();
            switch (callbackQuery.data()) {
                case DOG_SHELTER_CALLBACK:
                    // Dog shelter selected
                    clientStatusService.clickDog(chatId, 1);
                    SendMessage message1 = new SendMessage(chatId, DOG_SHELTER_HELLO);
                    send.sendMessage(message1.replyMarkup(MenuShelterDogButtons()));
                    break;
                case CAT_SHELTER_CALLBACK:
                    // Cat shelter selected
                    clientStatusService.clickCat(chatId, 1);
                    SendMessage message2 = new SendMessage(chatId, CAT_SHELTER_HELLO);
                    send.sendMessage(message2.replyMarkup(MenuShelterCatButtons()));

                    break;
                default:
                    handlerSubMenuButton(update);
            }
        }
    }

    /**
     * медод для обработки CallBack от клавиатуры с переходами в другие меню для кошачьего приюта
     */

    public void handlerMenuCatButton(Update update) throws IOException {
        logger.info("method handlerMenuCatButton is invoke");
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
                Optional<Client> client = Optional.ofNullable(clientService.findClientByChatId(chatId));
                Optional<Pet> pet1 = petService.findPetByClient(client.get());
                if (pet1.isPresent()) {
                    clientStatusService.clickCat(chatId, 1);
                    SendMessage sendMessage3 = new SendMessage(chatId, "Меню отправки отчета приветствует Вас");
                    SendResponse response3 = telegramBot.execute(sendMessage3.replyMarkup(MenuReportButtons()));
                    break;
                } else {
                    clientStatusService.clickCat(chatId, 1);
                    SendMessage sendMessage3 = new SendMessage(chatId, "Вы еще не взяли ни одного питомца на усыновление");
                    SendResponse response3 = telegramBot.execute(sendMessage3);
                    break;
                }
            case CALLCats_12:
                clientStatusService.clickCat(chatId, 1);
                Volunteer volunteer = volunteerService.findByStatus(0, 2);
                String volunteerName = "@" + volunteer.getUserName() + " -" + "ваш личный помошник. Готов помочь с любой проблемой)";
                SendMessage sendMessage5 = new SendMessage(chatId, volunteerName);
                SendResponse response5 = telegramBot.execute(sendMessage5);
                break;
        }
    }

    /**
     * медод для обработки CallBack от клавиатуры с переходами в другие меню для собачьего приюта
     */
    public void handlerMenuDogButton(Update update) throws IOException {
        logger.info("method handlerMenuDogButton is invoke");
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
                Optional<Client> client = Optional.ofNullable(clientService.findClientByChatId(chatId));
                Optional<Pet> pet1 = petService.findPetByClient(client.get());
                if (pet1.isPresent()) {
                    clientStatusService.clickCat(chatId, 1);
                    SendMessage sendMessage3 = new SendMessage(chatId, "Меню отправки отчета приветствует Вас");
                    SendResponse response3 = telegramBot.execute(sendMessage3.replyMarkup(MenuReportButtons()));
                    break;
                } else {
                    clientStatusService.clickCat(chatId, 1);
                    SendMessage sendMessage3 = new SendMessage(chatId, "Вы еще не взяли ни одного питомца на усыновление");
                    SendResponse response3 = telegramBot.execute(sendMessage3);
                    break;
                }
            case CALLDogs_12:
                clientStatusService.clickDog(chatId, 1);
                Volunteer volunteer = volunteerService.findByStatus(0, 2);
                String volunteerName = "@" + volunteer.getUserName() + " -" + "ваш личный помошник. Готов помочь с любой проблемой)";
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
            case Create_Report:
                catCallbackQuery.getPetsCatButton(update);
                break;
            case Report_CallBack:
                volunteerCallback.ReportButton(update);
                break;
            case Request_CallBack:
                volunteerCallback.RequestButton(update);
                break;
            case Volunteer:
                volunteerCallback.VolunteerButton(update);
                break;
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
                catCallbackQuery.infoPetsCatButton(update);
                break;
            case CALL_ID_SHELTER_INFORMATION_DOG_MENU:
                dogCallbackQuery.handlerDogButton(update);
                break;
            case CALL_Pet_MENU_DOG:
                dogCallbackQuery.infoPetsDogButton(update);
                break;
            default:
                controlCallbackQuery.ControlCallBack(update);
        }
    }


}
