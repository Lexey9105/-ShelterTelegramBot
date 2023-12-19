package pro.sky.ShelterTelegramBot.service.impl;

import jakarta.persistence.EntityNotFoundException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import pro.sky.ShelterTelegramBot.constants.ShelterType;
import pro.sky.ShelterTelegramBot.model.*;
import pro.sky.ShelterTelegramBot.repository.ClientRepository;
import pro.sky.ShelterTelegramBot.service.ClientService;
import pro.sky.ShelterTelegramBot.service.ClientStatusService;
import pro.sky.ShelterTelegramBot.service.ShelterService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
        logger.info("deleteClient method has been invoked");
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
    public Pet getPet(Long id) {
        logger.info("deleteClient method has been invoked");
        Optional<Client> client1 = Optional.ofNullable(findClientByChatId(id));
        if (client1.isPresent()) {
            Pet pet = client1.get().getPet().get(0);
            return pet;
        } else {
            logger.error("There is no client with id: " + id);
            throw new EntityNotFoundException("Клиента с " + id + "id не существует");
        }
    }

    public Client findClientByChatId(Long chatId) {
        return clientRepository.findClientByChatId(chatId);
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
        logger.info("updateWithClientStatus method has been invoked");
        clientStatus.setClient(client);
        client.setClientStatus(clientStatus);
        return clientRepository.save(client);
    }

    @Override
    @Transactional
    public Client createWithReport(Client client) {
        logger.info("createWithReport method has been invoked");
        List<Report> reports = new ArrayList<>();
        client.setReport(reports);
        return clientRepository.save(client);
    }

    @Override
    @Transactional
    public Client updateWithReport(Client client, Report report) {
        logger.info("updateWithReport method has been invoked");
        client.getReport().add(report);
        report.setClient(client);
        client.setReport(client.getReport());
        return clientRepository.save(client);
    }


    @Override
    @Transactional
    public Client createWithPets(Client client) {
        logger.info("createWithReport method has been invoked");
        List<Pet> Pets = new ArrayList<>();
        client.setPet(Pets);
        return clientRepository.save(client);
    }

    @Override
    @Transactional
    public Client updateWithPet(Client client, Pet pet) {
        logger.info("updateWithReport method has been invoked");
        client.getPet().add(pet);
        pet.setClient(client);
        client.setPet(client.getPet());
        return clientRepository.save(client);
    }


}
