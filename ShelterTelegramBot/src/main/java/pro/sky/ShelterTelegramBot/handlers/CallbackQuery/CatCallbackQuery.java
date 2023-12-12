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
import pro.sky.ShelterTelegramBot.model.Client;
import pro.sky.ShelterTelegramBot.model.Request;
import pro.sky.ShelterTelegramBot.model.Volunteer;
import pro.sky.ShelterTelegramBot.service.AttachmentService;
import pro.sky.ShelterTelegramBot.service.ClientStatusService;
import pro.sky.ShelterTelegramBot.service.RequestRepoService;
import pro.sky.ShelterTelegramBot.service.VolunteerService;

import java.io.IOException;

import static pro.sky.ShelterTelegramBot.constants.Constants.*;

@Service
public class CatCallbackQuery {

    private final TelegramBot telegramBot;
    private final AttachmentService attachmentService;
    private final ClientStatusService clientStatusService;
    private final VolunteerService volunteerService;
    private final RequestRepoService requestRepoService;

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    public CatCallbackQuery(TelegramBot telegramBot, AttachmentService attachmentService,ClientStatusService clientStatusService,VolunteerService volunteerService,RequestRepoService requestRepoService) {
        this.telegramBot = telegramBot;
        this.attachmentService=attachmentService;
        this.clientStatusService=clientStatusService;
        this.volunteerService=volunteerService;
        this.requestRepoService=requestRepoService;
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
                clientStatusService.clickCat(chatId,1);
                SendPhoto sendPhoto =new SendPhoto(chatId,attachmentService.loadFile("attach__00.jpg"));
                SendResponse response = telegramBot.execute(sendPhoto);
                break;
            case REGISTRATION_CARCat:
                clientStatusService.clickCat(chatId,3);
                SendMessage sendMessage2 = new SendMessage(chatId, shelterCat.getContactDetails());
                SendResponse response2 = telegramBot.execute(sendMessage2);
                break;
            case SAFETYCat:
                //SendMessage sendMessage3 = new SendMessage(chatId, DOWNLOAD_LINK + "01.docx");
                // SendResponse response3 = telegramBot.execute(sendMessage3);
                clientStatusService.clickCat(chatId,1);
                SendDocument sendDoc =new SendDocument(chatId,attachmentService.loadFile("attach__01.docx"));
                SendResponse response3 = telegramBot.execute(sendDoc);
                break;
            case CREATECat:
                clientStatusService.clickCat(chatId,3);
                SendMessage sendMessage4 = new SendMessage(chatId, CREATE);
                SendResponse response4 = telegramBot.execute(sendMessage4);
                break;
            case CALLCat:
                clientStatusService.clickCat(chatId,1);
                Volunteer volunteer= volunteerService.findByStatus(0,2);
                String volunteerName="@"+volunteer.getUserName()+" -"+"ваш личный помошник. Готов помочь с любой проблемой)";
                SendMessage sendMessage5 = new SendMessage(chatId, volunteerName);
                SendResponse response5 = telegramBot.execute(sendMessage5);
                break;

            case PetCatsList:
                //SendMessage sendMessage = new SendMessage(chatId, shelterCat.getAddress());
                // SendResponse response = telegramBot.execute(sendMessage);
                Client client=clientStatusService.findClient(chatId).getClient();
                clientStatusService.updateStatus(chatId,Report_Status);
                Request request=new Request(client.getName(),"заявка на усыновление");
                requestRepoService.create(request);
                String forWork="Скоро тут будет красивый списочек с животными.Фотография животного, к ней прикреплен keyBoard(Будет создаваться для каждого питомца в методе класса petsService)";
                SendMessage sendMessage6 = new SendMessage(chatId, forWork);
                SendResponse response6 = telegramBot.execute(sendMessage6);
                break;
            case RulesDatingCat:
                clientStatusService.clickCat(chatId,1);
                SendDocument sendDoc2 =new SendDocument(chatId,attachmentService.loadFile("attach__021.docx"));
                SendResponse response7 = telegramBot.execute(sendDoc2);
                break;
            case DocCat:
                clientStatusService.clickCat(chatId,1);
                SendDocument sendDoc3 =new SendDocument(chatId,attachmentService.loadFile("attach__022.docx"));
                SendResponse response8 = telegramBot.execute(sendDoc3);
                break;
            case TransportOfCat:
                clientStatusService.clickCat(chatId,1);
                SendDocument sendDoc4 =new SendDocument(chatId,attachmentService.loadFile("attach__023.docx"));
                SendResponse response9 = telegramBot.execute(sendDoc4);
                break;
            case HomeCatsChild:
                clientStatusService.clickCat(chatId,1);
                SendDocument sendDoc5 =new SendDocument(chatId,attachmentService.loadFile("attach__21_024.docx"));
                SendResponse response10 = telegramBot.execute(sendDoc5);
                break;
            case HomeCatsAdult:
                clientStatusService.clickCat(chatId,1);
                SendDocument sendDoc6 =new SendDocument(chatId,attachmentService.loadFile("attach__21_025.docx"));
                SendResponse response11 = telegramBot.execute(sendDoc6);
                break;
            case HomeCatsDisabilities:
                clientStatusService.clickCat(chatId,1);
                SendDocument sendDoc7 =new SendDocument(chatId,attachmentService.loadFile("attach__21_026.docx"));
                SendResponse response12 = telegramBot.execute(sendDoc7);
                break;
            case FailCats:
                clientStatusService.clickCat(chatId,1);
                SendDocument sendDoc8 =new SendDocument(chatId,attachmentService.loadFile("attach__21_027.docx"));
                SendResponse response13 = telegramBot.execute(sendDoc8);
                break;
            case CREATECats_31:
                clientStatusService.clickCat(chatId,4);
                SendMessage sendMessage8 = new SendMessage(chatId, CREATE);
                SendResponse response14 = telegramBot.execute(sendMessage8);
                break;
            case CALLCats_31:
                clientStatusService.clickCat(chatId,2);
                Volunteer volunteer2= volunteerService.findByStatus(0,2);
                String volunteerName2="@"+volunteer2.getUserName()+" -"+"ваш личный помошник. Готов помочь с любой проблемой)";
                SendMessage sendMessage9 = new SendMessage(chatId, volunteerName2);
                SendResponse response15 = telegramBot.execute(sendMessage9);
                break;

        }
    }

}
