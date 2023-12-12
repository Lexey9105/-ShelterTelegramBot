package pro.sky.ShelterTelegramBot.service;

import pro.sky.ShelterTelegramBot.model.Request;

import java.util.Collection;

public interface RequestRepoService {

    Request create ( Request  request);
    String delete (int id);
    Request get(int id);
    Collection<Request> getAll();
}
