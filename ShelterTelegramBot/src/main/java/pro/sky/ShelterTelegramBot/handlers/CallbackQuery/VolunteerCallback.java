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
import java.util.Optional;

import static pro.sky.ShelterTelegramBot.constants.Constants.*;

@Service
public class VolunteerCallback {

    private final TelegramBot telegramBot;
    private final AttachmentService attachmentService;
    private final ClientStatusService clientStatusService;
    private final ClientService clientService;
    private final RequestRepoService requestRepoService;
    private final ControlService controlService;
    private final Send send;
    private final ReportService reportService;
    private final ReportStatusService reportStatusService;
    private final ReportBreachService reportBreachService;
    private final PetService petService;
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    public VolunteerCallback(TelegramBot telegramBot, ClientService clientService,
                             ClientStatusService clientStatusService, Send send, AttachmentService attachmentService, RequestRepoService requestRepoService,
                             ReportService reportService, ReportStatusService reportStatusService,
                             ReportBreachService reportBreachService, ControlService controlService
            , PetService petService
    ) {
        this.telegramBot = telegramBot;
        this.attachmentService = attachmentService;
        this.send = send;
        this.clientStatusService = clientStatusService;
        this.clientService = clientService;
        this.requestRepoService = requestRepoService;
        this.controlService = controlService;
        this.reportService = reportService;
        this.reportStatusService = reportStatusService;
        this.reportBreachService = reportBreachService;
        this.petService = petService;
    }

