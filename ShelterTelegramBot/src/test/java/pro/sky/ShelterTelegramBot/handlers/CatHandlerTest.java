package pro.sky.ShelterTelegramBot.handlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.ShelterTelegramBot.handlers.CallbackQuery.CatCallbackQuery;
import pro.sky.ShelterTelegramBot.model.Attachment;
import pro.sky.ShelterTelegramBot.model.Client;
import pro.sky.ShelterTelegramBot.model.ClientStatus;
import pro.sky.ShelterTelegramBot.model.Pet;
import pro.sky.ShelterTelegramBot.service.*;
import pro.sky.ShelterTelegramBot.utils.Send;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CatHandlerTest {
    @Mock
   private TelegramBot telegramBot;
    @Mock
    private AttachmentService attachmentService;
    @Mock
    private ClientStatusService clientStatusService;
    @Mock
    private VolunteerService volunteerService;
    @Mock
    private RequestRepoService requestRepoService;
    @Mock
    private UserStatementService userStatementService;
    @Mock
    private PetService petService;
    @Mock
    private ShelterService shelterService;
    @Mock
    private Send send;
    @InjectMocks
    private CatCallbackQuery catCallbackQuery;
    @Test
    public void catInfoPetsTest () throws IOException {
        Long chatId=1L;
        String petType="кошка";
        Pet pet=new Pet("кошка",",fhfff","ffff");
        Attachment attachment=new Attachment("rrr", LocalDate.now(),"ffff","ffff");
        Collection<Pet> petCollection=new ArrayList<>();
        petCollection.add(pet);
        SendMessage sendMessage=new SendMessage(chatId,pet.getName());
        File file=new File(attachment.getAttachTitle());
        pet.setAttachment(attachment);
        //SendPhoto sendPhoto=new SendPhoto(chatId,attachment.getAttachTitle());

        Update update = Mockito.mock(Update.class);
        CallbackQuery callbackQuery = Mockito.mock(CallbackQuery.class);
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);

        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
        when(callbackQuery.data()).thenReturn("031_PetCatsList");
        when(petService.findAllByPetType(petType)).thenReturn(petCollection);
       //    when(send.sendMessageReturn(sendMessage)).thenReturn(pet.getName());
        when(attachmentService.loadFile(attachment.getAttachTitle())).thenReturn(file);
       // when(send.sendPhotoReturn(attachment.getAttachTitle(),update)).thenReturn(attachment.getAttachTitle());

catCallbackQuery.infoPetsCatButton(update);

        verify(clientStatusService).clickCat(1L, 2);
    }

}
