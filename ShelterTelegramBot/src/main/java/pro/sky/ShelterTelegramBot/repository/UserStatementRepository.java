package pro.sky.ShelterTelegramBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.ShelterTelegramBot.model.UserStatement;

public interface UserStatementRepository extends JpaRepository<UserStatement, Long> {
}
