package pro.sky.ShelterTelegramBot.service;

import pro.sky.ShelterTelegramBot.model.Client;
import pro.sky.ShelterTelegramBot.model.Pet;
import pro.sky.ShelterTelegramBot.model.Shelter;

import java.util.Collection;

public interface PetService {
    Pet create(Pet pet);

    Pet delete(Long id);

    Pet get(Long id);

    Pet setPetOwner(Pet pet, Client client);

    Collection<Pet> findAllFromShelter(Shelter shelter);
}
