package pro.sky.ShelterTelegramBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.ShelterTelegramBot.model.Client;
import pro.sky.ShelterTelegramBot.model.ClientStatus;
import pro.sky.ShelterTelegramBot.model.Volunteer;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findVolunteerByName(String Name);
    Client findVolunteerByChatId(Long chatId);

}
