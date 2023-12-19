package pro.sky.ShelterTelegramBot.handlers.CallbackQuery;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.ShelterTelegramBot.listener.TelegramBotUpdatesListener;
import pro.sky.ShelterTelegramBot.model.*;
import pro.sky.ShelterTelegramBot.service.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static pro.sky.ShelterTelegramBot.constants.Constants.*;

@Service
public class CatCallbackQuery {

    private final TelegramBot telegramBot;
    private final AttachmentService attachmentService;
    private final ClientStatusService clientStatusService;
    private final VolunteerService volunteerService;
    private final RequestRepoService requestRepoService;
    private final UserStatementService userStatementService;
    private final PetService petService;
    private final ShelterService shelterService;

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    public CatCallbackQuery(TelegramBot telegramBot, AttachmentService attachmentService,
                            ClientStatusService clientStatusService, VolunteerService volunteerService,
                            RequestRepoService requestRepoService, UserStatementService userStatementService,
                            PetService petService, ShelterService shelterService) {
        this.telegramBot = telegramBot;
        this.attachmentService = attachmentService;
        this.clientStatusService = clientStatusService;
        this.volunteerService = volunteerService;
        this.requestRepoService = requestRepoService;
        this.userStatementService = userStatementService;
        this.petService = petService;
        this.shelterService = shelterService;
    }

    /**
     * обработка сallBackQuery для клавиатуры CAT
     *
     * @param update
     */
    public void handlerCatButton(Update update) throws IOException {
        logger.info("method handlerCatButton is invoke");
        CallbackQuery callbackQuery = update.callbackQuery();
        long chatId = callbackQuery.message().chat().id();

        switch (callbackQuery.data()) {
            case WORK_SCHEDULE:
                clientStatusService.clickCat(chatId, 1);
                SendPhoto sendPhoto = new SendPhoto(chatId, attachmentService.loadFile("attach__11.jpg"));
                SendResponse response = telegramBot.execute(sendPhoto);
                break;
            case REGISTRATION_CARCat:
                clientStatusService.clickCat(chatId, 3);
                SendMessage sendMessage2 = new SendMessage(chatId, shelterCat.getContactDetails());
                SendResponse response2 = telegramBot.execute(sendMessage2);
                break;
            case SAFETYCat:
                clientStatusService.clickCat(chatId, 1);
                SendDocument sendDoc = new SendDocument(chatId, attachmentService.loadFile("attach__01.docx"));
                SendResponse response3 = telegramBot.execute(sendDoc);
                break;
            case CREATECat:
                clientStatusService.clickCat(chatId, 3);
                UserStatement userStatement = clientStatusService.findClient(chatId).getUserStatement();
                userStatement.setStatement("@");
                userStatementService.update(userStatement);
                SendMessage sendMessage4 = new SendMessage(chatId, CREATE);
                SendResponse response4 = telegramBot.execute(sendMessage4);
                break;
            case CALLCat:
                clientStatusService.clickCat(chatId, 1);
                Volunteer volunteer = volunteerService.findByStatus(0, 2);
                String volunteerName = "@" + volunteer.getUserName() + " -" + "ваш личный помошник. Готов помочь с любой проблемой)";
                SendMessage sendMessage5 = new SendMessage(chatId, volunteerName);
                SendResponse response5 = telegramBot.execute(sendMessage5);
                break;


        }
    }

