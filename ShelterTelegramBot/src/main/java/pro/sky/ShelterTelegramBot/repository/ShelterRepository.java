package pro.sky.ShelterTelegramBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.ShelterTelegramBot.model.Client;
import pro.sky.ShelterTelegramBot.model.Shelter;

import java.util.Collection;

public interface ShelterRepository extends JpaRepository<Shelter, Long> {
    Shelter findByName(String Name);
}
