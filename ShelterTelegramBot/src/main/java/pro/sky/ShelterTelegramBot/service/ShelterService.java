package pro.sky.ShelterTelegramBot.service;

import org.springframework.transaction.annotation.Transactional;
import pro.sky.ShelterTelegramBot.model.*;

import java.util.Collection;

public interface ShelterService {

    Shelter create(Shelter shelter);

    Shelter delete(Long id);

    Shelter get(Long id);

    int getCount();

    Collection<Shelter> findAll();

    Shelter findByName(String Name);

    Shelter createWithPets(Shelter shelter);

    Shelter updateWithPet(Shelter shelter, Pet pet);
}
