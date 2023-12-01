package pro.sky.ShelterTelegramBot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;


import jakarta.annotation.PostConstruct;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.ShelterTelegramBot.handlers.CallbackQuery.InfoShelterCallbackQuery;
import pro.sky.ShelterTelegramBot.model.Client;
import pro.sky.ShelterTelegramBot.model.ClientStatus;
import pro.sky.ShelterTelegramBot.model.Volunteer;
import pro.sky.ShelterTelegramBot.service.ClientService;
import pro.sky.ShelterTelegramBot.service.ClientStatusService;
import pro.sky.ShelterTelegramBot.service.VolunteerService;

import static pro.sky.ShelterTelegramBot.constants.Constants.DOG_SHELTER_CALLBACK;
import static pro.sky.ShelterTelegramBot.constants.Constants.*;
import static pro.sky.ShelterTelegramBot.handlers.Button.*;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Сервис отвечает за обработку и отправку сообщений в Telegram бота
 */
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private static final Logger LOG = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final TelegramBot telegramBot;
    private final ClientService clientService;
    private final ClientStatusService clientStatusService;
    private final VolunteerService volunteerService;
    private final InfoShelterCallbackQuery infoShelterCallbackQuery;


    private static final String START_COMMAND = "/start";
    private static final String HELP_COMMAND = "/help";
    Pattern pattern = Pattern.compile("\\d{3}-\\d{3}-\\d{2}-\\d{2}");

    public TelegramBotUpdatesListener(TelegramBot telegramBot, ClientService clientService,ClientStatusService clientStatusService,VolunteerService volunteerService, InfoShelterCallbackQuery infoShelterCallbackQuery) {
        this.telegramBot = telegramBot;
        this.clientService = clientService;
        this.clientStatusService=clientStatusService;
        this.volunteerService=volunteerService;
        this.infoShelterCallbackQuery = infoShelterCallbackQuery;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    /**
     * Получает список обновлений от бота, разделяет update по блокам  processUpdate(обработка сообщений от пользователя)
     * и  responseButton(update) обработка клавиатуры
     *
     * @param updates - сообщения
     * @return
     */
    @Override
    public int process(List<Update> updates) {
        try {
            updates.forEach(update -> {
                if (update.message() != null) {
                    processUpdate(update);
                } else {
                    try {
                        responseButton(update);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            });

        } catch (Exception e) {
            logger.error("Error during processing telegram update", e);
        }

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    /**
     * Получает информацию из обновления - идентификатор чата и текст сообщения
     *
     * @param update
     */
    private void processUpdate(Update update) {
        logger.info("Processing update: {}", update);

        Long chatId = update.message().chat().id();
        String text = update.message().text();
        if (text.substring(0, 1).equals("@")) {
            saveClient(update);
        } else if (text.substring(0, 1).equals("$")) {
            saveVolunteer(update);}
        else {
            String returnText = handleCommand(update, text);
            SendMessage sendMessage = new SendMessage(chatId, returnText);
            sendMessage(sendMessage);
        }
    }

    /**
     * Отправляет полученное сообщение обратно в чат
     * <p>
     * //     * @param chatId
     *
     * @param message
     */
    private void sendMessage(SendMessage message) {
        SendResponse response = telegramBot.execute(message);
        if (!response.isOk()) {
            logger.error("Error during sending message: {}", response.description());
        }
    }

    /**
     * Метод обрабатывает команду и возвращает соответствующий текст
     *
     * @param command
     * @return
     */
    private String handleCommand(Update update, String command) {

        switch (command) {
            case START_COMMAND:
                sendShelterTypeSelectMessage(update);
                return SAY_HELLO;
            case HELP_COMMAND:
                return ASK_HELP;
            default:
                return "Если есть вопросы свяжитесь с волонтером с помощью кнопки вызова - Позвать волонтера";
        }
    }

    /**
     * Метод обрабатывает сообщения от клавиатуры replyMarkup.Точка входа в InfoShelterCallbackQuery.Класс обработки кнопок клавиатуры
     *
     * @param update
     * @return
     */
    private void responseButton(Update update) throws IOException {
        CallbackQuery callbackQuery = update.callbackQuery();
        long chatId = callbackQuery.message().chat().id();
        switch (callbackQuery.data()) {
            case DOG_SHELTER_CALLBACK:
                // Dog shelter selected
                SendMessage message1 = new SendMessage(chatId, DOG_SHELTER_HELLO);
                sendMessage(message1.replyMarkup(infoShelterDogButtons()));
                break;
            case CAT_SHELTER_CALLBACK:
                // Cat shelter selected
                SendMessage message2 = new SendMessage(chatId, CAT_SHELTER_HELLO);
                sendMessage(message2.replyMarkup(infoShelterCatButtons()));

                break;
            default:
                infoShelterCallbackQuery.handlerSubMenuButton(update);
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
        sendMessage(message.replyMarkup(animalSelectionButtons()));
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
            sendMessage(sendMessage);
        }
        else if(clientService.findByUserName(parts[0].substring(1)).getName().equals(parts[0].substring(1))){
            String error = "Вы уже зарегистрированы.";
            SendMessage sendMessage = new SendMessage(update.message().chat().id(), error);
            sendMessage(sendMessage);
        }

        else if (matcher.matches()){
            Client client = new Client(parts[0].substring(1), Integer.parseInt(parts[1]), "+7-"+parts[2], parts[3]);
            clientService.create(client);
            ClientStatus clientStatus=new ClientStatus(chatId,"Зарегистрирован",0,0);
            clientStatusService.create(clientStatus);
            clientService.updateWithClientStatus(client,clientStatus);
            String test = "контактные данные успешно полученны";
            SendMessage sendMessage = new SendMessage(update.message().chat().id(), test);
            sendMessage(sendMessage);
        }else{
            String errorTel = "Некоректный формат телефона";
            SendMessage sendMessage = new SendMessage(update.message().chat().id(), errorTel);
            sendMessage(sendMessage);

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
              sendMessage(sendMessage);
          }

       else {
           String welcome = "Вы уже являетесь волонтером. P.S - функция удаления по прежнему отсуствует((.РАБотайте! ";
           SendMessage sendMessage = new SendMessage(update.message().chat().id(), welcome);
           sendMessage(sendMessage);}

       }
    }





