package pro.sky.ShelterTelegramBot.service.impl;

import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.ShelterTelegramBot.listener.TelegramBotUpdatesListener;
import pro.sky.ShelterTelegramBot.model.*;
import pro.sky.ShelterTelegramBot.repository.ClientRepository;
import pro.sky.ShelterTelegramBot.service.*;
import pro.sky.ShelterTelegramBot.utils.Send;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static pro.sky.ShelterTelegramBot.constants.Constants.*;

/**
 * Сервис котроля отправки отчетов
 */
@Service
public class ControlServiceImpl implements ControlService {
    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final ClientService clientService;
    private final ClientStatusService clientStatusService;
    private final ReportService reportService;
    private final ReportStatusService reportStatusService;
    private final ReportBreachService reportBreachService;
    private final PetService petService;
    private final Send send;

    public ControlServiceImpl(ClientService clientService, ClientStatusService clientStatusService,
                              ReportService reportService, ReportStatusService reportStatusService,
                              ReportBreachService reportBreachService, PetService petService, Send send) {
        this.clientService = clientService;
        this.clientStatusService = clientStatusService;
        this.reportService = reportService;
        this.reportStatusService = reportStatusService;
        this.reportBreachService = reportBreachService;
        this.petService = petService;
        this.send = send;
    }

    // @Override
    //@Transactional
    // public Report welcome(Client client) {
    //  clientStatusService.updateStatusWithReport(client.getChatId());
    // ReportStatus reportStatus = new ReportStatus("", "");
    // reportStatus.setTotalDayReport(30);
    // ReportBreach reportBreach = new ReportBreach();
    //Report report = new Report(LocalDateTime.now().plusDays(1).format(FORMATTER), 1, Report_Status);
    //reportService.create(report);
    // reportStatusService.create(reportStatus);
    // reportBreachService.create(reportBreach);
    // clientService.updateWithReportStatus(client, reportStatus);
    // clientService.updateWithReportBreach(client, reportBreach);
    // clientService.createWithReport(client);
    // reportService.updateWithClient(client,report);
    //return report;
    //}

    /**
     * создание нового отчета после одобрения или отказа по предыдущему отчету
     */
    @Override
    public void createReport(Report report) {
        clientStatusService.updateStatusWithReport(report.getClient().getChatId());
        String[] parts = report.getName().split("_");
        Pet pet = report.getPet();
        pet.setDayInFamily(pet.getDayInFamily() + 1);
        petService.update(pet);
        String reportName = pet.getName() + "_" + pet.getDayInFamily();
        LocalDate localDateTime = LocalDate.parse(report.getLocalDateTime(), FORMATTER);
        Report report2 = new Report(reportName, localDateTime.plusDays(1).format(FORMATTER), report.getDayReport() + 1, Report_Status);
        clientService.createWithReport(report.getClient());
        reportService.create(report2);
        reportService.updateWithClient(report.getClient(), report2);
        petService.createWithReports(pet);
        petService.updateWithReport(pet, report2);
        reportService.updateWithPet(report2, pet);
    }

    /**
     * прикрепление к Report текста с отчетом о состоянии питомца
     */
    @Override
    public void hand(Report report, String text) {
        report.setTextReport(text);
        reportService.update(report);
        SendMessage sendMessage = new SendMessage(report.getClient().getChatId(), "Текстовый отчет успешно поллучен");
        send.sendMessage(sendMessage);
    }

    /**
     * проверка Report на наличие текста и фото
     * смена статуса на готов к отправке
     * для выгрузки волонтеру
     */
    @Override
    public void check(Report report) {
        Optional<Attachment> attachment = Optional.ofNullable(report.getAttachment());
        Optional<String> text = Optional.ofNullable(report.getTextReport());
        if (attachment.isPresent() && text.isPresent()) {
            report.setStatus(ReadyToShip);
            reportService.update(report);
            SendMessage sendMessage = new SendMessage(report.getClient().getChatId(), "Отчет  сформирован,ожидайте ответа от волонтера");
            send.sendMessage(sendMessage);
        } else {
            SendMessage sendMessage = new SendMessage(report.getClient().getChatId(), "Отчет не сформирован,отправьте все данные");
            send.sendMessage(sendMessage);
        }

    }

