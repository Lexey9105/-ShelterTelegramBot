package pro.sky.ShelterTelegramBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.ShelterTelegramBot.model.Client;

public interface ClientRepository extends JpaRepository<Client,Long> {
    @Query(value = "SELECT Count(full_name) AS total FROM  client",nativeQuery = true)
    Object getTotalClientsById();
}
