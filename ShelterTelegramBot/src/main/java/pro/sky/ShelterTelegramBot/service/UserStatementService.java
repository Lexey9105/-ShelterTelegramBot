package pro.sky.ShelterTelegramBot.service;

import com.pengrad.telegrambot.model.Update;
import pro.sky.ShelterTelegramBot.model.ClientStatus;
import pro.sky.ShelterTelegramBot.model.Report;
import pro.sky.ShelterTelegramBot.model.UserStatement;

import java.io.IOException;
import java.util.Collection;

public interface UserStatementService {

    UserStatement create();
    UserStatement update (UserStatement userStatement);
    UserStatement delete (Long id);
    void zero(UserStatement userStatement);
    UserStatement get(Long id);
    Collection<UserStatement> getAll();

    boolean checkForHandler(Update update);

    void appoint(Update update, String text) throws IOException;
}