    public void VolunteerButton(Update update) throws IOException {
        logger.info("VolunteerButton is invoke");
        CallbackQuery callbackQuery = update.callbackQuery();
        long chatId = callbackQuery.message().chat().id();

        switch (callbackQuery.data()) {
            case Get_Request:
                requestRepoService.getAll().forEach(request -> {
                    Client client = clientService.findByUserName(request.getUserName());
                    InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
                    InlineKeyboardButton AcceptButton = new InlineKeyboardButton("Одобрить запрос").
                            callbackData(Request_CallBack + "_" + client.getChatId().toString() + "_" + request.getId() + "_" + Accept);
                    InlineKeyboardButton DenyButton = new InlineKeyboardButton("Отклонить запрос").
                            callbackData(Request_CallBack + "_" + client.getChatId().toString() + "_" + request.getId() + "_" + Deny);
                    keyboardMarkup.addRow(AcceptButton);
                    keyboardMarkup.addRow(DenyButton);
                    SendMessage sendMessage = new SendMessage(chatId, "Пользователь " + client.getName() + " отправил заявку на усыновление" + request.getPetName());
                    SendResponse sendResponse = telegramBot.execute(sendMessage.replyMarkup(keyboardMarkup));
                });
                break;
            case Get_Report:
                logger.info("method Get_Report is invoke");
                controlService.reject();
                controlService.load().forEach(report -> {
                    Client client = report.getClient();
                    InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
                    InlineKeyboardButton AcceptButton = new InlineKeyboardButton("Одобрить запрос").
                            callbackData(Report_CallBack + "," + client.getChatId().toString() + "," + report.getName() + "," + Accept);
                    InlineKeyboardButton DenyButton = new InlineKeyboardButton("Отклонить запрос").
                            callbackData(Report_CallBack + "," + client.getChatId().toString() + "," + report.getName() + "," + Deny);
                    keyboardMarkup.addRow(AcceptButton);
                    keyboardMarkup.addRow(DenyButton);
                    try {
                        SendPhoto sendPhoto = new SendPhoto(chatId, attachmentService.loadFile(report.getAttachment().getAttachTitle()));
                        SendResponse sendResponse = telegramBot.execute(sendPhoto);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    SendMessage sendMessage = new SendMessage(chatId, "Пользователь " + client.getName() + " отправил отчет на проверку");
                    SendResponse sendResponse = telegramBot.execute(sendMessage.replyMarkup(keyboardMarkup));

                });
                break;
        }
    }

    public void ReportButton(Update update) throws IOException {
        logger.info("method ReportButton is invoke");
        CallbackQuery callbackQuery = update.callbackQuery();
        long chatId = callbackQuery.message().chat().id();
        String[] nameClient = callbackQuery.data().split(",");
        Client client = clientStatusService.findClient(Long.parseLong(nameClient[1])).getClient();
        Optional<Report> report = Optional.ofNullable(reportService.findReportByName(nameClient[2]));
        System.out.println(report.get().getLocalDateTime());
        //Optional<String> reportCheck=Optional.ofNullable(reportService.findReportByName(nameClient[1]).getStatus());
        //Report report=reportService.findReportByName(nameClient[1]);
        switch (nameClient[3]) {
            case Accept:
                if (report.isPresent()) {
                    logger.info("method ReportButtonReadyToShip is invoke");
                    controlService.accept(client, report.get());
                    SendMessage sendMessage1 = new SendMessage(client.getChatId(), "Ваш отчет одобрили");
                    SendResponse response1 = telegramBot.execute(sendMessage1);
                    break;
                } else {
                    SendMessage sendMessage1 = new SendMessage(chatId, "Вам уже одобрили этот отчет");
                    SendResponse response1 = telegramBot.execute(sendMessage1);
                    break;
                }
            case Deny:
                if (report.isPresent()) {
                    logger.info("method ReportButtonDeny is invoke");
                    controlService.refusal(report.get());
                    SendMessage sendMessage2 = new SendMessage(client.getChatId(), "Ваш отчет не приняли, если есть вопросы свяжитесь с волонтером");
                    SendResponse response2 = telegramBot.execute(sendMessage2);
                    break;
                } else {
                    SendMessage sendMessage1 = new SendMessage(chatId, "Вам уже отправили отказ");
                    SendResponse response1 = telegramBot.execute(sendMessage1);
                    break;
                }
        }
    }

    public void RequestButton(Update update) throws IOException {
        logger.info("method handlerCatButton is invoke");
        CallbackQuery callbackQuery = update.callbackQuery();
        long chatId = callbackQuery.message().chat().id();
        String[] nameClient = callbackQuery.data().split("_");
        Client client = clientStatusService.findClient(Long.parseLong(nameClient[1])).getClient();
        long chatId2 = Long.parseLong(nameClient[1]);
        Optional<Request> request = Optional.of(requestRepoService.get(Long.parseLong(nameClient[2])));
        switch (nameClient[3]) {
            case Accept:
                if (request.isPresent()) {
                    //Request request=requestRepoService.get(Long.parseLong(nameClient[2]));
                    // Client client2=clientStatusService.findClient(Long.parseLong(nameClient[1])).getClient();
                    clientStatusService.updateStatus(Long.parseLong(nameClient[1]), Report_Status);
                    //controlService.welcome(client);
                    // clientStatusService.updateStatus(Long.parseLong(nameClient[1]), Report_Status);
                    clientStatusService.updateStatusWithReport(Long.parseLong(nameClient[1]));
                    ReportStatus reportStatus = new ReportStatus("", "");
                    reportStatus.setTotalDayReport(30);
                    ReportBreach reportBreach = new ReportBreach();
                    Pet pet = petService.findPetByName(request.get().getPetName());
                    pet.setDayInFamily(pet.getDayInFamily() + 1);
                    petService.update(pet);
                    String reportName = request.get().getPetName() + "_" + pet.getDayInFamily();
                    Report report = new Report(reportName, LocalDateTime.now().plusDays(1).format(FORMATTER), 1, Report_Status);
                    clientService.createWithPets(client);
                    clientService.updateWithPet(client, pet);
                    reportService.create(report);
                    reportStatusService.create(reportStatus);
                    reportBreachService.create(reportBreach);
                    petService.updateWithReportStatus(pet, reportStatus);
                    petService.updateWithReportBreach(pet, reportBreach);
                    clientService.createWithReport(client);
                    clientService.updateWithReport(client, report);
                    petService.createWithReports(pet);
                    petService.updateWithReport(pet, report);
                    reportService.updateWithPet(report, pet);
                    reportService.updateWithClient(client, report);
                    requestRepoService.delete(Long.parseLong(nameClient[2]));
                    SendMessage sendMessage1 = new SendMessage(chatId2, "Вам одобрили усыновление  питомца. С сегодняшнего дня в должны присылать отчет");
                    SendResponse response1 = telegramBot.execute(sendMessage1);
                    break;
                } else {
                    SendMessage sendMessage1 = new SendMessage(chatId, "Вам уже одобрили эту заявку");
                    SendResponse response1 = telegramBot.execute(sendMessage1);
                    break;
                }
            case Deny:
                if (request.isPresent()) {
                    requestRepoService.delete(Long.parseLong(nameClient[2]));
                    SendMessage sendMessage2 = new SendMessage(client.getChatId(), "Вам отказали в усыновлении питомца. Для получения причин отказа свяжитесь с волонтером");
                    SendResponse response2 = telegramBot.execute(sendMessage2);
                    break;
                } else {
                    SendMessage sendMessage1 = new SendMessage(chatId, "Вам уже отправии отказ");
                    SendResponse response1 = telegramBot.execute(sendMessage1);
                    break;
                }
        }
    }
}
