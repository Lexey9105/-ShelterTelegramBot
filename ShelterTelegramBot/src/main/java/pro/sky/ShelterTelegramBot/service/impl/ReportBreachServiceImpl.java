package pro.sky.ShelterTelegramBot.service.impl;


import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.ShelterTelegramBot.model.Report;
import pro.sky.ShelterTelegramBot.model.ReportBreach;
import pro.sky.ShelterTelegramBot.repository.ReportBreachRepository;
import pro.sky.ShelterTelegramBot.service.ReportBreachService;
import pro.sky.ShelterTelegramBot.service.ReportService;

import java.util.Collection;
import java.util.Optional;

/**
 * Сервис управления (создание, получение,удаление)
 * моделью с нарушениями при отправки отчетов
 */
@Service
public class ReportBreachServiceImpl implements ReportBreachService {
    private Logger logger = LoggerFactory.getLogger(ReportService.class);

    private ReportBreachRepository reportBreachRepository;


    public ReportBreachServiceImpl(ReportBreachRepository reportBreachRepository) {
        this.reportBreachRepository = reportBreachRepository;
    }


    @Override
    public ReportBreach create(ReportBreach reportBreach) {
        logger.info("createReportBreach method has been invoked");
        return reportBreachRepository.save(reportBreach);
    }

    @Override
    public ReportBreach update(ReportBreach reportBreach) {
        logger.info("createReport method has been invoked");
        return reportBreachRepository.save(reportBreach);
    }

    @Override
    public ReportBreach delete(Long id) {
        Optional<ReportBreach> reportBreach = reportBreachRepository.findById(id);
        if (reportBreach.isPresent()) {
            reportBreachRepository.delete(reportBreach.get());
        } else {
            logger.error("There is no report with id: " + id);
            throw new EntityNotFoundException("Репорта с " + id + "id не существует");
        }
        return reportBreach.get();
    }

    @Override
    public ReportBreach get(Long id) {
        Optional<ReportBreach> reportBreach = reportBreachRepository.findById(id);
        if (reportBreach.isPresent()) {
            return reportBreach.get();
        } else {
            logger.error("There is no report with id: " + id);
            throw new EntityNotFoundException("Репорта с " + id + "id не существует");
        }

    }

    @Override
    public Collection<ReportBreach> getAll() {
        return reportBreachRepository.findAll();
    }
}
