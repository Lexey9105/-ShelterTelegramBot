package pro.sky.ShelterTelegramBot.service;

import org.springframework.transaction.annotation.Transactional;
import pro.sky.ShelterTelegramBot.model.*;
import pro.sky.ShelterTelegramBot.model.Report;

import java.util.Collection;

public interface ClientService {
    Client create(Client client);

    Client delete(Long id);

    Client get(Long id);

    Collection<Client> findAll();

    Pet getPet(Long id);

    Client findClientByChatId(Long chatId);

    Client findByUserName(String userName);

    Client createWithReport(Client client);

    Client updateWithClientStatus(Client client, ClientStatus clientStatus);

    @Transactional
    Client updateWithReport(Client client, Report report);

    Client createWithPets(Client client);

    Client updateWithPet(Client client, Pet pet);


}
