package pro.sky.ShelterTelegramBot.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import pro.sky.ShelterTelegramBot.model.Client;
import pro.sky.ShelterTelegramBot.model.Request;
import pro.sky.ShelterTelegramBot.repository.RequestRepository;
import pro.sky.ShelterTelegramBot.service.RequestRepoService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * Сервис управления (создание, получение,удаление, поиск по имени)
 * модель Request(запрос об усыновлении)
 */
@Service
public class RequestRepoServiceImpl implements RequestRepoService {

    private final RequestRepository requestRepository;
    private final ArrayList<Request> requestStorage;

    public RequestRepoServiceImpl(RequestRepository requestRepository) {
        this.requestStorage = new ArrayList<>();
        this.requestRepository = requestRepository;
    }

    @Override
    public Request create(Request request) {

        return requestRepository.save(request);
    }

    @Override
    public Request delete(Long id) {
        Optional<Request> request = requestRepository.findById(id);
        if (request.isPresent()) {
            requestRepository.deleteById(id);
            return request.get();
        } else {

            throw new EntityNotFoundException("Клиента с " + id + "id не существует");
        }
    }

    @Override
    public Request get(Long id) {
        Optional<Request> request = requestRepository.findById(id);
        if (request.isPresent()) {
            return request.get();
        } else {

            throw new EntityNotFoundException("Клиента с " + id + "id не существует");
        }

    }

    @Override
    public Request findRequestByPetName(String petName) {
        return requestRepository.findRequestByPetName(petName);
    }

    @Override
    public Collection<Request> getAll() {
        return requestRepository.findAll();
    }
}
