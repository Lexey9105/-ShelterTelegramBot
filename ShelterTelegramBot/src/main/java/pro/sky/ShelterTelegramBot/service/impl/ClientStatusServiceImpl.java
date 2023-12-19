package pro.sky.ShelterTelegramBot.service.impl;


import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.ShelterTelegramBot.model.Client;
import pro.sky.ShelterTelegramBot.model.ClientStatus;
import pro.sky.ShelterTelegramBot.model.UserStatement;
import pro.sky.ShelterTelegramBot.repository.ClientStatusRepository;
import pro.sky.ShelterTelegramBot.service.ClientService;
import pro.sky.ShelterTelegramBot.service.ClientStatusService;
import pro.sky.ShelterTelegramBot.service.VolunteerService;

import java.util.Collection;
import java.util.Optional;

import static pro.sky.ShelterTelegramBot.constants.Constants.*;

/**
 * Класс для отслеживания статуса пользователя начиная с первого взаимодействия с ботом
 * обработка статусов связаных с отчетносьть см ControlServiceImpl методы accept b refusal
 */
@Service
public class ClientStatusServiceImpl implements ClientStatusService {

    private final ClientService clientService;
    private final ClientStatusRepository clientStatusRepository;
    private Logger logger = LoggerFactory.getLogger(ClientStatusService.class);

    public ClientStatusServiceImpl(ClientService clientService, ClientStatusRepository clientStatusRepository) {
        this.clientService = clientService;
        this.clientStatusRepository = clientStatusRepository;
    }


    @Override
    public ClientStatus create(Long chatId) {

        Optional<ClientStatus> clientStatus1 = Optional.ofNullable(findClient(chatId));
        if (clientStatus1.isPresent()) {
            logger.info("Client already create with id: " + chatId);
            return clientStatus1.get();
        } else {
            ClientStatus clientStatus = new ClientStatus(chatId, Guest_Status, 0, 0);
            return clientStatusRepository.save(clientStatus);
        }

    }

    @Override
    public ClientStatus updateStatus(Long chatId, String status) {
        ClientStatus clientStatus = clientStatusRepository.findClientStatusByChatId(chatId);
        clientStatus.setClientStatus(status);
        return clientStatusRepository.save(clientStatus);
    }

    @Override
    public ClientStatus updateStatusWithReport(Long chatId) {
        ClientStatus clientStatus = clientStatusRepository.findClientStatusByChatId(chatId);
        clientStatus.setDayReport(clientStatus.getDayReport() + 1);
        return clientStatusRepository.save(clientStatus);
    }

    @Override
    public ClientStatus delete(Long id) {
        Optional<ClientStatus> clientStatus = clientStatusRepository.findById(id);
        if (clientStatus.isPresent()) {
            clientStatusRepository.deleteById(id);
            return clientStatus.get();
        } else {
            logger.error("There is no client profile with id: " + id);
            throw new EntityNotFoundException("Клиента с профайлом" + id + "id не существует");
        }
    }

    @Override
    public ClientStatus get(Long id) {
        Optional<ClientStatus> clientStatus = clientStatusRepository.findById(id);
        if (clientStatus.isPresent()) {
            return clientStatus.get();
        } else {
            logger.error("There is no client profile with id: " + id);
            throw new EntityNotFoundException("Клиента с профайлом" + id + "id не существует");
        }
    }

    @Override
    public Collection<ClientStatus> findAll() {
        return clientStatusRepository.findAll();
    }

    @Override
    public ClientStatus findClient(Long chatId) {
        ClientStatus clientStatus = clientStatusRepository.findClientStatusByChatId(chatId);
        return clientStatus;
    }

    @Override
    public int clickCat(Long id, int click) {
        ClientStatus clientStatus = clientStatusRepository.findClientStatusByChatId(id);
        clientStatus.setClickCounterCat(clientStatus.getClickCounterCat() + click);
        clientStatusRepository.save(clientStatus);
        return clientStatus.getClickCounterCat();
    }

    @Override
    public int clickDog(Long id, int click) {
        ClientStatus clientStatus = clientStatusRepository.findClientStatusByChatId(id);
        clientStatus.setClickCounterDog(clientStatus.getClickCounterDog() + click);
        clientStatusRepository.save(clientStatus);
        return clientStatus.getClickCounterDog();
    }

    @Override
    @Transactional
    public ClientStatus updateWithUserStatement(ClientStatus clientStatus, UserStatement userStatement) {
        logger.info("updateWithClientStatus method has been invoked");
        userStatement.setClientStatus(clientStatus);
        clientStatus.setUserStatement(userStatement);
        return clientStatusRepository.save(clientStatus);
    }

}
