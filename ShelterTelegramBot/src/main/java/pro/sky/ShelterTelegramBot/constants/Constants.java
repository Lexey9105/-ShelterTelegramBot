package pro.sky.ShelterTelegramBot.constants;

import org.springframework.stereotype.Component;

/**
 * Константы
 */
@Component
public class Constants {

    public String SAY_HELLO() {
        String SAY_HELLO = "Привет! Я бот для работы с приютами для собак и кошек. " +
                "Здесь ты можешь получить о каждом приюте всю информацию, Пообщаться с волонтерами приютов." +
                "Кроме того, для тех кто заберет к себе домой животное, здесь нам будет удобно узнавать как поживает животное" +
                "Далее следуйте меню";
        return SAY_HELLO;
    }

    public String ASK_HELP() {
        String ASK_HELP = "Зовем волонтера";
        return ASK_HELP;
    }


    String ERROR = "Возникла ошибка, попробуйте еще раз";
}
