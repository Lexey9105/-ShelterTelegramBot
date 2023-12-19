package pro.sky.ShelterTelegramBot.service;

import pro.sky.ShelterTelegramBot.model.Attachment;
import pro.sky.ShelterTelegramBot.model.Client;
import pro.sky.ShelterTelegramBot.model.Pet;
import pro.sky.ShelterTelegramBot.model.Report;

import java.util.Collection;
import java.util.List;

public interface ReportService {

    Report create(Report report);

    Report update(Report report);

    Report delete(Long id);

    Report get(Long id);

    Collection<Report> getAll();

    List<Report> findReportsByClient(Client client);

    Report findReportByStatus(String status);

    Report findReportByName(String name);

    Report updateWithClient(Client client, Report report);

    Report updateWithAttachment(Attachment attachment, Report report);

    Report updateWithPet(Report report, Pet pet);

}
