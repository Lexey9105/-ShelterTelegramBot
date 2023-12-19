package pro.sky.ShelterTelegramBot.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.ShelterTelegramBot.model.Attachment;
import pro.sky.ShelterTelegramBot.model.Client;
import pro.sky.ShelterTelegramBot.model.Pet;
import pro.sky.ShelterTelegramBot.model.Report;
import pro.sky.ShelterTelegramBot.repository.ReportRepository;
import pro.sky.ShelterTelegramBot.service.ClientService;
import pro.sky.ShelterTelegramBot.service.ReportService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Сервис управления (создание, получение,удаление,создание связи с другими сущностями, поиск по имени) Report
 */
@Service
public class ReportServiceImpl implements ReportService {

    private Logger logger = LoggerFactory.getLogger(ReportService.class);

    private final ReportRepository reportRepository;


    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }


    @Override
    public Report create(Report report) {
        logger.info("createReport method has been invoked");
        return reportRepository.save(report);
    }

    @Override
    public Report update(Report report) {
        logger.info("createReport method has been invoked");
        return reportRepository.save(report);
    }

    @Override
    public Report delete(Long id) {
        Optional<Report> report = reportRepository.findById(id);
        if (report.isPresent()) {
            reportRepository.delete(report.get());
        } else {
            logger.error("There is no report with id: " + id);
            throw new EntityNotFoundException("Репорта с " + id + "id не существует");
        }
        return report.get();
    }

    @Override
    public Report get(Long id) {
        Optional<Report> report = reportRepository.findById(id);
        if (report.isPresent()) {
            return report.get();
        } else {
            logger.error("There is no report with id: " + id);
            throw new EntityNotFoundException("Репорта с " + id + "id не существует");
        }

    }

    @Override
    public Collection<Report> getAll() {
        return reportRepository.findAll();
    }

    @Override
    public List<Report> findReportsByClient(Client client) {
        return reportRepository.findReportsByClient(client);
    }

    @Override
    public Report findReportByStatus(String status) {
        return reportRepository.findReportByStatus(status);
    }

    public Report findReportByName(String name) {
        return reportRepository.findReportByName(name);
    }

    @Override
    @Transactional
    public Report updateWithClient(Client client, Report report) {
        logger.info("updateWithReport method has been invoked");
        client.getReport().add(report);
        report.setClient(client);

        return reportRepository.save(report);
    }

    @Override
    @Transactional
    public Report updateWithAttachment(Attachment attachment, Report report) {
        logger.info("updateWithReport method has been invoked");
        report.setAttachment(attachment);
        attachment.setReport(report);

        return reportRepository.save(report);
    }


    @Override
    @Transactional
    public Report updateWithPet(Report report, Pet pet) {
        logger.info("updateWithReport method has been invoked");
        List<Report> reports = pet.getReport();
        reports.add(report);
        report.setPet(pet);
        return reportRepository.save(report);
    }
}
