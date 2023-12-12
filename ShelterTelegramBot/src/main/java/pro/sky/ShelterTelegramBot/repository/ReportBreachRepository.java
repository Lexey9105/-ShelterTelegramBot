package pro.sky.ShelterTelegramBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.ShelterTelegramBot.model.ReportBreach;
import pro.sky.ShelterTelegramBot.model.ReportStatus;

public interface ReportBreachRepository extends JpaRepository<ReportBreach, Long> {
}
