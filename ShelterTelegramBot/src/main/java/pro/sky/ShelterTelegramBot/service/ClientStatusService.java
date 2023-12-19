package pro.sky.ShelterTelegramBot.service;


import pro.sky.ShelterTelegramBot.model.Client;
import pro.sky.ShelterTelegramBot.model.ClientStatus;
import pro.sky.ShelterTelegramBot.model.UserStatement;

import java.util.Collection;

public interface ClientStatusService {

    ClientStatus create(Long chatId);

    ClientStatus updateStatus(Long chatId, String status);

    ClientStatus updateStatusWithReport(Long chatId);

    ClientStatus delete(Long id);

    ClientStatus get(Long id);

    Collection<ClientStatus> findAll();

    ClientStatus findClient(Long chatId);

    int clickCat(Long id, int click);

    int clickDog(Long id, int click);

    ClientStatus updateWithUserStatement(ClientStatus clientStatus, UserStatement userStatement);
}
