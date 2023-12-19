package pro.sky.ShelterTelegramBot.service;

import pro.sky.ShelterTelegramBot.model.*;

import java.util.Collection;
import java.util.Optional;

public interface PetService {
    Pet create(Pet pet);

    Pet update(Pet pet);

    Pet delete(Long id);

    Pet get(Long id);

    Collection<Pet> getAll();

    Pet setPetOwner(Pet pet, Client client);

    Collection<Pet> findAllByPetType(String petType);

    Optional<Pet> findPetByClient(Client client);

    Pet createWithReports(Pet pet);

    Pet updateWithReport(Pet pet, Report report);

    Pet updateWithShelter(Shelter shelter, Pet pet);

    Pet updateWithAttachment(Pet pet, Attachment attachment);

    Pet updateWithReportStatus(Pet pet, ReportStatus reportStatus);

    Pet updateWithReportBreach(Pet pet, ReportBreach reportBreach);

    Pet findPetByName(String name);
}
