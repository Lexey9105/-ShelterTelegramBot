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
import pro.sky.ShelterTelegramBot.model.Client;
import pro.sky.ShelterTelegramBot.model.Report;
import pro.sky.ShelterTelegramBot.model.ReportBreach;
import pro.sky.ShelterTelegramBot.model.ReportStatus;
import pro.sky.ShelterTelegramBot.service.*;
import pro.sky.ShelterTelegramBot.utils.Send;
import pro.sky.ShelterTelegramBot.utils.TelegramFileService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static pro.sky.ShelterTelegramBot.constants.Constants.*;
@Service
public class VolunteerCallback {

    private final TelegramBot telegramBot;
    private final AttachmentService attachmentService;
    private final ClientStatusService clientStatusService;
    private final ClientService clientService;
    private final RequestRepoService requestRepoService;
    private final  ControlService controlService;
    private final Send send;
    private final ReportService reportService;
    private final ReportStatusService reportStatusService;
    private final ReportBreachService reportBreachService;
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    public VolunteerCallback(TelegramBot telegramBot,ClientService clientService,
                                ClientStatusService clientStatusService,Send send, AttachmentService attachmentService,RequestRepoService requestRepoService,
                                ReportService reportService,ReportStatusService reportStatusService,
                                ReportBreachService reportBreachService,ControlService controlService
    ) {
        this.telegramBot = telegramBot;
        this.attachmentService=attachmentService;
        this.send = send;
        this.clientStatusService=clientStatusService;
        this.clientService=clientService;
        this.requestRepoService=requestRepoService;
        this.controlService=controlService;
        this.reportService=reportService;
        this.reportStatusService=reportStatusService;
        this.reportBreachService=reportBreachService;
    }

    public  void VolunteerButton(Update update) throws IOException {
        logger.info("VolunteerButton is invoke");
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
               //controlService.welcome(client);
                        // clientStatusService.updateStatus(Long.parseLong(nameClient[1]), Report_Status);
                clientStatusService.updateStatusWithReport(Long.parseLong(nameClient[1]));
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
}
