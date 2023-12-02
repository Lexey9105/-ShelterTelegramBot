package pro.sky.ShelterTelegramBot.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.ShelterTelegramBot.model.Client;
import pro.sky.ShelterTelegramBot.model.Volunteer;
import pro.sky.ShelterTelegramBot.repository.ClientRepository;
import pro.sky.ShelterTelegramBot.repository.VolunteerRepository;
import pro.sky.ShelterTelegramBot.service.ClientService;
import pro.sky.ShelterTelegramBot.service.VolunteerService;

import java.util.Collection;
import java.util.Optional;

@Service
public class VolunteerServiceImpl implements VolunteerService {

    private Logger logger = LoggerFactory.getLogger(VolunteerService.class);
    private final VolunteerRepository volunteerRepository;
    private Long count = 1L;

    public VolunteerServiceImpl(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }


    /**
     * Создание нового клиента
     *
     * @return созданный клиент
     */
    @Override
    public Volunteer create(Volunteer volunteer) {
        logger.info("createClient method has been invoked");
        count++;
        return volunteerRepository.save(volunteer);
    }

    public Long getCount() {
        return count;
    }

    /**
     * удаление клиента кота по id
     * Используется метод репозитория {@link ClientRepository#deleteById(Object)}
     *
     * @param id идентификатор клиента
     */
    @Override
    public Volunteer delete(Long id) {
        logger.info("deleteClient method has been invoked");
        Optional<Volunteer> volunteer = volunteerRepository.findById(id);
        if (volunteer.isPresent()) {
            volunteerRepository.deleteById(id);
            return volunteer.get();
        } else {
            logger.error("There is no client with id: " + id);
            throw new EntityNotFoundException("Клиента с " + id + "id не существует");
        }
    }


    @Override
    public Volunteer get(Long id) {
        logger.info("deleteClient method has been invoked");
        Optional<Volunteer> volunteer = volunteerRepository.findById(id);
        if (volunteer.isPresent()) {
            return volunteer.get();
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
    public Collection<Volunteer> findAll() {
        logger.info("findAllClient method has been invoked");
        return volunteerRepository.findAll();
    }

    @Override
    public Volunteer findByStatus(int startStatus, int finalStatus) {
        logger.info("findByStatus method has been invoked");
        Volunteer volunteer = volunteerRepository.findVolunteersByStatusBetween(0, 2).stream().findFirst().get();
        volunteer.setStatus(volunteer.getStatus() + 1);
        return volunteer;
    }

    @Override
    public Volunteer closeStatus(String userName) {
        logger.info("findByStatus method has been invoked");
        Volunteer volunteer = volunteerRepository.findVolunteerByUserName(userName);
        volunteer.setStatus(volunteer.getStatus() - 1);
        return volunteer;
    }

    @Override
    public Volunteer findByUserName(String userName) {
        logger.info("findByStatus method has been invoked");
        String nullName = "zero";
        return volunteerRepository.findAll().stream()
                .filter(v -> v.getUserName().equals(userName))
                .findFirst().orElse(new Volunteer(getCount(), nullName, 0));
    }

}
