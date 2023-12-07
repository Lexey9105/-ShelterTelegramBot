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

import java.io.IOException;

import static pro.sky.ShelterTelegramBot.constants.Constants.*;

@Service
public class DogCallbackQuery {
    private final TelegramBot telegramBot;
    private final AttachmentService attachmentService;
    private final ClientStatusService clientStatusService;
    private final VolunteerService volunteerService;

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    public DogCallbackQuery(TelegramBot telegramBot, AttachmentService attachmentService,ClientStatusService clientStatusService,VolunteerService volunteerService) {
        this.telegramBot = telegramBot;
        this.attachmentService=attachmentService;
        this.clientStatusService=clientStatusService;
        this.volunteerService=volunteerService;
    }

    /**
     * обработка сallBackQuery для клавиатуры DOG
     *
     * @param update
     */
    public void handlerDogButton(Update update) throws IOException {
        logger.info("method handlerDogButton is invoke");
        CallbackQuery callbackQuery = update.callbackQuery();
        long chatId = callbackQuery.message().chat().id();
        String shelterAddress=attachmentService.loadFileAsResource("attach__00.jpg").getURL().toString();
        switch (callbackQuery.data()) {
            case WORK_SCHEDULEDog:
                clientStatusService.clickDog(chatId,1);
                SendPhoto sendPhoto =new SendPhoto(chatId, attachmentService.loadFile("attach__00.jpg"));
                SendResponse response = telegramBot.execute(sendPhoto);
                break;
            case REGISTRATION_CARDog:
                clientStatusService.clickDog(chatId,3);
                SendMessage sendMessage2 = new SendMessage(chatId, shelterDog.getContactDetails());
                SendResponse response2 = telegramBot.execute(sendMessage2);
                break;
            case SAFETYDog:
                clientStatusService.clickDog(chatId,1);
                SendDocument sendDoc =new SendDocument(chatId,attachmentService.loadFile("attach__01.docx"));
                SendResponse response3 = telegramBot.execute(sendDoc);
                break;
            case CREATEDog:
                clientStatusService.clickDog(chatId,3);
                SendMessage sendMessage4 = new SendMessage(chatId, CREATE);
                SendResponse response4 = telegramBot.execute(sendMessage4);
                break;
            case CALLDog:
                clientStatusService.clickDog(chatId,1);
                Volunteer volunteer= volunteerService.findByStatus(0,2);
                String volunteerName="@"+volunteer.getUserName()+" -"+"ваш личный помошник. Готов помочь с любой проблемой)";
                SendMessage sendMessage5 = new SendMessage(chatId, volunteerName);
                SendResponse response5 = telegramBot.execute(sendMessage5);
                break;

            case PetDogList:
                //SendMessage sendMessage = new SendMessage(chatId, shelterCat.getAddress());
                // SendResponse response = telegramBot.execute(sendMessage);
                String forWork="Скоро тут будет красивый списочек с животными.Фотография животного, к ней прикреплен keyBoard(Будет создаваться для каждого питомца в методе класса petsService)";
                SendMessage sendMessage6 = new SendMessage(chatId, forWork);
                SendResponse response6 = telegramBot.execute(sendMessage6);
                break;
            case RulesDatingDog:
                clientStatusService.clickDog(chatId,1);
                SendDocument sendDoc2 =new SendDocument(chatId,attachmentService.loadFile("attach__021.docx"));
                SendResponse response7 = telegramBot.execute(sendDoc2);
                break;
            case DocDog:
                clientStatusService.clickDog(chatId,1);
                SendDocument sendDoc3 =new SendDocument(chatId,attachmentService.loadFile("attach__022.docx"));
                SendResponse response8 = telegramBot.execute(sendDoc3);
                break;
            case TransportOfDog:
                clientStatusService.clickDog(chatId,1);
                SendDocument sendDoc4 =new SendDocument(chatId,attachmentService.loadFile("attach__023.docx"));
                SendResponse response9 = telegramBot.execute(sendDoc4);
                break;

            case HomeDogsChild:
                clientStatusService.clickDog(chatId,1);
                SendDocument sendDoc5 =new SendDocument(chatId,attachmentService.loadFile("attach__22_024.docx"));
                SendResponse response10 = telegramBot.execute(sendDoc5);
                break;
            case HomeDogsAdult:
                clientStatusService.clickDog(chatId,1);
                SendDocument sendDoc6 =new SendDocument(chatId,attachmentService.loadFile("attach__22_025.docx"));
                SendResponse response11 = telegramBot.execute(sendDoc6);
                break;
            case HomeDogsDisabilities:
                clientStatusService.clickDog(chatId,1);
                SendDocument sendDoc7 =new SendDocument(chatId,attachmentService.loadFile("attach__22_026.docx"));
                SendResponse response12 = telegramBot.execute(sendDoc7);
                break;
            case DogHandlerFirstTime:
                clientStatusService.clickDog(chatId,1);
                SendDocument sendDoc8 =new SendDocument(chatId,attachmentService.loadFile("attach__22_027.docx"));
                SendResponse response13 = telegramBot.execute(sendDoc8);
                break;
            case DogHandlerRecommendation:
                clientStatusService.clickDog(chatId,1);
                SendDocument sendDoc9 =new SendDocument(chatId,attachmentService.loadFile("attach__22_028.docx"));
                SendResponse response14 = telegramBot.execute(sendDoc9);
                break;
            case FailDogs:
                clientStatusService.clickDog(chatId,1);
                SendDocument sendDoc10 =new SendDocument(chatId,attachmentService.loadFile("attach__22_029.docx"));
                SendResponse response15 = telegramBot.execute(sendDoc10);
                break;
            case CREATEDogs_32:
                clientStatusService.clickDog(chatId,4);
                SendMessage sendMessage11 = new SendMessage(chatId, CREATE);
                SendResponse response16 = telegramBot.execute(sendMessage11);
                break;
            case CALLDogs_32:
                clientStatusService.clickDog(chatId,2);
                Volunteer volunteer2= volunteerService.findByStatus(0,2);
                String volunteerName2="@"+volunteer2.getUserName()+" -"+"ваш личный помошник. Готов помочь с любой проблемой)";
                SendMessage sendMessage12 = new SendMessage(chatId, volunteerName2);
                SendResponse response17 = telegramBot.execute(sendMessage12);
                break;









        }
    }

}
