package pro.sky.ShelterTelegramBot.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.ShelterTelegramBot.model.Client;
import pro.sky.ShelterTelegramBot.model.ClientStatus;
import pro.sky.ShelterTelegramBot.repository.ClientStatusRepository;
import pro.sky.ShelterTelegramBot.service.ClientService;
import pro.sky.ShelterTelegramBot.service.ClientStatusService;
import pro.sky.ShelterTelegramBot.service.VolunteerService;

import java.util.Collection;
import java.util.Optional;
@Service
public class ClientStatusServiceImpl implements ClientStatusService {

    private final ClientService clientService;
    private final ClientStatusRepository clientStatusRepository;
    private Logger logger = LoggerFactory.getLogger(ClientStatusService.class);

    public ClientStatusServiceImpl (ClientService clientService,ClientStatusRepository clientStatusRepository){
        this.clientService=clientService;
        this.clientStatusRepository=clientStatusRepository;
    }


    @Override
    public ClientStatus create( ClientStatus clientStatus) {
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

   // @Override
   // public int clickCat(int click) {
       // return ;
   // }

   // @Override
    //public int clickDog(int click) {
       // return 0;
    //}

    //@Override
    //public String interest(ClientStatus clientStatus) {
       // return null;
    //}
}
