package pro.sky.ShelterTelegramBot.service;

import pro.sky.ShelterTelegramBot.model.Client;

import java.util.Collection;

public interface ClientService {
    Client create (Client client);
    Client delete (Long id);
    Client get (Long id);
    Collection<Client> findAll();
    Object getClientsById();

}
