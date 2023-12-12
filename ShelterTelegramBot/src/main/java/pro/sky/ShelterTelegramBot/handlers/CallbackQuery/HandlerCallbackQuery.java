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

import static pro.sky.ShelterTelegramBot.constants.Constants.*;
import static pro.sky.ShelterTelegramBot.handlers.Button.*;

@Service
public class HandlerCallbackQuery {

    private final TelegramBot telegramBot;
    private final AttachmentService attachmentService;
    private final VolunteerService volunteerService;
    private final ClientStatusService clientStatusService;
    private final ClientService clientService;
    private final DogCallbackQuery dogCallbackQuery;
    private final CatCallbackQuery catCallbackQuery;
    private final TelegramFileService telegramFileService;
    private final RequestRepoService requestRepoService;
    private final  ControlService controlService;
    private final Send send;
    private final ReportService reportService;
    private final ReportStatusService reportStatusService;
    private final ReportBreachService reportBreachService;
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    public HandlerCallbackQuery(TelegramBot telegramBot,ClientService clientService,
                                ClientStatusService clientStatusService,Send send,
                                DogCallbackQuery dogCallbackQuery, CatCallbackQuery catCallbackQuery, AttachmentService attachmentService,
                                VolunteerService volunteerService,
                                TelegramFileService telegramFileService,RequestRepoService requestRepoService,
                                ReportService reportService,ReportStatusService reportStatusService,
                                ReportBreachService reportBreachService,ControlService controlService
    ) {
        this.telegramBot = telegramBot;
        this.attachmentService=attachmentService;
        this.volunteerService=volunteerService;
        this.dogCallbackQuery=dogCallbackQuery;
        this.catCallbackQuery=catCallbackQuery;
        this.send = send;
        this.clientStatusService=clientStatusService;
        this.clientService=clientService;
        this.telegramFileService=telegramFileService;
        this.requestRepoService=requestRepoService;
        this.controlService=controlService;
        this.reportService=reportService;
        this.reportStatusService=reportStatusService;
        this.reportBreachService=reportBreachService;
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

    public  void VolunteerButton(Update update) throws IOException {

        CallbackQuery callbackQuery = update.callbackQuery();
        long chatId = callbackQuery.message().chat().id();

        switch (callbackQuery.data()) {
            case Get_Request:
                requestRepoService.getAll().forEach(request -> {
                    Client client=clientService.findByUserName(request.getUserName());
                    InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
                    InlineKeyboardButton AcceptButton = new InlineKeyboardButton("Одобрить запрос").
                            callbackData(Request_CallBack+"_"+client.getChatId().toString()+"_"+request.getId()+"_"+Accept);
                    InlineKeyboardButton DenyButton = new InlineKeyboardButton("Отклонить запрос").
                            callbackData(Request_CallBack+"_"+client.getChatId().toString()+"_"+request.getId()+"_"+Deny);
                    keyboardMarkup.addRow(AcceptButton);
                    keyboardMarkup.addRow(DenyButton);
                    SendMessage sendMessage=new SendMessage(chatId,"Пользователь "+client.getName()+" отправил заявку на усыновление");
                    SendResponse sendResponse=telegramBot.execute(sendMessage.replyMarkup(keyboardMarkup));
                });
                break;
            case Get_Report:
                logger.info("method Get_Report is invoke");
                controlService.reject();
                controlService.load().forEach(report -> {
                    Client client=report.getClient();
                    InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
                    InlineKeyboardButton AcceptButton = new InlineKeyboardButton("Одобрить запрос").
                            callbackData(Report_CallBack+"_"+client.getChatId().toString()+"_"+report.getDayReport()+"_"+Accept);
                    InlineKeyboardButton DenyButton = new InlineKeyboardButton("Отклонить запрос").
                            callbackData(Report_CallBack+"_"+client.getChatId().toString()+"_"+report.getDayReport()+"_"+Deny);
                    keyboardMarkup.addRow(AcceptButton);
                    keyboardMarkup.addRow(DenyButton);
                    try {
                        SendPhoto sendPhoto =new SendPhoto(chatId,attachmentService.loadFile(report.getAttachment().getAttachTitle()));
                    SendResponse sendResponse=telegramBot.execute(sendPhoto);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    SendMessage sendMessage=new SendMessage(chatId,"Пользователь "+client.getName()+" отправил отчет на проверку");
                    SendResponse sendResponse=telegramBot.execute(sendMessage.replyMarkup(keyboardMarkup));

                });
                break;
        }
    }

    public  void ReportButton(Update update) throws IOException {
        logger.info("method handlerCatButton is invoke");
        CallbackQuery callbackQuery = update.callbackQuery();
        long chatId = callbackQuery.message().chat().id();
        String [] nameClient=callbackQuery.data().split("_");
        Client client=clientStatusService.findClient(Long.parseLong(nameClient[1])).getClient();
        switch (nameClient[3]) {
            case Accept:
                controlService.accept(client);
                SendMessage sendMessage1 = new SendMessage(client.getChatId(), "Ваш отчет одобрили");
                SendResponse response1 = telegramBot.execute(sendMessage1);
                break;
            case Deny:
                List<Report> reports=reportService.findReportsByClient(client);
                int i=reports.size()-1;
                controlService.refusal(reports.get(i));
                SendMessage sendMessage2 = new SendMessage(client.getChatId(), "Ваш отчет не приняли, если есть вопросы свяжитесь с волонтером");
                SendResponse response2 = telegramBot.execute(sendMessage2);
                break;
        }
    }

    public  void RequestButton(Update update) throws IOException {
        logger.info("method handlerCatButton is invoke");
        CallbackQuery callbackQuery = update.callbackQuery();
        long chatId = callbackQuery.message().chat().id();
        String [] nameClient=callbackQuery.data().split("_");
        Client client=clientStatusService.findClient(Long.parseLong(nameClient[1])).getClient();
        switch (nameClient[3]) {
            case Accept:
               // Client client2=clientStatusService.findClient(Long.parseLong(nameClient[1])).getClient();

                clientStatusService.updateStatus(Long.parseLong(nameClient[1]), Report_Status);
                ReportStatus reportStatus = new ReportStatus("", "");
                reportStatus.setTotalDayReport(30);
                ReportBreach reportBreach = new ReportBreach();
                Report report = new Report(LocalDateTime.now().plusDays(1).format(FORMATTER), 1, Report_Status);
                reportService.create(report);
                reportStatusService.create(reportStatus);
                reportBreachService.create(reportBreach);
                clientService.updateWithReportStatus(client, reportStatus);
                clientService.updateWithReportBreach(client, reportBreach);
                clientService.createWithReport(client);
                clientService.updateWithReport(client, report);
                reportService.updateWithClient(client,report);

                requestRepoService.delete(Integer.parseInt(nameClient[2]));
                SendMessage sendMessage1 = new SendMessage(client.getChatId(), "Вам одобрили усыновление  питомца. С сегодняшнего дня в должны присылать отчет");
                SendResponse response1 = telegramBot.execute(sendMessage1);
                break;
            case Deny:
                requestRepoService.delete(Integer.parseInt(nameClient[2]));
                SendMessage sendMessage2 = new SendMessage(client.getChatId(), "Вам отказали в усыновлении питомца. Для получения причин отказа свяжитесь с волонтером");
                SendResponse response2 = telegramBot.execute(sendMessage2);
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

            case Report_CallBack:
                ReportButton(update);
                break;
            case Request_CallBack:
                RequestButton(update);
                break;
            case Volunteer:
                VolunteerButton(update);
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
                catCallbackQuery.handlerCatButton(update);
                break;
            case CALL_ID_SHELTER_INFORMATION_DOG_MENU:
                dogCallbackQuery.handlerDogButton(update);
                break;
            case CALL_Pet_MENU_DOG:
                dogCallbackQuery.handlerDogButton(update);
                break;
            default:telegramFileService.getLocalPathTelegramFile(update);
        }
    }


}
