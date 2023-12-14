package pro.sky.ShelterTelegramBot.handlers;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.InlineQueryResultPhoto;

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

    public static InlineKeyboardMarkup infoShelterCatButtons() {
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

    public static InlineKeyboardMarkup infoShelterDogButtons() {
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

    public static InlineKeyboardMarkup infoPetsCatButtons() {
        InlineKeyboardMarkup markupPetsCat = new InlineKeyboardMarkup();
        InlineKeyboardButton PetCatsListButton = new InlineKeyboardButton("Получить список животных для усыновления").
                callbackData(PetCatsList);
        InlineKeyboardButton RulesDatingCatButton = new InlineKeyboardButton("Правила знакомства с животным в приюте").
                callbackData(RulesDatingCat);
        InlineKeyboardButton DocCatButton = new InlineKeyboardButton("Список документов для усыновления").
                callbackData(DocCat);
        InlineKeyboardButton TransportOfCatButton = new InlineKeyboardButton("Рекомендации по транспортировке").
                callbackData(TransportOfCat);
        InlineKeyboardButton HomeCatsChildButton = new InlineKeyboardButton("Рекомендаций по обустройству дома для котёнка").
                callbackData(HomeCatsChild);
        InlineKeyboardButton HomeCatsAdultButton = new InlineKeyboardButton("Рекомендаций по обустройству дома для взрослой кошки").
                callbackData(HomeCatsAdult);
        InlineKeyboardButton HomeCatsDisabilitiesButton = new InlineKeyboardButton("Рекомендаций по обустройству дома для  кошки с ограниченными возможностями").
                callbackData(HomeCatsDisabilities);
        InlineKeyboardButton FailCatsButton = new InlineKeyboardButton("Причины для отказа в усыновлении").
                callbackData(FailCats);
        InlineKeyboardButton CREATECats_31Button = new InlineKeyboardButton("Отправить контактные данные").
                callbackData(CREATECats_31);
        InlineKeyboardButton CALLCats_31Button = new InlineKeyboardButton("Позвать волонтера").
                callbackData(CALLCats_31);
        markupPetsCat.addRow(PetCatsListButton);
        markupPetsCat.addRow(RulesDatingCatButton);
        markupPetsCat.addRow(DocCatButton);
        markupPetsCat.addRow(TransportOfCatButton);
        markupPetsCat.addRow(HomeCatsChildButton);
        markupPetsCat.addRow(HomeCatsAdultButton);
        markupPetsCat.addRow(HomeCatsDisabilitiesButton);
        markupPetsCat.addRow(FailCatsButton);
        markupPetsCat.addRow(CREATECats_31Button);
        markupPetsCat.addRow(CALLCats_31Button);


        return markupPetsCat;
    }


    public static InlineKeyboardMarkup infoPetsDogButtons() {
        InlineKeyboardMarkup markupPetsDog = new InlineKeyboardMarkup();
        InlineKeyboardButton PetDogListButton = new InlineKeyboardButton("Получить список животных для усыновления").
                callbackData(PetDogList);
        InlineKeyboardButton RulesDatingDogButton = new InlineKeyboardButton("Правила знакомства с животным в приюте").
                callbackData(RulesDatingDog);
        InlineKeyboardButton DocDogButton = new InlineKeyboardButton("Список документов для усыновления").
                callbackData(DocDog);
        InlineKeyboardButton TransportOfDogButton = new InlineKeyboardButton("Рекомендации по транспортировке").
                callbackData(TransportOfDog);
        InlineKeyboardButton HomeDogsChildButton = new InlineKeyboardButton("Рекомендаций по обустройству дома для щенка").
                callbackData(HomeDogsChild);
        InlineKeyboardButton HomeDogsAdultButton = new InlineKeyboardButton("Рекомендаций по обустройству дома для взрослой собаки").
                callbackData(HomeDogsAdult);
        InlineKeyboardButton HomeDogsDisabilitiesButton = new InlineKeyboardButton("Рекомендаций по обустройству дома для  собаки с ограниченными возможностями").
                callbackData(HomeDogsDisabilities);
        InlineKeyboardButton DogHandlerFirstTimeButton = new InlineKeyboardButton("Рекомендаций по обустройству дома для  собаки с ограниченными возможностями").
                callbackData(DogHandlerFirstTime);
        InlineKeyboardButton DogHandlerRecommendationButton = new InlineKeyboardButton("Рекомендаций по обустройству дома для  собаки с ограниченными возможностями").
                callbackData(DogHandlerRecommendation);
        InlineKeyboardButton FailDogsButton = new InlineKeyboardButton("Причины для отказа в усыновлении").
                callbackData(FailDogs);
        InlineKeyboardButton CREATEDogs_32Button = new InlineKeyboardButton("Отправить контактные данные").
                callbackData(CREATEDogs_32);
        InlineKeyboardButton CALLDogs_32Button = new InlineKeyboardButton("Позвать волонтера").
                callbackData(CALLDogs_32);
        markupPetsDog.addRow(PetDogListButton);
        markupPetsDog.addRow(RulesDatingDogButton);
        markupPetsDog.addRow(DocDogButton);
        markupPetsDog.addRow(TransportOfDogButton);
        markupPetsDog.addRow(HomeDogsChildButton);
        markupPetsDog.addRow(HomeDogsAdultButton);
        markupPetsDog.addRow(HomeDogsDisabilitiesButton);
        markupPetsDog.addRow(DogHandlerFirstTimeButton);
        markupPetsDog.addRow(DogHandlerRecommendationButton);
        markupPetsDog.addRow(FailDogsButton);
        markupPetsDog.addRow(CREATEDogs_32Button);
        markupPetsDog.addRow(CALLDogs_32Button);


        return markupPetsDog;
    }


    public static InlineKeyboardMarkup MenuShelterDogButtons() {
        InlineKeyboardMarkup markupMenuDog = new InlineKeyboardMarkup();
        InlineKeyboardButton DogShelterInfoButton = new InlineKeyboardButton("Общая информация о приюте").
                callbackData(DogShelterInfo);
        InlineKeyboardButton DogsPetsInfoButton = new InlineKeyboardButton("Как взять собаку из приюта").
                callbackData(DogsPetsInfo);
        InlineKeyboardButton DogsControlServiceButton = new InlineKeyboardButton("Отправить отчет").
                callbackData(DogsControlService);
        InlineKeyboardButton CALLDogs_12Button = new InlineKeyboardButton("Позвать волонтера").
                callbackData(CALLDogs_12);
        markupMenuDog.addRow(DogShelterInfoButton);
        markupMenuDog.addRow(DogsPetsInfoButton);
        markupMenuDog.addRow(DogsControlServiceButton);
        markupMenuDog.addRow(CALLDogs_12Button);

        return markupMenuDog;
    }

    public static InlineKeyboardMarkup MenuShelterCatButtons() {
        InlineKeyboardMarkup markupMenuCat = new InlineKeyboardMarkup();
        InlineKeyboardButton CatShelterInfoButton = new InlineKeyboardButton("Общая информация о приюте").
                callbackData(CatShelterInfo);
        InlineKeyboardButton CatsPetsInfoButton = new InlineKeyboardButton("Как взять собаку из приюта").
                callbackData(CatsPetsInfo);
        InlineKeyboardButton CatsControlServiceButton = new InlineKeyboardButton("Отправить отчет").
                callbackData(CatsControlService);
        InlineKeyboardButton CALLCats_12Button = new InlineKeyboardButton("Позвать волонтера").
                callbackData(CALLCats_12);
        markupMenuCat.addRow(CatShelterInfoButton);
        markupMenuCat.addRow(CatsPetsInfoButton);
        markupMenuCat.addRow(CatsControlServiceButton);
        markupMenuCat.addRow(CALLCats_12Button);

        return markupMenuCat;
    }

    public static InlineKeyboardMarkup MenuVolunteerButtons() {
        InlineKeyboardMarkup markupMenuCat = new InlineKeyboardMarkup();
        InlineKeyboardButton VolunteerGetRequestButton = new InlineKeyboardButton("Получить запросы об усыновлении").
                callbackData(Get_Request);
        InlineKeyboardButton VolunteerGetReportButton = new InlineKeyboardButton("Получить отчеты об усыновлении").
                callbackData(Get_Report);
        markupMenuCat.addRow(VolunteerGetRequestButton);
        markupMenuCat.addRow(VolunteerGetReportButton);

        return markupMenuCat;
    }

    public static InlineKeyboardMarkup MenuReportButtons() {
        InlineKeyboardMarkup markupMenuReport = new InlineKeyboardMarkup();
        InlineKeyboardButton ReportInfoButton = new InlineKeyboardButton("Правила сдачи отчета").
                callbackData(ReportInfo);
        InlineKeyboardButton PetsPhotoButton = new InlineKeyboardButton("Отправить фото питмца").
                callbackData(PetsPhoto);
        InlineKeyboardButton ReportControlButton = new InlineKeyboardButton("Отправить отчет").
                callbackData(ReportControl);
        InlineKeyboardButton CALLButton = new InlineKeyboardButton("Позвать волонтера").
                callbackData(CALL);
        markupMenuReport.addRow(ReportInfoButton);
        markupMenuReport.addRow(PetsPhotoButton);
        markupMenuReport.addRow(ReportControlButton);
        markupMenuReport.addRow(CALLButton);

        return markupMenuReport;
    }
}
