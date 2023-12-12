package pro.sky.ShelterTelegramBot.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.ShelterTelegramBot.model.Report;
import pro.sky.ShelterTelegramBot.model.ReportStatus;
import pro.sky.ShelterTelegramBot.repository.ReportRepository;
import pro.sky.ShelterTelegramBot.repository.ReportStatusRepository;
import pro.sky.ShelterTelegramBot.service.ReportService;
import pro.sky.ShelterTelegramBot.service.ReportStatusService;

import java.util.Collection;
import java.util.Optional;
@Service
public class ReportStatusServiceImpl implements ReportStatusService {

    private Logger logger = LoggerFactory.getLogger(ReportService.class);

    private final ReportStatusRepository reportStatusRepository;


    public ReportStatusServiceImpl(ReportStatusRepository reportStatusRepository){
        this.reportStatusRepository=reportStatusRepository;
    }


    @Override
    public ReportStatus create(ReportStatus reportStatus) {
        logger.info("createReportStatus method has been invoked");
        return reportStatusRepository.save(reportStatus);
    }
    @Override
    public ReportStatus update(ReportStatus reportStatus) {
        logger.info("createReport method has been invoked");
        return reportStatusRepository.save(reportStatus);
    }

    @Override
    public ReportStatus delete(Long id) {
        Optional<ReportStatus> reportStatus=reportStatusRepository.findById(id);
        if(reportStatus.isPresent()){
            reportStatusRepository.delete(reportStatus.get());
        }else {
            logger.error("There is no report with id: " + id);
            throw new EntityNotFoundException("Репорта с " + id + "id не существует");
        }
        return reportStatus.get();
    }

    @Override
    public ReportStatus get(Long id) {
        Optional<ReportStatus> report=reportStatusRepository.findById(id);
        if(report.isPresent()){
            return report.get();
        }else {
            logger.error("There is no report with id: " + id);
            throw new EntityNotFoundException("Репорта с " + id + "id не существует");
        }

    }

    @Override
    public Collection<ReportStatus> getAll() {
        return reportStatusRepository.findAll();
    }
}
