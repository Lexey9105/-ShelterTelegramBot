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


    public static final String ERROR = "Возникла ошибка, попробуйте еще раз";

    //1-й уровень кнопок
    public final static String CAT_SHELTER_CALLBACK = "Приют для кошек";
    public final static String DOG_SHELTER_CALLBACK = "Приют для собак";
    public final static String DOG_SHELTER_WELCOME_MSG_TEXT = "Вас приветствует приют для собак. Чем я могу Вам помочь?";
    public final static String CAT_SHELTER_WELCOME_MSG_TEXT = "Вас приветствует приют для кошек. Чем я могу Вам помочь?";

    //2-й уровень кнопок

    public final static String BUTTON_INFO_TEXT = "Узнать информацию о приюте";
    public final static String BUTTON_HOW_TO_PICK_UP_TEXT = "Как взять питомца из приюта";
    public final static String BUTTON_SEND_REPORT_TEXT = "Прислать отчет о питомце";
    public final static String BUTTON_CALL_VOLUNTEER_TEXT = "Позвать волонтера";
    public final static String BUTTON_BACK_TEXT = "Вернутся назад";

    //3-й уровень кнопок
        //Информация о приюте


}
