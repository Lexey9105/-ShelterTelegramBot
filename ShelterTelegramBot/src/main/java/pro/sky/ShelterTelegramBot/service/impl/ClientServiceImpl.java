package pro.sky.ShelterTelegramBot.service.impl;

import jakarta.persistence.EntityNotFoundException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import pro.sky.ShelterTelegramBot.model.Client;
import pro.sky.ShelterTelegramBot.model.ClientStatus;
import pro.sky.ShelterTelegramBot.model.Volunteer;
import pro.sky.ShelterTelegramBot.repository.ClientRepository;
import pro.sky.ShelterTelegramBot.service.ClientService;

import java.util.Collection;
import java.util.Optional;


@Service
public class ClientServiceImpl implements ClientService {

    private Logger logger = LoggerFactory.getLogger(ClientService.class);
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    /**
     * Создание нового клиента
     *
     * @return созданный клиент
     */
    @Override
    public Client create(Client client) {
        logger.info("createClient method has been invoked");
        return clientRepository.save(client);
    }

    /**
     * удаление клиента кота по id
     * Используется метод репозитория {@link ClientRepository#deleteById(Object)}
     *
     * @param id идентификатор клиента
     */
    @Override
    public Client delete(Long id) {
        logger.info("deleteClient method has been invoked");
        Optional<Client> client = clientRepository.findById(id);
        if (client.isPresent()) {
            clientRepository.deleteById(id);
            return client.get();
        } else {
            logger.error("There is no client with id: " + id);
            throw new EntityNotFoundException("Клиента с " + id + "id не существует");
        }
    }


    @Override
    public Client get(Long id) {
        logger.info("getClient method has been invoked");
        Optional<Client> client = clientRepository.findById(id);
        if (client.isPresent()) {
            return client.get();
        } else {
            logger.error("There is no client with id: " + id);
            throw new EntityNotFoundException("Клиента с " + id + "id не существует");
        }
    }

    /**
     * получение списка клиентов котов
     * Используется метод репозитория {@link ClientRepository#findAll()}
     *
     * @return список клиентов
     */
    @Override
    public Collection<Client> findAll() {
        logger.info("findAllClient method has been invoked");
        return clientRepository.findAll();
    }

    @Override
    public Client findByUserName(String userName) {
        logger.info("findByStatus method has been invoked");
        String nullName = "zero";
        return clientRepository.findAll().stream()
                .filter(c -> c.getName().equals(userName))
                .findFirst().orElse(new Client(nullName, 66, "666", "4fff"));
    }

    @Override
    @Transactional
    public Client updateWithClientStatus(Client client, ClientStatus clientStatus) {
        logger.info("createClient method has been invoked");
        clientStatus.setClient(client);
        client.setClientStatus(clientStatus);
        return clientRepository.save(client);
    }
}
