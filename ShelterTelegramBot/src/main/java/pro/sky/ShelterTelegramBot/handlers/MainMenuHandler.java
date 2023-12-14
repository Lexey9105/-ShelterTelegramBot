package pro.sky.ShelterTelegramBot.handlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.ShelterTelegramBot.handlers.CallbackQuery.HandlerCallbackQuery;
import pro.sky.ShelterTelegramBot.listener.TelegramBotUpdatesListener;
import pro.sky.ShelterTelegramBot.model.Client;
import pro.sky.ShelterTelegramBot.model.ClientStatus;
import pro.sky.ShelterTelegramBot.model.UserStatement;
import pro.sky.ShelterTelegramBot.model.Volunteer;
import pro.sky.ShelterTelegramBot.service.*;
import pro.sky.ShelterTelegramBot.utils.Send;
import pro.sky.ShelterTelegramBot.utils.TelegramFileService;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static pro.sky.ShelterTelegramBot.constants.Constants.*;
import static pro.sky.ShelterTelegramBot.handlers.Button.MenuVolunteerButtons;
import static pro.sky.ShelterTelegramBot.handlers.Button.animalSelectionButtons;
@Service
public class MainMenuHandler {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private static final Logger LOG = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final TelegramBot telegramBot;
    private final ClientService clientService;
    private final ClientStatusService clientStatusService;
    private final VolunteerService volunteerService;
    private final TelegramFileService telegramFileService;
    private final Send send;
    //private final HandlerCallbackQuery handlerCallbackQuery;
    private final ControlService controlService;
    private final UserStatementService userStatementService;



    private static final String START_COMMAND = "/start";
    private static final String HELP_COMMAND = "/help";

//,HandlerCallbackQuery handlerCallbackQuery

    public MainMenuHandler(TelegramBot telegramBot, Send send, ClientService clientService, ClientStatusService clientStatusService, VolunteerService volunteerService,TelegramFileService telegramFileService
                          ,ControlService controlService,
                     UserStatementService userStatementService) {
        this.telegramBot = telegramBot;
        this.clientService = clientService;
        this.clientStatusService=clientStatusService;
        this.volunteerService=volunteerService;
        this.telegramFileService=telegramFileService;
        this.send = send;
        //this.handlerCallbackQuery = handlerCallbackQuery;
        this.controlService=controlService;
        this.userStatementService=userStatementService;
    }

    /**
     * Получает информацию из обновления - идентификатор чата и текст сообщения
     *
     * @param update
     */
    public void processUpdate(Update update) throws IOException {
        logger.info("Processing update: {}", update);

        Long chatId = update.message().chat().id();
        String text = update.message().text();

       if(update.message().photo()!=null){statementHandler(update);}
        // if (clientStatus.getUserStatement().getStatement().equals(text)) {

        //}

        //else if (text.charAt(0) == '$') {
          //  saveVolunteer(update);}
        else if(text.equals(START_COMMAND)){
            String returnText = handleCommand(update, text);
            SendMessage sendMessage = new SendMessage(chatId, returnText);
            send.sendMessage(sendMessage);
        } else if (text.charAt(0) == '$') {
            saveVolunteer(update);
        } else if (text.equals(volunteerService.findByUserName(text).getUserName())) {
           String returnText = "Приятной работы "+text;
           SendMessage sendMessage = new SendMessage(chatId, returnText);
           send.sendMessage(sendMessage.replyMarkup(MenuVolunteerButtons()));
           }
       //else if (text.charAt(0) == '&') {
         // String[] pars=text.split("_");
           //controlService.hand(clientStatusService.findClient(chatId).getClient(),pars[1]);
       //}

            else {
            if (userStatementService.checkForHandler(update)) {
                statementHandler(update);
            } else {
                String returnText = handleCommand(update, text);
                SendMessage sendMessage = new SendMessage(chatId, returnText);
                send.sendMessage(sendMessage);
            }
        }

    }



    /**
     * Метод обрабатывает команду и возвращает соответствующий текст
     *
     * @param command
     * @return
     */
    private String handleCommand(Update update, String command) throws IOException {
        long chatId = update.message().chat().id();
        switch (command) {
            case START_COMMAND:
               ClientStatus clientStatus= clientStatusService.create(update.message().chat().id());
                UserStatement userStatement=userStatementService.create();
                clientStatusService.updateWithUserStatement(clientStatus,userStatement);
                sendShelterTypeSelectMessage(update);
                return SAY_HELLO;
            case HELP_COMMAND:
                return ASK_HELP;

            default: return "Данная комманда не поддерживается ботом";
        }
    }

    /**
     * Метод для создания  клавиатуры  replyMarkup первого уровня с выбором питомника
     *
     * @param update
     * @return
     */
    private void sendShelterTypeSelectMessage(Update update) {
        Long chatId = update.message().chat().id();
        SendMessage message = new SendMessage(chatId, SHELTER_TYPE_SELECT_MSG_TEXT);
        // Adding buttons
        send.sendMessage(message.replyMarkup(animalSelectionButtons()));
    }

    public void statementHandler(Update update) throws IOException {
        Long chatId = update.message().chat().id();
        String text = update.message().text();
            userStatementService.appoint(update,text);
    }

    private void saveVolunteer(Update update) {
        String userName=update.message().from().username();
        String nullName="zero";
        if (volunteerService.findByUserName(userName).getUserName().equals(nullName)) {
            Long id = volunteerService.getCount();
            Volunteer volunteer = new Volunteer(id, userName, 0);
            volunteerService.create(volunteer);
            String welcome = "Вы успешно стали волонтером. Так как мы не реализовали функцию удления данных волонтера из приложения, Вы с нами надолго)))";
            SendMessage sendMessage = new SendMessage(update.message().chat().id(), welcome);
            send.sendMessage(sendMessage);
        }

        else {
            String welcome = "Вы уже являетесь волонтером. P.S - функция удаления по прежнему отсуствует((.РАБотайте! ";
            SendMessage sendMessage = new SendMessage(update.message().chat().id(), welcome);
            send.sendMessage(sendMessage);}

    }
}