    /**
     * обработка Report после одобрения волонтером
     * обнуление счетчиков с днями несдачи подряд
     */
    @Override
    public void accept(Client client, Report report) {
        Pet pet = report.getPet();
        ReportBreach reportBreach = pet.getReportBreach();
        ReportStatus reportStatus = pet.getReportStatus();
        if (reportBreach.getRepoAttachDay2() > 0 || reportBreach.getRepoAttachDay1() > 0) {
            reportStatus.setAvailableReportDays(reportStatus.getAvailableReportDays() + report.getDayReport());
            reportBreach.setRepoAttachDay1(0);
            reportBreach.setRepoAttachDay2(0);
            report.setStatus(Passed);
            reportBreachService.update(reportBreach);
            reportStatusService.update(reportStatus);
            reportService.update(report);
            createReport(report);
            SendMessage sendMessage = new SendMessage(report.getClient().getChatId(), "Отчет  одобрен волонтером");
            send.sendMessage(sendMessage);
        } else {
            reportStatus.setAvailableReportDays(reportStatus.getAvailableReportDays() + report.getDayReport());
            report.setStatus(Passed);
            reportStatusService.update(reportStatus);
            reportService.update(report);
            createReport(report);
            SendMessage sendMessage = new SendMessage(report.getClient().getChatId(), "Отчет  одобрен волонтером");
            send.sendMessage(sendMessage);
        }
    }

