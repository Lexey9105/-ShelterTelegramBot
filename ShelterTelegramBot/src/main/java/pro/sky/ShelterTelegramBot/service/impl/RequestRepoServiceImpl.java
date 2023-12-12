package pro.sky.ShelterTelegramBot.service.impl;

import org.springframework.stereotype.Service;
import pro.sky.ShelterTelegramBot.model.Request;
import pro.sky.ShelterTelegramBot.service.RequestRepoService;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class RequestRepoServiceImpl implements RequestRepoService {


    private final ArrayList<Request> requestStorage;

    public RequestRepoServiceImpl(){
        this.requestStorage=new ArrayList<>();
    }

    @Override
    public Request create(Request  request) {
        requestStorage.add(request);
        request.setId(requestStorage.indexOf(request));
        return request;
    }

    @Override
    public String delete(int id) {
        requestStorage.remove(requestStorage.get(id));
        return "удаление прошло успшено";
    }

    @Override
    public Request get(int id) {
        return requestStorage.get(id);
    }

    @Override
    public Collection<Request> getAll() {
        return requestStorage;
    }
}
