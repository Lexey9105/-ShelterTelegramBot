package pro.sky.ShelterTelegramBot.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.ShelterTelegramBot.model.Client;
import pro.sky.ShelterTelegramBot.model.Pet;
import pro.sky.ShelterTelegramBot.model.Shelter;
import pro.sky.ShelterTelegramBot.repository.PetRepository;
import pro.sky.ShelterTelegramBot.service.PetService;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class PetServiceImpl implements PetService {
    private Logger logger = LoggerFactory.getLogger(PetService.class);
    private final PetRepository petRepository;
    private Long count = 1L;

    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    /**
     * Создание нового питомца
     *
     * @return созданное питомец
     */
    @Override
    public Pet create(Pet pet) {
        logger.info("createPet method has been invoked");
        return petRepository.save(pet);
    }

    /**
     * удаление питомца по id
     * Используется метод репозитория {@link PetRepository#deleteById(Object)}
     *
     * @param id идентификатор питомца
     */
    @Override
    public Pet delete(Long id) {
        logger.info("deletePet method has been invoked");
        Optional<Pet> pet = petRepository.findById(id);
        if (pet.isPresent()) {
            petRepository.deleteById(id);
            return pet.get();
        } else {
            logger.error("There is no pet with id: " + id);
            throw new EntityNotFoundException("Питомец с " + id + "id не существует");
        }
    }


    @Override
    public Pet get(Long id) {
        logger.info("getPet method has been invoked");
        Optional<Pet> pet = petRepository.findById(id);
        if (pet.isPresent()) {
            return pet.get();
        } else {
            logger.error("There is no pet with id: " + id);
            throw new EntityNotFoundException("Питомец с " + id + "id не существует");
        }
    }

    @Override
    public Pet setPetOwner(Pet pet, Client client) {
        logger.info("setPetOwner method has been invoked");
        pet.setClient(client);
        return petRepository.save(pet);
    }

    @Override
    public Collection<Pet> findAllFromShelter(Shelter shelter) {
        logger.info("findByStatus method has been invoked");
        return petRepository.findAll().stream()
                .filter(c -> c.getShelter().equals(shelter))
                .collect(Collectors.toList());
    }


}
