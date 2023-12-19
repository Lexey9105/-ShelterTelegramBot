package pro.sky.ShelterTelegramBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.ShelterTelegramBot.model.Request;

public interface RequestRepository extends JpaRepository<Request, Long> {

    Request findRequestByPetName(String petName);
}

