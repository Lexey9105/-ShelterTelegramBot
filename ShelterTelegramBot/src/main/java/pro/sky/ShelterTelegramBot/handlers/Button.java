package pro.sky.ShelterTelegramBot.handlers;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;

public class Button {

    public static InlineKeyboardMarkup animalSelectionButtons() {

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

        InlineKeyboardButton catButton = new InlineKeyboardButton("Приют кошек").
                callbackData("CAT_SHELTER_CALLBACK");
        InlineKeyboardButton dogButton = new InlineKeyboardButton("Приют собак").
                callbackData("DOG_SHELTER_CALLBACK");

        markupInline.addRow(catButton);
        markupInline.addRow(dogButton);

        return markupInline;
    }

    public static InlineKeyboardMarkup infoShelterCatButtons(){
        InlineKeyboardMarkup markupShelterCat = new InlineKeyboardMarkup();
        InlineKeyboardButton workScheduleButton = new InlineKeyboardButton("Расписание работы").
                callbackData("021_WORK_SCHEDULE");
        InlineKeyboardButton carPassButton = new InlineKeyboardButton("Оформление пропуска на машину").
                callbackData("021_REGISTRATION_CAR");
        InlineKeyboardButton safetyButton = new InlineKeyboardButton("Техника безопасности на территории приюта").
                callbackData("021_SAFETY");
        InlineKeyboardButton createButton = new InlineKeyboardButton("Отправить контактные данные").
                callbackData("021_CREATE");
        InlineKeyboardButton callButton = new InlineKeyboardButton("Позвать волонтера").
                callbackData("021_CALL");
        markupShelterCat.addRow(workScheduleButton);
        markupShelterCat.addRow(carPassButton);
        markupShelterCat.addRow(safetyButton);
        markupShelterCat.addRow(createButton);
        markupShelterCat.addRow(callButton);


        return markupShelterCat;
    }

    public static InlineKeyboardMarkup infoShelterDogButtons(){
        InlineKeyboardMarkup markupShelterDog = new InlineKeyboardMarkup();
        InlineKeyboardButton workScheduleButton = new InlineKeyboardButton("Расписание работы").
                callbackData("022_WORK_SCHEDULE");
        InlineKeyboardButton carPassButton = new InlineKeyboardButton("Оформление пропуска на машину").
                callbackData("022_REGISTRATION_CAR");
        InlineKeyboardButton safetyButton = new InlineKeyboardButton("Техника безопасности на территории приюта").
                callbackData("022_SAFETY");
        InlineKeyboardButton createButton = new InlineKeyboardButton("Отправить контактные данные").
                callbackData("022_CREATE");
        InlineKeyboardButton callButton = new InlineKeyboardButton("Позвать волонтера").
                callbackData("022_CALL");
        markupShelterDog.addRow(workScheduleButton);
        markupShelterDog.addRow(carPassButton);
        markupShelterDog.addRow(safetyButton);
        markupShelterDog.addRow(createButton);
        markupShelterDog.addRow(callButton);


        return markupShelterDog;
    }

}
