package pro.sky.ShelterTelegramBot.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.ShelterTelegramBot.listener.TelegramBotUpdatesListener;
import pro.sky.ShelterTelegramBot.model.*;
import pro.sky.ShelterTelegramBot.service.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static pro.sky.ShelterTelegramBot.constants.Constants.*;

@Service
public class ControlServiceImpl implements ControlService {
    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final ClientService clientService;
    private final ClientStatusService clientStatusService;
    private final ReportService reportService;
    private final ReportStatusService reportStatusService;
    private final ReportBreachService reportBreachService;

    public ControlServiceImpl(ClientService clientService, ClientStatusService clientStatusService,
                              ReportService reportService, ReportStatusService reportStatusService,
                              ReportBreachService reportBreachService) {
        this.clientService = clientService;
        this.clientStatusService = clientStatusService;
        this.reportService = reportService;
        this.reportStatusService = reportStatusService;
        this.reportBreachService = reportBreachService;
    }

    @Override
    @Transactional
    public Report welcome(Client client) {
        clientStatusService.updateStatusWithReport(client.getChatId());
        ReportStatus reportStatus = new ReportStatus("", "");
        reportStatus.setTotalDayReport(30);
        ReportBreach reportBreach = new ReportBreach();
        Report report = new Report(LocalDateTime.now().plusDays(1).format(FORMATTER), 1, Report_Status);
        reportService.create(report);
        reportStatusService.create(reportStatus);
        reportBreachService.create(reportBreach);
        clientService.updateWithReportStatus(client, reportStatus);
        clientService.updateWithReportBreach(client, reportBreach);
        clientService.createWithReport(client);
        reportService.updateWithClient(client,report);
        return report;
    }

    public void createReport(Report report){
        clientStatusService.updateStatusWithReport(report.getClient().getChatId());
        Report report2 = new Report(LocalDateTime.now().plusDays(1).format(FORMATTER), report.getDayReport()+1, Report_Status);
        clientService.createWithReport(report.getClient());
        reportService.create(report2);
        reportService.updateWithClient(report.getClient(), report2);
    }


    @Override
    public void hand(Client client, String text) {
        reportService.findReportsByClient(client);
        int index=reportService.findReportsByClient(client).size()-1;
        Report report=reportService.findReportsByClient(client).get(index);
        report.setTextReport(text);
        report.setStatus(ReadyToShip);
        reportService.update(report);
    }

    @Override
    public void accept(Client client) {
        List<Report>reports=reportService.findReportsByClient(client);
        ReportBreach reportBreach = client.getReportBreach();
        ReportStatus reportStatus=client.getReportStatus();
        int index=reports.size()-1;
        Report report=reportService.findReportByStatus(ReadyToShip);
       if(reportBreach.getRepoAttachDay2()>0||reportBreach.getRepoAttachDay1()>0) {
           reportStatus.setAvailableReportDays(reportStatus.getAvailableReportDays() + report.getDayReport());
           reportBreach.setRepoAttachDay1(0);
           reportBreach.setRepoAttachDay2(0);
           report.setStatus(Passed);
           reportBreachService.update(reportBreach);
           reportStatusService.update(reportStatus);
           reportService.update(report);
           Report report2 = new Report(LocalDateTime.now().plusDays(1).format(FORMATTER), report.getDayReport() + 1, Report_Status);
           createReport(report);
       }else {
            reportStatus.setAvailableReportDays(reportStatus.getAvailableReportDays()+report.getDayReport());
            report.setStatus(Passed);
            reportStatusService.update(reportStatus);
            reportService.update(report);
            createReport(report);
        }
    }

