package pro.sky.ShelterTelegramBot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pro.sky.ShelterTelegramBot.handlers.CallbackQuery.HandlerCallbackQuery;
import pro.sky.ShelterTelegramBot.service.ClientService;
import pro.sky.ShelterTelegramBot.utils.BotTestUtils;

import java.util.stream.Stream;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static pro.sky.ShelterTelegramBot.constants.Constants.*;
import static pro.sky.ShelterTelegramBot.constants.ConstantsTest.*;

@SpringBootTest
public class TelegramBotUpdatesListenerTest {
    @MockBean
    private  TelegramBot telegramBot;
    @MockBean
    private HandlerCallbackQuery handlerCallbackQuery;
    @MockBean
    private ClientService clientService;
@Autowired
    private TelegramBotUpdatesListener telegramBotUpdatesListener;


    public static Stream<Arguments> paramsForTest2() {
        return Stream.of(
                Arguments.of(DOG_SHELTER_CALLBACK,DOG_SHELTER_CALLBACK),
                Arguments.of(CAT_SHELTER_CALLBACK,CAT_SHELTER_CALLBACK),
                Arguments.of(WORK_SCHEDULE,WORK_SCHEDULE_CAT),
                Arguments.of(REGISTRATION_CARCat, REGISTRATION_CAR_CAT),
                Arguments.of(SAFETYCat, DOWNLOAD_LINK+"01.docx"),
                Arguments.of(CREATECat, CREATE),
                Arguments.of(CALLCat, CALLBACK),
                Arguments.of(WORK_SCHEDULEDog,WORK_SCHEDULE_DOG),
                Arguments.of(REGISTRATION_CARDog, REGISTRATION_CAR_DOG),
                Arguments.of(SAFETYDog, DOWNLOAD_LINK+"01.docx"),
                Arguments.of(CREATEDog, CREATE),
                Arguments.of(CALLDog, CALLBACK)
        );
    }


    public static Stream<Arguments> paramsForTest1() {
        return Stream.of(
                Arguments.of(updateCLIENT,updateCLIENTSend),
                Arguments.of(updateStart,updateSendStart),
                Arguments.of(updateHelp,updateSendHelp)
        );
    }


    @ParameterizedTest
    @MethodSource("paramsForTest1")
    public void ProcessUpdateAndHandleAndSaveClientTest(Update update1,Update update){
        String ass=update.message().text();
        var sendMessage = BotTestUtils.getSendMessage(telegramBot, telegramBotUpdatesListener, update1,update);
        assertEquals(ass, sendMessage.getParameters().get("text"));
    }


    @ParameterizedTest
    @MethodSource("paramsForTest2")
    public void ResponseButtonInfoShelterCallbackQueryTest(String callbackQuery,String update) {
        //String ass = update.message().text();
        Update update1 =BotTestUtils.getCallbackQuery(callbackQuery,null);
        Update update2=BotTestUtils.getUpdate(111L,update);
        SendMessage sendMessage = BotTestUtils.getSendMessage(telegramBot, telegramBotUpdatesListener, update1, update2);
        assertEquals(update, sendMessage.getParameters().get("text"));
    }



}
