package pro.sky.ShelterTelegramBot.service;

import pro.sky.ShelterTelegramBot.model.Client;
import pro.sky.ShelterTelegramBot.model.Report;

import java.util.List;

public interface ControlService {

    //  Report welcome(Client client);
    void createReport(Report report);
    void hand(Report report, String text);

    void check(Report report);

    void accept(Client client, Report report);

    String refusal(Report report);

    List<Report> load();

    void reject();
}
