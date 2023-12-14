package pro.sky.ShelterTelegramBot.service.impl;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.ShelterTelegramBot.handlers.MainMenuHandler;
import pro.sky.ShelterTelegramBot.model.*;
import pro.sky.ShelterTelegramBot.repository.UserStatementRepository;
import pro.sky.ShelterTelegramBot.service.*;
import pro.sky.ShelterTelegramBot.utils.Send;
import pro.sky.ShelterTelegramBot.utils.TelegramFileService;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static pro.sky.ShelterTelegramBot.constants.Constants.Registration_Status;
import static pro.sky.ShelterTelegramBot.constants.Constants.Zero_CallBack;

@Service
public class UserStatementServiceImpl implements UserStatementService {

    Pattern pattern = Pattern.compile("\\d{3}-\\d{3}-\\d{2}-\\d{2}");

    private final UserStatementRepository userStatementRepository;
    private final ClientStatusService clientStatusService;
    private final ClientService clientService;
    private final Send send;
    private final ReportService reportService;
   // private final MainMenuHandler mainMenuHandler;
    private final ControlService controlService;
    private final VolunteerService volunteerService;
    private final TelegramFileService telegramFileService;
    private Logger logger = LoggerFactory.getLogger(ReportService.class);

    public UserStatementServiceImpl(UserStatementRepository userStatementRepository,
                                    ClientStatusService clientStatusService,
                                    ReportService reportService,
                                    //MainMenuHandler mainMenuHandler,
                                    ControlService controlService
                             ,TelegramFileService telegramFileService,ClientService clientService,
                                    VolunteerService volunteerService,Send send){
        this.userStatementRepository=userStatementRepository;
        this.clientStatusService=clientStatusService;
        this.reportService=reportService;
        //this.mainMenuHandler=mainMenuHandler;
        this.controlService=controlService;
        this.telegramFileService=telegramFileService;
        this.clientService=clientService;
        this.volunteerService=volunteerService;
        this.send=send;
    }

    @Override
    public UserStatement create() {
        logger.info("createReport method has been invoked");
        UserStatement userStatement=new UserStatement();
        return userStatementRepository.save(userStatement);
    }
    @Override
    public UserStatement update(UserStatement userStatement) {
        logger.info("createReport method has been invoked");
        return userStatementRepository.save(userStatement);
    }

    @Override
    public UserStatement delete(Long id) {
        Optional<UserStatement> userStatement=userStatementRepository.findById(id);
        if(userStatement.isPresent()){
            userStatementRepository.delete(userStatement.get());
        }else {
            logger.error("There is no report with id: " + id);
            throw new EntityNotFoundException("Репорта с " + id + "id не существует");
        }
        return userStatement.get();
    }
    @Override
    public void zero(UserStatement userStatement){
        logger.info("zero method has been invoked");
        userStatement.setStatement("");
        update(userStatement);

    }

    @Override
    public UserStatement get(Long id) {
        Optional<UserStatement> userStatement=userStatementRepository.findById(id);
        if(userStatement.isPresent()){
            return userStatement.get();
        }else {
            logger.error("There is no report with id: " + id);
            throw new EntityNotFoundException("Репорта с " + id + "id не существует");
        }

    }

    @Override
    public Collection<UserStatement> getAll() {
        return userStatementRepository.findAll();
    }

    @Override
    public boolean checkForHandler(Update update) {
        ClientStatus clientStatus=clientStatusService.findClient(update.message().chat().id());
        if (!clientStatus.getUserStatement().getStatement().equals("")){
            return true;
        }else{
        return false;}
    }

    @Override
    public void appoint(Update update, String text) throws IOException {
        ClientStatus clientStatus=clientStatusService.findClient(update.message().chat().id());
        if (clientStatus.getUserStatement().getStatement().equals("@")){
            saveClient(update);
            zero(clientStatus.getUserStatement());
        }
        else if (clientStatus.getUserStatement().getStatement().equals("&")||clientStatus.getUserStatement().getStatement().equals("фото отчет")) {
           Report report= reportService.findReportsByClient(clientStatus.getClient()).stream()
                   .filter(report1 -> report1.getDayReport()==clientStatus.getDayReport())
                   .findFirst().orElse(new Report("",0,""));
            if (clientStatus.getUserStatement().getStatement().equals("&")){
                controlService.hand(clientStatus.getClient(),text);
                zero(clientStatus.getUserStatement());
            }
            else if (update.message().photo()!=null&&clientStatus.getUserStatement().getStatement().equals("фото отчет")){
                telegramFileService.getLocalPathTelegramFile(update);
                zero(clientStatus.getUserStatement());
            } else if (update.message().text().equals(Zero_CallBack)) {
                zero(clientStatus.getUserStatement());
            } else{
                throw new RuntimeException();
            }
        }
    }

    /**
     * Метод для создания Client из сообщения update
     *
     * @param update
     * @return
     */
    public void saveClient(Update update) {
        long chatId = update.message().chat().id();
        String text = update.message().text();
        String[] parts = text.split(",");
        //String nullName="zero";
        Matcher matcher = pattern.matcher(parts[2]);
        if (parts.length!=4){
            String error = "Некоректный ввод данных для регистрации.";
            SendMessage sendMessage = new SendMessage(update.message().chat().id(), error);
            send.sendMessage(sendMessage);
        }
        else if(clientService.findByUserName(parts[0]).getName().equals(parts[0])){
            String error = "Вы уже зарегистрированы.";
            SendMessage sendMessage = new SendMessage(update.message().chat().id(), error);
            send.sendMessage(sendMessage);
        }

        else if (matcher.matches()){
            Client client = new Client(chatId,parts[0], Integer.parseInt(parts[1]), "+7-"+parts[2], parts[3]);
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


}
