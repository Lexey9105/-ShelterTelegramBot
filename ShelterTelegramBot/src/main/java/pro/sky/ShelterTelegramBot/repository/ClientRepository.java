package pro.sky.ShelterTelegramBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.ShelterTelegramBot.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}
