package pro.sky.ShelterTelegramBot.handlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.ShelterTelegramBot.constants.ShelterType;
import pro.sky.ShelterTelegramBot.handlers.CallbackQuery.HandlerCallbackQuery;
import pro.sky.ShelterTelegramBot.listener.TelegramBotUpdatesListener;
import pro.sky.ShelterTelegramBot.model.*;
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
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static pro.sky.ShelterTelegramBot.constants.Constants.*;
import static pro.sky.ShelterTelegramBot.handlers.Button.MenuVolunteerButtons;
import static pro.sky.ShelterTelegramBot.handlers.Button.animalSelectionButtons;

@Service
public class MainMenuHandler {

    private Logger logger = LoggerFactory.getLogger(MainMenuHandler.class);
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
    private final ShelterService shelterService;
    private final PetService petService;


    private static final String START_COMMAND = "/start";
    private static final String HELP_COMMAND = "/help";

    /**
     * Класс для обработки сообщений пользователя
     */
    public MainMenuHandler(TelegramBot telegramBot, Send send, ClientService clientService, ClientStatusService clientStatusService, VolunteerService volunteerService, TelegramFileService telegramFileService
            , ControlService controlService,
                           UserStatementService userStatementService,
                           ShelterService shelterService, PetService petService) {
        this.telegramBot = telegramBot;
        this.clientService = clientService;
        this.clientStatusService = clientStatusService;
        this.volunteerService = volunteerService;
        this.telegramFileService = telegramFileService;
        this.send = send;
        //this.handlerCallbackQuery = handlerCallbackQuery;
        this.controlService = controlService;
        this.userStatementService = userStatementService;
        this.shelterService = shelterService;
        this.petService = petService;
    }

