package pro.sky.ShelterTelegramBot.handlers.CallbackQuery;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.ShelterTelegramBot.listener.TelegramBotUpdatesListener;
import pro.sky.ShelterTelegramBot.model.UserStatement;
import pro.sky.ShelterTelegramBot.service.*;

import java.io.IOException;

import static pro.sky.ShelterTelegramBot.constants.Constants.*;

@Service
public class ControlCallbackQuery {

    private final TelegramBot telegramBot;
    private final AttachmentService attachmentService;
    private final ClientStatusService clientStatusService;
    private final VolunteerService volunteerService;
    private final RequestRepoService requestRepoService;
    private final UserStatementService userStatementService;

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    public ControlCallbackQuery(TelegramBot telegramBot, AttachmentService attachmentService,
                                ClientStatusService clientStatusService, VolunteerService volunteerService,
                                RequestRepoService requestRepoService, UserStatementService userStatementService) {
        this.telegramBot = telegramBot;
        this.attachmentService = attachmentService;
        this.clientStatusService = clientStatusService;
        this.volunteerService = volunteerService;
        this.requestRepoService = requestRepoService;
        this.userStatementService = userStatementService;
    }

    public void ControlCallBack(Update update) throws IOException {
        Long chatId = update.callbackQuery().message().chat().id();
        String CallBack = update.callbackQuery().data();
        logger.info("ControlCallBack is invoke");
        switch (CallBack) {
            case ReportInfo:
                SendDocument sendDoc = new SendDocument(chatId, attachmentService.loadFile("правила.txt"));
                SendResponse response = telegramBot.execute(sendDoc);
                break;
            case GetReportName:
                String reportName = "Для получения имени текущего отчета отправьте имя питомца";
                UserStatement userStatement3 = clientStatusService.findClient(chatId).getUserStatement();
                userStatement3.setStatement("#");
                userStatementService.update(userStatement3);
                SendMessage sendMessage3 = new SendMessage(chatId, reportName);
                SendResponse response3 = telegramBot.execute(sendMessage3);
                break;
            case PetsPhoto:
                UserStatement userStatement = clientStatusService.findClient(chatId).getUserStatement();
                userStatement.setStatement("фото отчет");
                userStatementService.update(userStatement);
                SendMessage sendMessage = new SendMessage(chatId, SendPhoto);
                SendResponse response1 = telegramBot.execute(sendMessage);
                break;
            case ReportControl:
                logger.info("ReportControl is invoke");
                UserStatement userStatement2 = clientStatusService.findClient(chatId).getUserStatement();
                userStatement2.setStatement("&");
                userStatementService.update(userStatement2);
                SendMessage sendMessage2 = new SendMessage(chatId, SendText);
                SendResponse response2 = telegramBot.execute(sendMessage2);
                break;
            case PushReport:
                logger.info("PushReport is invoke");
                UserStatement userStatement4 = clientStatusService.findClient(chatId).getUserStatement();
                userStatement4.setStatement("=");
                userStatementService.update(userStatement4);
                SendMessage sendMessage4 = new SendMessage(chatId, Send);
                SendResponse response4 = telegramBot.execute(sendMessage4);
                break;
            case CALL:
                pro.sky.ShelterTelegramBot.model.Volunteer volunteer = volunteerService.findByStatus(0, 2);
                String volunteerName = "@" + volunteer.getUserName() + " -" + "ваш личный помошник. Готов помочь с любой проблемой)";
                SendMessage sendMessage5 = new SendMessage(chatId, volunteerName);
                SendResponse response5 = telegramBot.execute(sendMessage5);
                break;

        }
    }
}
