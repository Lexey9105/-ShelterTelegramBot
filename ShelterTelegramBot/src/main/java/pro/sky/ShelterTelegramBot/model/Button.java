package pro.sky.ShelterTelegramBot.model;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;

public class Button {

    public InlineKeyboardMarkup animalSelectionButtons() {

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

        InlineKeyboardButton catButton = new InlineKeyboardButton("Приют кошек").
                callbackData("CAT_SHELTER_CALLBACK");
        InlineKeyboardButton dogButton = new InlineKeyboardButton("Приют собак").
                callbackData("DOG_SHELTER_CALLBACK");

        markupInline.addRow(catButton);
        markupInline.addRow(dogButton);

        return markupInline;
    }
}
