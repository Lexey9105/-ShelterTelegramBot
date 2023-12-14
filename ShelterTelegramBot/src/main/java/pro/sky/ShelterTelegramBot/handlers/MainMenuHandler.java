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
import pro.sky.ShelterTelegramBot.model.Volunteer;
import pro.sky.ShelterTelegramBot.service.ClientService;
import pro.sky.ShelterTelegramBot.service.ClientStatusService;
import pro.sky.ShelterTelegramBot.service.ControlService;
import pro.sky.ShelterTelegramBot.service.VolunteerService;
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
    private final HandlerCallbackQuery handlerCallbackQuery;
    private final ControlService controlService;



    private static final String START_COMMAND = "/start";
    private static final String HELP_COMMAND = "/help";
    Pattern pattern = Pattern.compile("\\d{3}-\\d{3}-\\d{2}-\\d{2}");


    public MainMenuHandler(TelegramBot telegramBot, Send send, ClientService clientService, ClientStatusService clientStatusService, VolunteerService volunteerService,TelegramFileService telegramFileService, HandlerCallbackQuery handlerCallbackQuery,ControlService controlService) {
        this.telegramBot = telegramBot;
        this.clientService = clientService;
        this.clientStatusService=clientStatusService;
        this.volunteerService=volunteerService;
        this.telegramFileService=telegramFileService;
        this.send = send;
        this.handlerCallbackQuery = handlerCallbackQuery;
        this.controlService=controlService;
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

       if(update.message().photo()!=null){telegramFileService.getLocalPathTelegramFile(update);}
        else if (text.charAt(0) == '@') {
            saveClient(update);
        } else if (text.charAt(0) == '$') {
            saveVolunteer(update);}
       else if (text.equals(volunteerService.findByUserName(text).getUserName())) {
           String returnText = "Приятной работы "+text;
           SendMessage sendMessage = new SendMessage(chatId, returnText);
           send.sendMessage(sendMessage.replyMarkup(MenuVolunteerButtons()));
           } else if (text.charAt(0) == '&') {
          String[] pars=text.split("_");
           controlService.hand(clientStatusService.findClient(chatId).getClient(),pars[1]);

       }  else {
            String returnText = handleCommand(update, text);
            SendMessage sendMessage = new SendMessage(chatId, returnText);
            send.sendMessage(sendMessage);
        }
    }



    /**
     * Метод обрабатывает команду и возвращает соответствующий текст
     *
     * @param command
     * @return
     */
    private String handleCommand(Update update, String command)  {
        long chatId = update.message().chat().id();
        switch (command) {
            case START_COMMAND:
                clientStatusService.create(update.message().chat().id());
                sendShelterTypeSelectMessage(update);
                return SAY_HELLO;
            case HELP_COMMAND:
                return ASK_HELP;

            default: return "Позвать волонтера";
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


    /**
     * Метод для создания Client из сообщения update
     *
     * @param update
     * @return
     */
    private void saveClient(Update update) {
        long chatId = update.message().chat().id();
        String text = update.message().text();
        String[] parts = text.split(",");
        String nullName="zero";
        Matcher matcher = pattern.matcher(parts[2]);
        if (parts.length!=4){
            String error = "Некоректный ввод данных для регистрации.";
            SendMessage sendMessage = new SendMessage(update.message().chat().id(), error);
            send.sendMessage(sendMessage);
        }
        else if(clientService.findByUserName(parts[0].substring(1)).getName().equals(parts[0].substring(1))){
            String error = "Вы уже зарегистрированы.";
            SendMessage sendMessage = new SendMessage(update.message().chat().id(), error);
            send.sendMessage(sendMessage);
        }

        else if (matcher.matches()){
            Client client = new Client(chatId,parts[0].substring(1), Integer.parseInt(parts[1]), "+7-"+parts[2], parts[3]);
            clientService.create(client);
            ClientStatus clientStatus=clientStatusService.updateStatus(client.getChatId(),Registration_Status);
            clientService.updateWithClientStatus(client,clientStatus);
            String test = "контактные данные успешно полученны";
            SendMessage sendMessage = new SendMessage(update.message().chat().id(), test);
            send.sendMessage(sendMessage);
        }else{
            String errorTel = "Некоректный формат телефона";
            SendMessage sendMessage = new SendMessage(update.message().chat().id(), errorTel);
            send.sendMessage(sendMessage);

        }
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
