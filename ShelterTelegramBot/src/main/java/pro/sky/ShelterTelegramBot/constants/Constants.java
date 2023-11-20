package pro.sky.ShelterTelegramBot.constants;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Константы
 */
@Service
public class Constants {

    public static final String SAY_HELLO = "Привет! Я бот для работы с приютами для собак и кошек. " +
            "Здесь ты можешь получить о каждом приюте всю информацию, Пообщаться с волонтерами приютов." +
            "Кроме того, для тех кто заберет к себе домой животное, здесь нам будет удобно узнавать как поживает животное" +
            "Далее следуйте меню";


    public static final String ASK_HELP = "Зовем волонтера";

    public static final String SHELTER_TYPE_SELECT_MSG_TEXT = "Выберите приют";


    public static final String ERROR = "Возникла ошибка, попробуйте еще раз";

    //1-й уровень кнопок
    public final static String CAT_SHELTER_CALLBACK = "Приют для кошек";
    public final static String DOG_SHELTER_CALLBACK = "Приют для собак";
    public final static String DOG_SHELTER_WELCOME_MSG_TEXT = "Вас приветствует приют для собак. Чем я могу Вам помочь?";
    public final static String CAT_SHELTER_WELCOME_MSG_TEXT = "Вас приветствует приют для кошек. Чем я могу Вам помочь?";

    //2-й уровень кнопок

    public final static String CALL_ID_SHELTER_INFORMATION_CAT_MENU="021";
    public final static String CALL_ID_SHELTER_INFORMATION_DOG_MENU="022";

    public final static String DOWNLOAD_LINK="Для скачивания файла перейдите по ссылке - http://localhost:8080/attachments/get/";
    public final static String WORK_SCHEDULE_CAT="Для получения информации по расположению и режиму работы перейдите по ссылке - https://yandex.ru/maps/org/murkosha/1807729764/?ll=37.151500%2C55.881819&source=serp_navig&z=9";
    public final static String REGISTRATION_CAR_CAT="Контакты охранника";
    public final static String WORK_SCHEDULE_DOG="Для получения информации по расположению и режиму работы перейдите по ссылке -https://yandex.ru/maps/org/iskra/58489882123/?ll=37.503295%2C55.863665&source=serp_navig&z=10.8";
    public final static String REGISTRATION_CAR_DOG="Контакты охранника";

    public final static String CREATE="Для регистрации в нашем приюте отправте контактые данные строго в формате:@Иван Иванов,21,89990001111,Москва Название улицы 9";
    public final static String CALLBACK="Наш волотер скоро с вами свяжется";



    public final static String BUTTON_INFO_TEXT = "Узнать информацию о приюте";
    public final static String BUTTON_HOW_TO_PICK_UP_TEXT = "Как взять питомца из приюта";
    public final static String BUTTON_SEND_REPORT_TEXT = "Прислать отчет о питомце";
    public final static String BUTTON_CALL_VOLUNTEER_TEXT = "Позвать волонтера";
    public final static String BUTTON_BACK_TEXT = "Вернутся назад";

    //3-й уровень кнопок
        //Информация о приюте


}
