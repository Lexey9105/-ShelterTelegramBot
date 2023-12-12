package pro.sky.ShelterTelegramBot.service;

import pro.sky.ShelterTelegramBot.model.Report;
import pro.sky.ShelterTelegramBot.model.ReportStatus;

import java.util.Collection;

public interface ReportStatusService {
    ReportStatus create (ReportStatus reportStatus);
    ReportStatus update(ReportStatus reportStatus);
    ReportStatus delete (Long id);
    ReportStatus get(Long id);
    Collection<ReportStatus> getAll();
}