    /**
     * Получает информацию из обновления - идентификатор чата и текст сообщения
     * распределяет Update по блокам обработки
     * Точки входа в другие классы для поледующей обработки:
     * text.isEmpty() - обработка фото от пользователя см строка 96
     * if(clientStatus.isEmpty()) проверка на первое обращение к боту,
     * создание необходимых для работы блока сущностей см строка 111
     * else if(userStatement2.get().getStatement().equals("@")|| -
     * вход в UserStatementService для обработки ОС от InlineKeyBoardButton и реализации
     * ответа приложения.
     */
    public void processUpdate(Update update) throws IOException {
        logger.info("Processing update: {}", update);

        Long chatId = update.message().chat().id();
        Optional<String> text = Optional.ofNullable(update.message().text());
        Optional<ClientStatus> clientStatusError = Optional.ofNullable(clientStatusService.findClient(update.message().chat().id()));

        if (clientStatusError.isPresent() && clientStatusError.get().getClientStatus().equals(Failed_Status)) {
            String error = "Вы провалили проверку, бот для вас заблокирован. Ожидайте приезда волонтеров";
            SendMessage sendMessage = new SendMessage(update.message().chat().id(), error);
            SendResponse sendResponse = telegramBot.execute(sendMessage);
        } else if (text.isEmpty()) {
            Optional<UserStatement> userStatement2 = Optional.ofNullable(clientStatusService.findClient(chatId).getUserStatement());
            logger.info("text.isEmpty()");
            //UserStatement userStatement=clientStatusService.findClient(chatId).getUserStatement();
            if (update.message().photo() != null) {
                logger.info(" photo handler");
                telegramFileService.getLocalPathTelegramFile(update);
            } else if (update.message().photo() != null && userStatement2.get().getStatement().equals(PhotoReport)) {
                logger.info("userStatement photo handler");
                userStatementService.appoint(update, text.get());
            }
        } else {
            int iText = text.get().indexOf("_");
            Optional<ClientStatus> clientStatus = Optional.ofNullable(clientStatusService.findClient(chatId));
            if (clientStatus.isEmpty()) {
                logger.info("first start");
                ClientStatus clientStatus2 = clientStatusService.create(update.message().chat().id());
                UserStatement userStatement = userStatementService.create();
                clientStatusService.updateWithUserStatement(clientStatus2, userStatement);
                createShelter();
                String returnText = handleCommand(update, text.get());
                SendMessage sendMessage = new SendMessage(chatId, returnText);
                send.sendMessage(sendMessage);
            } else if (text.get().charAt(0) == '$') {
                logger.info("saveVolunteer");
                saveVolunteer(update);
            } else if (text.get().equals(volunteerService.findByUserName(text.get()).getUserName())) {
                logger.info("startVolunteer");
                String returnText = "Приятной работы " + text;
                SendMessage sendMessage = new SendMessage(chatId, returnText);
                send.sendMessage(sendMessage.replyMarkup(MenuVolunteerButtons()));
            } else if (text.get().charAt(0) == '!') {
                logger.info("startSavePetAndShelter");
                savePetAndShelter(update);
            } else if (text.get().equals("/start") || text.get().equals("/help")) {
                String returnText = handleCommand(update, text.get());
                SendMessage sendMessage = new SendMessage(chatId, returnText);
                send.sendMessage(sendMessage);

            } else if (clientStatusError.isPresent()) {
                Optional<UserStatement> userStatement1 = Optional.ofNullable(clientStatusService.findClient(chatId).getUserStatement());
                if (userStatement1.get().getStatement().equals("@")
                        || userStatement1.get().getStatement().equals("&") ||
                        userStatement1.get().getStatement().equals("#") ||
                        userStatement1.get().getStatement().equals("=")) {
                    logger.info("userStatement2.isPresent()");

                    userStatementService.appoint(update, text.get());
                    //}
                }
            } else {
                logger.info("startHandleCommand");
                String returnText = handleCommand(update, text.get());
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
        logger.info("HandleCommand");
        long chatId = update.message().chat().id();
        switch (command) {
            case START_COMMAND:
                sendShelterTypeSelectMessage(update);
                return SAY_HELLO;
            case HELP_COMMAND:
                return ASK_HELP;

            default:
                return "Данная комманда не поддерживается ботом";
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
        userStatementService.appoint(update, text);
    }

    private void saveVolunteer(Update update) {
        String userName = update.message().from().username();
        String nullName = "zero";
        if (volunteerService.findByUserName(userName).getUserName().equals(nullName)) {
            Long id = volunteerService.getCount();
            Volunteer volunteer = new Volunteer(id, userName, 0);
            volunteerService.create(volunteer);
            String welcome = "Вы успешно стали волонтером. Так как мы не реализовали функцию удления данных волонтера из приложения, Вы с нами надолго)))";
            SendMessage sendMessage = new SendMessage(update.message().chat().id(), welcome);
            send.sendMessage(sendMessage);
        } else {
            String welcome = "Вы уже являетесь волонтером. P.S - функция удаления по прежнему отсуствует((.РАБотайте! ";
            SendMessage sendMessage = new SendMessage(update.message().chat().id(), welcome);
            send.sendMessage(sendMessage);
        }

    }

    private void createShelter() {
        if (shelterService.getCount() == 0) {
            Shelter shelter1 = new Shelter(Cats_Shelter, ShelterType.CAT_SHELTER, "адрес", "часы работы", "контакт для связи");
            Shelter shelter2 = new Shelter(Dogs_Shelter, ShelterType.DOG_SHELTER, "адрес", "часы работы", "контакт для связи");
            shelterService.create(shelter1);
            shelterService.create(shelter2);
            logger.info("приюты созданы" + shelterService.getCount());
        } else {
            logger.info("приюты созданы" + shelterService.getCount());
        }
    }

    private void savePetAndShelter(Update update) {
        String[] parts = update.message().text().split("_");
        Pet pet = new Pet(parts[1], parts[2], parts[3]);
        petService.create(pet);
        if (parts[1].equals("кошка")) {
            Shelter shelter = shelterService.findByName(Cats_Shelter);
            shelterService.createWithPets(shelter);
            //shelterService.updateWithPet(shelterService.findByName(Cats_Shelter),pet);
            petService.updateWithShelter(shelter, pet);
        } else if (parts[1].equals("собака")) {
            Shelter shelter = shelterService.findByName(Dogs_Shelter);
            shelterService.createWithPets(shelter);
            // shelterService.updateWithPet(shelterService.findByName(Dogs_Shelter),pet);
            petService.updateWithShelter(shelter, pet);
        }

    }
}