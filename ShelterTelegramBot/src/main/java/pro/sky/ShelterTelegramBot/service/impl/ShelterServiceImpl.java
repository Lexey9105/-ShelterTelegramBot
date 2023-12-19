package pro.sky.ShelterTelegramBot.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.ShelterTelegramBot.model.*;
import pro.sky.ShelterTelegramBot.repository.ClientRepository;
import pro.sky.ShelterTelegramBot.repository.ShelterRepository;
import pro.sky.ShelterTelegramBot.service.ClientService;
import pro.sky.ShelterTelegramBot.service.ShelterService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Сервис управления моделью Shelter
 * (создание, получение,удаление,создание связи с другими сущностями)
 */
@Service
public class ShelterServiceImpl implements ShelterService {

    private Logger logger = LoggerFactory.getLogger(ShelterService.class);
    private final ShelterRepository shelterRepository;

    public ShelterServiceImpl(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    private int count = 0;

    /**
     * Создание нового клиента
     *
     * @return созданный клиент
     */
    @Override
    public Shelter create(Shelter shelter) {
        logger.info("createShelter method has been invoked");
        Optional<Shelter> shelter1 = Optional.ofNullable(findByName(shelter.getName()));
        if (shelter1.isEmpty()) {
            return shelterRepository.save(shelter);
        } else {
            logger.info("Shelter already create with name: " + shelter1.get().getName());
        }
        return shelter1.get();
    }

    /**
     * удаление клиента кота по id
     * Используется метод репозитория {@link ClientRepository#deleteById(Object)}
     *
     * @param id идентификатор клиента
     */
    @Override
    public Shelter delete(Long id) {
        logger.info("deleteClient method has been invoked");
        Optional<Shelter> shelter = shelterRepository.findById(id);
        if (shelter.isPresent()) {
            count--;
            shelterRepository.deleteById(id);
            return shelter.get();
        } else {
            logger.error("There is no client with id: " + id);
            throw new EntityNotFoundException("Клиента с " + id + "id не существует");
        }
    }


    @Override
    public Shelter get(Long id) {
        logger.info("deleteClient method has been invoked");
        Optional<Shelter> shelter = shelterRepository.findById(id);
        if (shelter.isPresent()) {
            return shelter.get();
        } else {
            logger.error("There is no client with id: " + id);
            throw new EntityNotFoundException("Клиента с " + id + "id не существует");
        }
    }

    @Override
    public int getCount() {
        return count;
    }

    /**
     * получение списка клиентов котов
     * Используется метод репозитория {@link ClientRepository#findAll()}
     *
     * @return список клиентов
     */
    @Override
    public Collection<Shelter> findAll() {
        logger.info("findAllClient method has been invoked");
        return shelterRepository.findAll();
    }

    @Override
    public Shelter findByName(String Name) {
        return shelterRepository.findByName(Name);
    }

    @Override
    @Transactional
    public Shelter createWithPets(Shelter shelter) {
        logger.info("createWithPets method has been invoked");
        List<Pet> Pets = new ArrayList<>();
        shelter.setPets(Pets);
        return shelterRepository.save(shelter);
    }

    @Transactional
    public Shelter updateWithPet(Shelter shelter, Pet pet) {
        logger.info("updateWithPet method has been invoked");
        shelter.getPets().add(pet);
        pet.setShelter(shelter);
        shelter.setPets(shelter.getPets());
        return shelterRepository.save(shelter);
    }
}
