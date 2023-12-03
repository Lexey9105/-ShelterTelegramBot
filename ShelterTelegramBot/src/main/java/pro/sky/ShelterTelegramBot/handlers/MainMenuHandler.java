package pro.sky.ShelterTelegramBot.handlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
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
import pro.sky.ShelterTelegramBot.service.VolunteerService;
import pro.sky.ShelterTelegramBot.utils.Send;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static pro.sky.ShelterTelegramBot.constants.Constants.*;
import static pro.sky.ShelterTelegramBot.handlers.Button.animalSelectionButtons;
@Service
public class MainMenuHandler {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private static final Logger LOG = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final TelegramBot telegramBot;
    private final ClientService clientService;
    private final ClientStatusService clientStatusService;
    private final VolunteerService volunteerService;
    private final Send send;
    private final HandlerCallbackQuery handlerCallbackQuery;



    private static final String START_COMMAND = "/start";
    private static final String HELP_COMMAND = "/help";
    Pattern pattern = Pattern.compile("\\d{3}-\\d{3}-\\d{2}-\\d{2}");

    public MainMenuHandler(TelegramBot telegramBot, Send send, ClientService clientService, ClientStatusService clientStatusService, VolunteerService volunteerService, HandlerCallbackQuery handlerCallbackQuery) {
        this.telegramBot = telegramBot;
        this.clientService = clientService;
        this.clientStatusService=clientStatusService;
        this.volunteerService=volunteerService;
        this.send = send;
        this.handlerCallbackQuery = handlerCallbackQuery;
    }

    /**
     * Получает информацию из обновления - идентификатор чата и текст сообщения
     *
     * @param update
     */
    public void processUpdate(Update update) {
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
            send.sendMessage(sendMessage);
        }
    }



    /**
     * Метод обрабатывает команду и возвращает соответствующий текст
     *
     * @param command
     * @return
     */
    private String handleCommand(Update update, String command) {
        long chatId = update.message().chat().id();
        switch (command) {
            case START_COMMAND:
                ClientStatus clientStatus=new ClientStatus(chatId,"Гость",0,0);
                clientStatusService.create(clientStatus);
                sendShelterTypeSelectMessage(update);
                return SAY_HELLO;
            case HELP_COMMAND:
                return ASK_HELP;
            default:
                return "Если есть вопросы свяжитесь с волонтером с помощью кнопки вызова - Позвать волонтера";
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
            ClientStatus clientStatus=clientStatusService.registration(client.getChatId());
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
