package pro.sky.ShelterTelegramBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.ShelterTelegramBot.model.Client;
import pro.sky.ShelterTelegramBot.model.Pet;
import pro.sky.ShelterTelegramBot.model.Shelter;
import pro.sky.ShelterTelegramBot.model.Volunteer;

import java.util.Collection;

public interface PetRepository extends JpaRepository<Pet, Long> {

    Collection<Pet> findPetsByClient(Client client);

    Collection<Pet> findAllByPetType(String petType);

    Pet findPetByName(String name);
}
