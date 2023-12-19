package pro.sky.ShelterTelegramBot.service;

import pro.sky.ShelterTelegramBot.model.Request;

import java.util.Collection;

public interface RequestRepoService {

    Request create(Request request);

    Request delete(Long id);

    Request get(Long id);

    Request findRequestByPetName(String petName);

    Collection<Request> getAll();
}
