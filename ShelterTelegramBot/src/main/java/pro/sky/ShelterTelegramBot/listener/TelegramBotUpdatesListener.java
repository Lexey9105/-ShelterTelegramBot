package pro.sky.ShelterTelegramBot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private String welcomeMessage = "Привет.";

    private TelegramBot telegramBot;

    public TelegramBotUpdatesListener(TelegramBot telegramBot){
        this.telegramBot=telegramBot;
    }
    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {

            updates.forEach(update -> {
                logger.info("Processing update: {}", update);
                if (update.message().text().equals("/start")) {
                    SendMessage message = new SendMessage
                            (update.message().chat().id(), welcomeMessage);
                    SendResponse response = telegramBot.execute(message);
                } else {

                }
            });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
