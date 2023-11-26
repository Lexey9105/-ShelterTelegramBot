package pro.sky.ShelterTelegramBot.handlers;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;

import static pro.sky.ShelterTelegramBot.constants.Constants.*;

public class Button {

    public static InlineKeyboardMarkup animalSelectionButtons() {

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

        InlineKeyboardButton catButton = new InlineKeyboardButton("Приют кошек").
                callbackData(CAT_SHELTER_CALLBACK);
        InlineKeyboardButton dogButton = new InlineKeyboardButton("Приют собак").
                callbackData(DOG_SHELTER_CALLBACK);

        markupInline.addRow(catButton);
        markupInline.addRow(dogButton);

        return markupInline;
    }

    public static InlineKeyboardMarkup infoShelterCatButtons(){
        InlineKeyboardMarkup markupShelterCat = new InlineKeyboardMarkup();
        InlineKeyboardButton workScheduleButton = new InlineKeyboardButton("Расписание работы").
                callbackData(WORK_SCHEDULE);
        InlineKeyboardButton carPassButton = new InlineKeyboardButton("Оформление пропуска на машину").
                callbackData(REGISTRATION_CARCat);
        InlineKeyboardButton safetyButton = new InlineKeyboardButton("Техника безопасности на территории приюта").
                callbackData(SAFETYCat);
        InlineKeyboardButton createButton = new InlineKeyboardButton("Отправить контактные данные").
                callbackData(CREATECat);
        InlineKeyboardButton callButton = new InlineKeyboardButton("Позвать волонтера").
                callbackData(CALLCat);
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
                callbackData(WORK_SCHEDULEDog);
        InlineKeyboardButton carPassButton = new InlineKeyboardButton("Оформление пропуска на машину").
                callbackData(REGISTRATION_CARDog);
        InlineKeyboardButton safetyButton = new InlineKeyboardButton("Техника безопасности на территории приюта").
                callbackData(SAFETYDog);
        InlineKeyboardButton createButton = new InlineKeyboardButton("Отправить контактные данные").
                callbackData(CREATEDog);
        InlineKeyboardButton callButton = new InlineKeyboardButton("Позвать волонтера").
                callbackData(CALLDog);
        markupShelterDog.addRow(workScheduleButton);
        markupShelterDog.addRow(carPassButton);
        markupShelterDog.addRow(safetyButton);
        markupShelterDog.addRow(createButton);
        markupShelterDog.addRow(callButton);


        return markupShelterDog;
    }

}
