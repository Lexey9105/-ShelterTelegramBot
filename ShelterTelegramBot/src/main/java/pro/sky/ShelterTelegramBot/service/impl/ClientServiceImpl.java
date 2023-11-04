package pro.sky.ShelterTelegramBot.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.ShelterTelegramBot.model.Client;
import pro.sky.ShelterTelegramBot.repository.ClientRepository;
import pro.sky.ShelterTelegramBot.service.ClientService;

import java.util.Collection;
import java.util.Optional;
@Service
public class ClientServiceImpl implements ClientService {

    Logger logger = LoggerFactory.getLogger(ClientService.class);
    private final ClientRepository clientRepository;

public ClientServiceImpl(ClientRepository clientRepository){
    this.clientRepository=clientRepository;
}


    @Override
    public Client create(Client client) {
        logger.info("createClient method has been invoked");
    return clientRepository.save(client);
    }

    @Override
    public Client delete(Long id) {
        logger.info("deleteClient method has been invoked");
        Optional<Client> client = clientRepository.findById(id);
        if(client.isPresent()){
            clientRepository.deleteById(id);
            return client.get();
        }
        else {
            logger.error("There is no client with id: " + id);
            throw new EntityNotFoundException("Клиента с " + id + "id не существует");
        }
    }

    @Override
    public Client get(Long id) {
        logger.info("deleteClient method has been invoked");
        Optional<Client> client = clientRepository.findById(id);
        if(client.isPresent()){
            return client.get();
        }
        else {
            logger.error("There is no client with id: " + id);
            throw new EntityNotFoundException("Клиента с " + id + "id не существует");
        }
    }

    @Override
    public Collection<Client> findAll() {
        logger.info("findAllClient method has been invoked");
    return clientRepository.findAll();
    }

    @Override
    public Object getClientsById() {
        logger.info("getClientsByIdClient method has been invoked");
        return clientRepository.getTotalClientsById();
    }
}
