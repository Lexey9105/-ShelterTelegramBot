package pro.sky.ShelterTelegramBot.service;

import pro.sky.ShelterTelegramBot.model.Report;
import pro.sky.ShelterTelegramBot.model.ReportBreach;

import java.util.Collection;

public interface ReportBreachService {

    ReportBreach create(ReportBreach reportBreach);

    ReportBreach update(ReportBreach reportBreach);

    ReportBreach delete(Long id);

    ReportBreach get(Long id);

    Collection<ReportBreach> getAll();
}
