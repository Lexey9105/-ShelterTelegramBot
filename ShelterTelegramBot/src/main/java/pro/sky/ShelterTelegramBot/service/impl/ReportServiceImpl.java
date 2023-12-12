package pro.sky.ShelterTelegramBot.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.ShelterTelegramBot.model.Attachment;
import pro.sky.ShelterTelegramBot.model.Client;
import pro.sky.ShelterTelegramBot.model.Report;
import pro.sky.ShelterTelegramBot.repository.ReportRepository;
import pro.sky.ShelterTelegramBot.service.ClientService;
import pro.sky.ShelterTelegramBot.service.ReportService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
@Service
public class ReportServiceImpl implements ReportService {

    private Logger logger = LoggerFactory.getLogger(ReportService.class);

    private final ReportRepository reportRepository;


    public ReportServiceImpl(ReportRepository reportRepository){
        this.reportRepository=reportRepository;
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
        Optional<Report> report=reportRepository.findById(id);
        if(report.isPresent()){
            reportRepository.delete(report.get());
        }else {
            logger.error("There is no report with id: " + id);
            throw new EntityNotFoundException("Репорта с " + id + "id не существует");
        }
        return report.get();
    }

    @Override
    public Report get(Long id) {
        Optional<Report> report=reportRepository.findById(id);
        if(report.isPresent()){
            return report.get();
        }else {
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
    @Transactional
    public Report updateWithClient(Client client, Report report) {
        logger.info("updateWithReport method has been invoked");
        client.getReport().add(report);
        report.setClient(client);

        return reportRepository.save(report);
    }

    @Override
    @Transactional
    public Report updateWithReport(Attachment attachment, Report report) {
        logger.info("updateWithReport method has been invoked");
        report.setAttachment(attachment);
        attachment.setReport(report);

        return reportRepository.save(report);
    }
}