    /**
     * обработка Report после отказа волонтером
     * обработка счетчиков с днями несдачи подряд
     * выдача статусов на испытательном сроке и проверка провалена
     * статусы привязаны к Client
     * если у Client статус проверка провалена по одному из питомцев на усыновлении
     * блокирока всего приложения
     */
    @Override
    public String refusal(Report report) {
        logger.info("method refusal is invoke");
        Client client = report.getClient();
        Pet pet = report.getPet();
        List<Report> reports = reportService.findReportsByClient(client);
        String answer = "";
        int index = reportService.findReportsByClient(client).size() - 1;

        ReportStatus reportStatus = pet.getReportStatus();
        ReportBreach reportBreach = pet.getReportBreach();
        reportBreach.setTotalPassesAttach(reportBreach.getTotalPassesAttach() + 1);

        if (reportBreach.getRepoAttachDay1() == 0) {
            logger.info("первый день несдачи");
            reportStatus.setMissingReportDays(reportStatus.getMissingReportDays() + Integer.toString(report.getDayReport()));
            reportBreach.setRepoAttachDay1(1);
            report.setStatus(NotPassed);
            reportBreachService.update(reportBreach);
            reportStatusService.update(reportStatus);
            reportService.update(report);
            createReport(report);
            SendMessage sendMessage = new SendMessage(report.getClient().getChatId(), "Вы не сдали вовремя отчет или прислали нечеткое фото питомца.Напоминаем если три дня подряд отчет будет отсуствовать, будет введен испытательный срок");
            send.sendMessage(sendMessage);
            return "Вы не сдали вовремя отчет или прислали нечеткое фото питомца.Напоминаем если три дня подряд отчет будет отсуствовать, будет введен испытательный срок";
        } else if (reportBreach.getRepoAttachDay1() > 0 && report.getStatus().equals(Report_Status) && reportBreach.getRepoAttachDay2() == 0) {
            logger.info("второй день несдачи");
            reportStatus.setMissingReportDays(reportStatus.getMissingReportDays() + Integer.toString(report.getDayReport()));
            reportBreach.setRepoAttachDay2(1);
            report.setStatus(NotPassed);
            reportBreachService.update(reportBreach);
            reportStatusService.update(reportStatus);
            reportService.update(report);
            createReport(report);
            SendMessage sendMessage = new SendMessage(report.getClient().getChatId(), "Вы не сдали вовремя отчет или прислали нечеткое фото питомца.Напоминаем если три дня подряд отчет будет отсуствовать, будет введен испытательный срок");
            send.sendMessage(sendMessage);
            return "Вы не сдали вовремя отчет или прислали нечеткое фото питомца.Напоминаем если три дня подряд отчет будет отсуствовать, будет введен испытательный срок";
        } else if (reportBreach.getRepoAttachDay2() > 0 && report.getStatus().equals(Report_Status) && reportStatus.getTotalDayReport() == 30) {
            logger.info("третий день несдачи");
            reportStatus.setMissingReportDays(reportStatus.getMissingReportDays() + Integer.toString(report.getDayReport()));
            reportBreach.setRepoAttachDay1(0);
            reportBreach.setRepoAttachDay2(0);
            reportStatus.setTotalDayReport(reportStatus.getTotalDayReport() + 15);
            report.setStatus(NotPassed);
            reportBreachService.update(reportBreach);
            reportStatusService.update(reportStatus);
            reportService.update(report);
            createReport(report);
            clientStatusService.updateStatus(client.getChatId(), Probation_Period_Status);
            SendMessage sendMessage = new SendMessage(report.getClient().getChatId(), "Вы переведены на испытательный срок");
            send.sendMessage(sendMessage);
            return "Вы переведены на испытательный срок";
        } else if (reportBreach.getRepoAttachDay2() > 0 && report.getStatus().equals(Report_Status) && reportStatus.getTotalDayReport() == 45) {
            logger.info("третий день несдачи и испыталеьный срок");
            reportStatus.setMissingReportDays(reportStatus.getMissingReportDays() + Integer.toString(report.getDayReport()));
            reportBreach.setRepoAttachDay3(1);
            report.setStatus(NotPassed);
            reportBreachService.update(reportBreach);
            reportStatusService.update(reportStatus);
            reportService.update(report);
            clientStatusService.updateStatus(client.getChatId(), Failed_Status);
            SendMessage sendMessage = new SendMessage(report.getClient().getChatId(), "Вы провалили проверку. К вам направлен волонтер для возвращения питомца на территорию приюта");
            send.sendMessage(sendMessage);
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
            clientStatusService.updateStatus(client.getChatId(), Probation_Period_Status);
            SendMessage sendMessage = new SendMessage(report.getClient().getChatId(), "Вы переведены на испытательный срок");
            send.sendMessage(sendMessage);
            return "Вы переведены на испытательный срок";
        } else if (reportBreach.getTotalPassesAttach() == 15 && reportStatus.getTotalDayReport() == 45) {
            reportStatus.setMissingReportDays(reportStatus.getMissingReportDays() + Integer.toString(report.getDayReport()));
            reportBreach.setRepoAttachDay3(1);
            report.setStatus(NotPassed);
            reportBreachService.update(reportBreach);
            reportStatusService.update(reportStatus);
            reportService.update(report);
            clientStatusService.updateStatus(client.getChatId(), Failed_Status);
            SendMessage sendMessage = new SendMessage(report.getClient().getChatId(), "Вы провалили проверку. К вам направлен волонтер для возвращения питомца на территорию приюта");
            send.sendMessage(sendMessage);
            return "Вы провалили проверку. К вам направлен волонтер для возвращения питомца на территорию приюта";
        }

        return answer;
    }

    /**
     * формирование коллекции Report со статусом готов к отправке
     *
     * @return List reports отсортированный список
     */
    @Override
    public List<Report> load() {
        logger.info("method load() is invoke");
        List<Report> reports = new ArrayList<>();
        reportService.getAll().forEach(report -> {
            //if (report.getLocalDateTime().equals(LocalDateTime.now().format(FORMATTER))) {
            if (report.getStatus().equals(ReadyToShip)) {
                reports.add(report);
            }

        });
        return reports;
    }

    /**
     * по запросу волонтера при загрузке  отчетов со статусом говов к отправке -
     * формирование коллекции Report со статусом
     * На проверке(LocalDateTime.now()
     * - пользователи не предоставили отчет к установленному сроку сдачи)
     * для отправки на обработку к refusal
     */
    @Override
    public void reject() {
        logger.info("method reject() is invoke");
        reportService.getAll().forEach(report -> {
            if (report.getLocalDateTime().equals(LocalDateTime.now().format(FORMATTER))) {
                if (report.getStatus().equals(Report_Status)) {
                    refusal(report);
                }
            }
        });
    }
}
