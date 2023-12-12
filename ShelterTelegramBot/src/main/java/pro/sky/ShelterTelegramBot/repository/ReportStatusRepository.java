package pro.sky.ShelterTelegramBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.ShelterTelegramBot.model.Report;
import pro.sky.ShelterTelegramBot.model.ReportStatus;

public interface ReportStatusRepository extends JpaRepository<ReportStatus, Long> {
}
