package pro.sky.ShelterTelegramBot.utils;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import pro.sky.ShelterTelegramBot.service.AttachmentService;


import java.io.IOException;
import java.net.URL;



import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static pro.sky.ShelterTelegramBot.utils.URLPathGenerator.generateURLPath;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TelegramFileTest {


    @Mock
    private  TelegramBot telegramBot;
    @Mock
    private  AttachmentService attachmentService;
    @Mock
    private   Send send;
    @InjectMocks
    private TelegramFileService telegramFileService;


@Test
    public void getTelegramFileIdTest() throws IOException {
        Update update= mock(Update.class);
        Message message= mock(Message.class);
        Document document= mock(Document.class);
        PhotoSize photo= mock(PhotoSize.class);
        PhotoSize[]f1={photo};
        String fileId=f1[f1.length-1].fileId();
        when(update.message()).thenReturn(message);
        when(message.photo()).thenReturn(f1);
        when(f1[f1.length-1].fileId()).thenReturn(fileId);
        telegramFileService.getTelegramFileId(update);

    }
@Test
    public void saveFileTest() throws IOException {
    URL url = new URL("http://example.com/" + generateURLPath(10));
    String pathUrl=url.toString();
    Update update= mock(Update.class);

    telegramFileService.saveFile(pathUrl,update);
    }
}
