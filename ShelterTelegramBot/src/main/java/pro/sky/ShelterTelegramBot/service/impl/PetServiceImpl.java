package pro.sky.ShelterTelegramBot.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.ShelterTelegramBot.constants.ShelterType;
import pro.sky.ShelterTelegramBot.model.*;
import pro.sky.ShelterTelegramBot.repository.PetRepository;
import pro.sky.ShelterTelegramBot.service.PetService;
import pro.sky.ShelterTelegramBot.service.ShelterService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetServiceImpl implements PetService {
    private Logger logger = LoggerFactory.getLogger(PetService.class);
    private final PetRepository petRepository;
    private final ShelterService shelterService;
    private Long count = 1L;

    public PetServiceImpl(PetRepository petRepository, ShelterService shelterService) {
        this.petRepository = petRepository;
        this.shelterService = shelterService;
    }

    /**
     * Создание нового питомца
     *
     * @return созданное питомец
     */
    @Override
    public Pet create(Pet pet) {
        logger.info("createPet method has been invoked");
        Optional<Pet> petCheck = Optional.ofNullable(findPetByName(pet.getName()));
        if (petCheck.isEmpty()) {
            return petRepository.save(pet);
        } else {
            logger.info("Pet already create with name: " + petCheck.get().getName());
            return petCheck.get();
        }
    }

    @Override
    public Pet update(Pet pet) {
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

    public Collection<Pet> getAll() {
        return petRepository.findAll();
    }

    @Override
    public Pet setPetOwner(Pet pet, Client client) {
        logger.info("setPetOwner method has been invoked");
        pet.setClient(client);
        return petRepository.save(pet);
    }

    /**
     * Создание Collection питомца по petType(кошки,собаки)
     * Удаление питомцев из списка, взятых на усыновление
     *
     * @return Collection питомец
     */
    @Override
    @Lazy
    public Collection<Pet> findAllByPetType(String petType) {
        logger.info("findCatsByPetType method has been invoked");
        Collection<Pet> petCollection = petRepository.findAllByPetType(petType);
        Collection<Pet> pets = petCollection.stream()
                .filter(p -> !p.clientPresent(p)).toList();
        return pets;
    }

    @Override
    public Optional<Pet> findPetByClient(Client client) {
        Optional<Pet> pet = petRepository.findPetsByClient(client).stream().findFirst();
        return pet;
    }

    /**
     * Создание связи pet и List reports
     *
     * @return питомец связанный с List reports
     */
    @Override
    @Transactional
    public Pet createWithReports(Pet pet) {
        logger.info("createWithReport method has been invoked");
        List<Report> reports = new ArrayList<>();
        pet.setReport(reports);
        return petRepository.save(pet);
    }

    /**
     * Создание связи pet и report
     *
     * @return питомец связанный с report
     */
    @Override
    @Transactional
    public Pet updateWithReport(Pet pet, Report report) {
        logger.info("updateWithReport method has been invoked");
        List<Report> reports = pet.getReport();
        reports.add(report);
        report.setPet(pet);
        pet.setReport(reports);
        return petRepository.save(pet);
    }

    /**
     * Создание связи pet и shelter
     *
     * @return питомец связанный с shelter
     */
    @Override
    @Transactional
    public Pet updateWithShelter(Shelter shelter, Pet pet) {
        logger.info("updateWithShelter method has been invoked");
        List<Pet> pets = shelter.getPets();
        pets.add(pet);
        pet.setShelter(shelter);
        return petRepository.save(pet);
    }

    @Override
    @Transactional
    public Pet updateWithAttachment(Pet pet, Attachment attachment) {
        logger.info("updateWithAttachment method has been invoked");
        pet.setAttachment(attachment);
        attachment.setPet(pet);
        return petRepository.save(pet);
    }

    @Override
    @Transactional
    public Pet updateWithReportStatus(Pet pet, ReportStatus reportStatus) {
        logger.info("updateWithReportStatus method has been invoked");
        reportStatus.setPet(pet);
        pet.setReportStatus(reportStatus);
        return petRepository.save(pet);
    }

    @Override
    @Transactional
    public Pet updateWithReportBreach(Pet pet, ReportBreach reportBreach) {
        logger.info("updateWithReportBreach method has been invoked");
        reportBreach.setPet(pet);
        pet.setReportBreach(reportBreach);
        return petRepository.save(pet);
    }

    @Override
    public Pet findPetByName(String name) {
        return petRepository.findPetByName(name);
    }

}
