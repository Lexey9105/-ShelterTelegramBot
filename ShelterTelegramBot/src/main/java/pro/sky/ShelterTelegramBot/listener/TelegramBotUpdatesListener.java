package pro.sky.ShelterTelegramBot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;


import jakarta.annotation.PostConstruct;

import org.apache.tomcat.util.net.NioEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.ShelterTelegramBot.handlers.CallbackQuery.InfoShelterCallbackQuery;
import pro.sky.ShelterTelegramBot.model.Client;
import pro.sky.ShelterTelegramBot.service.ClientService;

import static pro.sky.ShelterTelegramBot.constants.Constants.*;
import static pro.sky.ShelterTelegramBot.handlers.Button.*;

import java.util.List;

/**
 * Сервис отвечает за обработку и отправку сообщений в Telegram бота
 */
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final TelegramBot telegramBot;
    private final ClientService clientService;
    private final InfoShelterCallbackQuery infoShelterCallbackQuery;


    private static final String START_COMMAND = "/start";
    private static final String HELP_COMMAND = "/help";

    public TelegramBotUpdatesListener(TelegramBot telegramBot, ClientService clientService, InfoShelterCallbackQuery infoShelterCallbackQuery) {
        this.telegramBot = telegramBot;
        this.clientService = clientService;
        this.infoShelterCallbackQuery = infoShelterCallbackQuery;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    /**
     * Получает список обновлений от бота, отфильтровывает только текстовые сообщения
     * и обрабатывает методом {@code processUpdate(Update update)}
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
                    responseButton(update);

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
        if(text.substring(0,1).equals("@")){
            saveClient(update);}
        else{
        String returnText = handleCommand(update, text);
        SendMessage sendMessage = new SendMessage(chatId, returnText);
        sendMessage(sendMessage);}
    }

    /**
     * Отправляет полученное сообщение обратно в чат
     *
//     * @param chatId
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
                 return "Передаю вопрос волонтерам";
        }
    }

    private void responseButton(Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        long chatId = callbackQuery.message().chat().id();
            switch (callbackQuery.data()) {
                case "DOG_SHELTER_CALLBACK":
                    // Dog shelter selected
                    System.out.println(callbackQuery.data());
                    SendMessage message1=new SendMessage(chatId,DOG_SHELTER_CALLBACK);
                    sendMessage(message1.replyMarkup(infoShelterDogButtons()));
                    break;
                case "CAT_SHELTER_CALLBACK":
                    // Cat shelter selected
                    SendMessage message2=new SendMessage(chatId,CAT_SHELTER_CALLBACK);
                    sendMessage(message2.replyMarkup(infoShelterCatButtons()));

                    break;
                default:
                    infoShelterCallbackQuery.handlerSubMenuButton(update);
            }
    }

    private void sendShelterTypeSelectMessage(Update update) {
        Long chatId = update.message().chat().id();
        SendMessage message = new SendMessage(chatId, SHELTER_TYPE_SELECT_MSG_TEXT);
        // Adding buttons
        sendMessage(message.replyMarkup(animalSelectionButtons()));
    }

    private void sendButtonClickMessage(long chatId, String message) {
        SendMessage messageGo=new SendMessage(chatId,message);
        if(message.equals(CAT_SHELTER_CALLBACK)){
            sendMessage(messageGo.replyMarkup(infoShelterCatButtons()));};
        if(message.equals(DOG_SHELTER_CALLBACK)){
            sendMessage(messageGo.replyMarkup(infoShelterDogButtons()));};

    }

    private void saveClient(Update update){
        long chatId = update.message().chat().id();
        String text = update.message().text();
        String[] parts = text.split(",");
        Client client=new Client(parts[0].substring(1),Integer.parseInt(parts[1]),Long.parseLong(parts[2]),parts[3]);
        clientService.create(client);
        String test="контактные данные успешно полученны";
        SendMessage sendMessage=new SendMessage(update.message().chat().id(),test);
        sendMessage(sendMessage);
        }

    //private void processStartCommand(Update update) {
       // long chatId = update.message().chat().id();
       // switch (shelterType) {
           // case DOG:
              //  sendStage0Message(chatId, DOG_SHELTER_WELCOME_MSG_TEXT);
              //  break;
          // case CAT:
              //  sendStage0Message(chatId, CAT_SHELTER_WELCOME_MSG_TEXT);
               // break;
           // default:
                //sendShelterTypeSelectMessage(chatId);
        //}
    //}
}


