package pro.sky.ShelterTelegramBot.service;

import pro.sky.ShelterTelegramBot.model.Client;
import pro.sky.ShelterTelegramBot.model.Volunteer;

import java.util.Collection;

public interface VolunteerService {
    Volunteer create(Volunteer volunteer);

    Volunteer  delete(Long id);

    Volunteer get(Long id);

    Collection<Volunteer> findAll();
    Volunteer findByStatus(int startStatus, int finalStatus);
    Volunteer closeStatus(String userName);
    Long getCount();
    Volunteer findByUserName(String userName);
}
