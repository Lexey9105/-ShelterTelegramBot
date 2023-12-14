package pro.sky.ShelterTelegramBot.service;

import pro.sky.ShelterTelegramBot.model.Client;
import pro.sky.ShelterTelegramBot.model.Report;

import java.util.List;

public interface ControlService {

    Report welcome(Client client);
    void hand(Client client,String text);
    void accept(Client client);
    String refusal(Report report);
    List<Report> load();
    void reject();
}