    public void infoPetsCatButton(Update update) throws IOException {
        logger.info("method infoPetsCatButton is invoke");
        CallbackQuery callbackQuery = update.callbackQuery();
        long chatId = callbackQuery.message().chat().id();
        Client client = clientStatusService.findClient(chatId).getClient();
        switch (callbackQuery.data()) {

            case PetCatsList:
                petService.findAllByPetType(Cats_Shelter).forEach(pet -> {
                    InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
                    String CallBackInfo = Create_Report + "," + Pets_Information + "," + chatId + "," + pet.getId();
                    String CallBackGet = Create_Report + "," + Pets_Get + "," + chatId + "," + pet.getId();
                    InlineKeyboardButton AcceptButton = new InlineKeyboardButton("Получить информацию о питомце").
                            callbackData(CallBackInfo);
                    InlineKeyboardButton DenyButton = new InlineKeyboardButton("Приютить питомца").
                            callbackData(CallBackGet);
                    keyboardMarkup.addRow(AcceptButton);
                    keyboardMarkup.addRow(DenyButton);
                    try {
                        SendPhoto sendPhoto = new SendPhoto(chatId, attachmentService.loadFile(pet.getAttachment().getAttachTitle()));
                        SendResponse sendResponse = telegramBot.execute(sendPhoto);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    SendMessage sendMessage = new SendMessage(chatId, pet.getName());
                    SendResponse sendResponse = telegramBot.execute(sendMessage.replyMarkup(keyboardMarkup));


                });
                break;
            case RulesDatingCat:
                clientStatusService.clickCat(chatId, 1);
                SendDocument sendDoc2 = new SendDocument(chatId, attachmentService.loadFile("attach__021.docx"));
                SendResponse response7 = telegramBot.execute(sendDoc2);
                break;
            case DocCat:
                clientStatusService.clickCat(chatId, 1);
                SendDocument sendDoc3 = new SendDocument(chatId, attachmentService.loadFile("attach__022.docx"));
                SendResponse response8 = telegramBot.execute(sendDoc3);
                break;
            case TransportOfCat:
                clientStatusService.clickCat(chatId, 1);
                SendDocument sendDoc4 = new SendDocument(chatId, attachmentService.loadFile("attach__023.docx"));
                SendResponse response9 = telegramBot.execute(sendDoc4);
                break;
            case HomeCatsChild:
                clientStatusService.clickCat(chatId, 1);
                SendDocument sendDoc5 = new SendDocument(chatId, attachmentService.loadFile("attach__21_024.docx"));
                SendResponse response10 = telegramBot.execute(sendDoc5);
                break;
            case HomeCatsAdult:
                clientStatusService.clickCat(chatId, 1);
                SendDocument sendDoc6 = new SendDocument(chatId, attachmentService.loadFile("attach__21_025.docx"));
                SendResponse response11 = telegramBot.execute(sendDoc6);
                break;
            case HomeCatsDisabilities:
                clientStatusService.clickCat(chatId, 1);
                SendDocument sendDoc7 = new SendDocument(chatId, attachmentService.loadFile("attach__21_026.docx"));
                SendResponse response12 = telegramBot.execute(sendDoc7);
                break;
            case FailCats:
                clientStatusService.clickCat(chatId, 1);
                SendDocument sendDoc8 = new SendDocument(chatId, attachmentService.loadFile("attach__21_027.docx"));
                SendResponse response13 = telegramBot.execute(sendDoc8);
                break;
            case CREATECats_31:
                clientStatusService.clickCat(chatId, 4);
                UserStatement userStatement = clientStatusService.findClient(chatId).getUserStatement();
                userStatement.setStatement("@");
                userStatementService.update(userStatement);
                SendMessage sendMessage8 = new SendMessage(chatId, CREATE);
                SendResponse response14 = telegramBot.execute(sendMessage8);
                break;
            case CALLCats_31:
                clientStatusService.clickCat(chatId, 2);
                Volunteer volunteer2 = volunteerService.findByStatus(0, 2);
                String volunteerName2 = "@" + volunteer2.getUserName() + " -" + "ваш личный помошник. Готов помочь с любой проблемой)";
                SendMessage sendMessage9 = new SendMessage(chatId, volunteerName2);
                SendResponse response15 = telegramBot.execute(sendMessage9);
                break;
        }
    }

    public void getPetsCatButton(Update update) throws IOException {
        logger.info("method getPetsCatButton is invoke");
        CallbackQuery callbackQuery = update.callbackQuery();
        long chatId = callbackQuery.message().chat().id();
        String[] parts = callbackQuery.data().split(",");
        System.out.println(callbackQuery.data());
        Client client = clientStatusService.findClient(Long.valueOf(parts[2])).getClient();
        switch (parts[1]) {
            case Pets_Information:
                Pet pet = petService.get(Long.valueOf(parts[3]));
                SendMessage sendMessage1 = new SendMessage(client.getChatId(), "Имя питомца" + pet.getName() + "пол" + pet.getGender());
                SendResponse response1 = telegramBot.execute(sendMessage1);
                break;
            case Pets_Get:
                Pet pet2 = petService.get(Long.valueOf(parts[3]));
                Optional<Request> requestCheck = Optional.ofNullable(requestRepoService.findRequestByPetName(pet2.getName()));
                if (requestCheck.isEmpty()) {
                    Request request = new Request(client.getName(), pet2.getName());
                    requestRepoService.create(request);
                    SendMessage sendMessage2 = new SendMessage(client.getChatId(), "Ваш запрос об усыновлении передан волотёру");
                    SendResponse response2 = telegramBot.execute(sendMessage2);
                    break;
                } else {
                    SendMessage sendMessage2 = new SendMessage(client.getChatId(), "Вы уже передали запрос об усыновлении волотёру");
                    SendResponse response2 = telegramBot.execute(sendMessage2);
                }
        }
    }

}
