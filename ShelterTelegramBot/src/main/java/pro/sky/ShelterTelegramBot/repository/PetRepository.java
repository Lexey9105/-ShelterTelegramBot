package pro.sky.ShelterTelegramBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.ShelterTelegramBot.model.Client;
import pro.sky.ShelterTelegramBot.model.Pet;
import pro.sky.ShelterTelegramBot.model.Shelter;
import pro.sky.ShelterTelegramBot.model.Volunteer;

public interface PetRepository extends JpaRepository<Pet, Long> {
    Pet findPetsByShelter(Shelter shelter);
}
