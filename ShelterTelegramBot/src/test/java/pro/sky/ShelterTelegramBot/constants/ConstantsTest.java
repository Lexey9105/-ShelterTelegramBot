package pro.sky.ShelterTelegramBot.constants;

import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Service;
import pro.sky.ShelterTelegramBot.utils.BotTestUtils;

import static pro.sky.ShelterTelegramBot.constants.Constants.*;

/**
 * Константы для тестирования
 */
@Service
public class ConstantsTest {
    public final static Update updateCLIENT= BotTestUtils.getUpdate(CHAT_ID, "@Иван Иванов,21,89990001111,Москва Название улицы 9");
    public final static Update updateCLIENTSend= BotTestUtils.getUpdate(CHAT_ID, "контактные данные успешно полученны");

    public final static Update updateStart= BotTestUtils.getUpdate(CHAT_ID, "/start");
    public final static Update updateHelp= BotTestUtils.getUpdate(CHAT_ID, "/help");
    public final static Update updateSendStart= BotTestUtils.getUpdate(CHAT_ID, "Привет! Я бот для работы с приютами для собак и кошек. " +
            "Здесь ты можешь получить о каждом приюте всю информацию, Пообщаться с волонтерами приютов." +
            "Кроме того, для тех кто заберет к себе домой животное, здесь нам будет удобно узнавать как поживает животное" +
            "Далее следуйте меню");
    public final static Update updateSendHelp= BotTestUtils.getUpdate(CHAT_ID, "Зовем волонтера");








}