    @Override
    public String refusal(Report report) {
        logger.info("method refusal is invoke");
        Client client= report.getClient();
        List<Report>reports=reportService.findReportsByClient(client);
        String answer = "";
        int index=reportService.findReportsByClient(client).size()-1;

        ReportStatus reportStatus = client.getReportStatus();
        ReportBreach reportBreach = client.getReportBreach();
        reportBreach.setTotalPassesAttach(reportBreach.getTotalPassesAttach() + 1);
        if (reports.size()>2){
            Report report2 = reportService.findReportsByClient(client).get(index - 1);
            Report report3 = reportService.findReportsByClient(client).get(index - 2);
            if (reportBreach.getRepoAttachDay1() > 0 && report2.getStatus().equals(NotPassed)) {
                reportStatus.setMissingReportDays(reportStatus.getMissingReportDays() + Integer.toString(report.getDayReport()));
                reportBreach.setRepoAttachDay2(1);
                report.setStatus(NotPassed);
                reportBreachService.update(reportBreach);
                reportStatusService.update(reportStatus);
                reportService.update(report);
                createReport(report);
                return "Вы не сдали вовремя отчет или прислали нечеткое фото питомца.Напоминаем если три дня подряд отчет будет отсуствовать, будет введен испытательный срок";
            }
            else if (reportBreach.getRepoAttachDay2() > 0 && report3.getStatus().equals(NotPassed) && reportStatus.getTotalDayReport() == 30) {
                reportStatus.setMissingReportDays(reportStatus.getMissingReportDays() + Integer.toString(report.getDayReport()));
                reportBreach.setRepoAttachDay1(0);
                reportBreach.setRepoAttachDay2(0);
                reportStatus.setTotalDayReport(reportStatus.getTotalDayReport() + 15);
                report.setStatus(NotPassed);
                reportBreachService.update(reportBreach);
                reportStatusService.update(reportStatus);
                reportService.update(report);
                createReport(report);
                clientStatusService.updateStatus(client.getChatId(),Probation_Period_Status);
                return "Вы переведены на испытательный срок";
            } else if (reportBreach.getRepoAttachDay2() > 0 && report3.getStatus().equals(NotPassed) && reportStatus.getTotalDayReport() == 45) {
                reportStatus.setMissingReportDays(reportStatus.getMissingReportDays() + Integer.toString(report.getDayReport()));
                reportBreach.setRepoAttachDay3(1);
                report.setStatus(NotPassed);
                reportBreachService.update(reportBreach);
                reportStatusService.update(reportStatus);
                reportService.update(report);
                clientStatusService.updateStatus(client.getChatId(),Failed_Status);
                return "Вы провалили проверку. К вам направлен волонтер для возвращения питомца на территорию приюта";
            } else if (reportBreach.getTotalPassesAttach() == 15 && reportStatus.getTotalDayReport() == 30) {
                reportStatus.setMissingReportDays(reportStatus.getMissingReportDays() + Integer.toString(report.getDayReport()));
                reportBreach.setRepoAttachDay1(0);
                reportBreach.setRepoAttachDay2(0);
                reportStatus.setTotalDayReport(reportStatus.getTotalDayReport() + 15);
                report.setStatus(NotPassed);
                reportBreachService.update(reportBreach);
                reportStatusService.update(reportStatus);
                reportService.update(report);
                createReport(report);
                clientStatusService.updateStatus(client.getChatId(),Probation_Period_Status);
                return "Вы переведены на испытательный срок";
            } else if (reportBreach.getTotalPassesAttach() == 15 && reportStatus.getTotalDayReport() == 45) {
                reportStatus.setMissingReportDays(reportStatus.getMissingReportDays() + Integer.toString(report.getDayReport()));
                reportBreach.setRepoAttachDay3(1);
                report.setStatus(NotPassed);
                reportBreachService.update(reportBreach);
                reportStatusService.update(reportStatus);
                reportService.update(report);
                clientStatusService.updateStatus(client.getChatId(),Failed_Status);
                return "Вы провалили проверку. К вам направлен волонтер для возвращения питомца на территорию приюта";
            }

        }
        if (reportBreach.getRepoAttachDay1() == 0) {
            reportStatus.setMissingReportDays(reportStatus.getMissingReportDays() + Integer.toString(report.getDayReport()));
            reportBreach.setRepoAttachDay1(1);
            report.setStatus(NotPassed);
            reportBreachService.update(reportBreach);
            reportStatusService.update(reportStatus);
            reportService.update(report);
            createReport(report);
            return "Вы не сдали вовремя отчет или прислали нечеткое фото питомца.Напоминаем если три дня подряд отчет будет отсуствовать, будет введен испытательный срок";
        }

        return answer;
    }

    @Override
    public List<Report> load() {
        logger.info("method load() is invoke");
        List<Report>reports=new ArrayList<>();
        reportService.getAll().forEach(report -> {
            //if (report.getLocalDateTime().equals(LocalDateTime.now().format(FORMATTER))) {
                if (report.getStatus().equals(ReadyToShip)) {
                    reports.add(report);
                }

        });
    return reports;}

    @Override
    public void reject() {
        logger.info("method reject() is invoke");
        reportService.getAll().forEach(report -> {
            if (report.getLocalDateTime().equals(LocalDateTime.now().plusDays(1).format(FORMATTER))) {
                if (report.getStatus().equals(Report_Status)) {
                    refusal(report);
                }
            }
        });
    }
}
