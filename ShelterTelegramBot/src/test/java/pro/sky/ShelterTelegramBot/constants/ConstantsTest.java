package pro.sky.ShelterTelegramBot.constants;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;
import pro.sky.ShelterTelegramBot.model.Attachment;
import pro.sky.ShelterTelegramBot.model.Client;
import pro.sky.ShelterTelegramBot.model.Shelter;
import pro.sky.ShelterTelegramBot.urils.BotTestUtils;

import static pro.sky.ShelterTelegramBot.constants.Constants.*;
import static pro.sky.ShelterTelegramBot.constants.ShelterType.CAT_SHELTER;
import static pro.sky.ShelterTelegramBot.constants.ShelterType.DOG_SHELTER;

/**
 * Константы для тестирования
 */
@Service
public class ConstantsTest {




    public static final String SAY_HELLO = "Привет! Я бот для работы с приютами для собак и кошек. " +
            "Здесь ты можешь получить о каждом приюте всю информацию, Пообщаться с волонтерами приютов." +
            "Кроме того, для тех кто заберет к себе домой животное, здесь нам будет удобно узнавать как поживает животное" +
            "Далее следуйте меню";





    public final static Long CHAT_ID=332L;

    public final static Update updateCLIENT= BotTestUtils.getUpdate(CHAT_ID, "@Иван Иванов,21,89990001111,Москва Название улицы 9");

    public final static Update updateCLIENTSend= BotTestUtils.getUpdate(CHAT_ID, "контактные данные успешно полученны");


    public final static Update updateStart= BotTestUtils.getUpdate(CHAT_ID, "/start");
    public final static Update updateHelp= BotTestUtils.getUpdate(CHAT_ID, "/help");



    public final static Update updateSendStart= BotTestUtils.getUpdate(CHAT_ID, "Привет! Я бот для работы с приютами для собак и кошек. " +
            "Здесь ты можешь получить о каждом приюте всю информацию, Пообщаться с волонтерами приютов." +
            "Кроме того, для тех кто заберет к себе домой животное, здесь нам будет удобно узнавать как поживает животное" +
            "Далее следуйте меню");
    public final static Update updateSendHelp= BotTestUtils.getUpdate(CHAT_ID, "Зовем волонтера");


    public final static String DOWNLOAD_LINK="Для скачивания файла перейдите по ссылке - http://localhost:8080/attachments/get/";
    public final static String WORK_SCHEDULE_CAT="Для получения информации по расположению и режиму работы перейдите по ссылке - https://yandex.ru/maps/org/murkosha/1807729764/?ll=37.151500%2C55.881819&source=serp_navig&z=9";
    public final static String REGISTRATION_CAR_CAT="Контакты охранника";
    public final static String WORK_SCHEDULE_DOG="Для получения информации по расположению и режиму работы перейдите по ссылке -https://yandex.ru/maps/org/iskra/58489882123/?ll=37.503295%2C55.863665&source=serp_navig&z=10.8";
    public final static String REGISTRATION_CAR_DOG="Контакты охранника";


    public final static String CREATE="Для регистрации в нашем приюте отправте контактые данные строго в формате:@Иван Иванов,21,89990001111,Москва Название улицы 9";
    public final static String CALLBACK="Наш волотер скоро с вами свяжется";






}
