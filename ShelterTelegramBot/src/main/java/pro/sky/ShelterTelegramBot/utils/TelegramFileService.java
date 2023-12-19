package pro.sky.ShelterTelegramBot.utils;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.impl.FileApi;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.ShelterTelegramBot.handlers.CallbackQuery.CatCallbackQuery;
import pro.sky.ShelterTelegramBot.handlers.CallbackQuery.DogCallbackQuery;
import pro.sky.ShelterTelegramBot.listener.TelegramBotUpdatesListener;
import pro.sky.ShelterTelegramBot.model.Attachment;
import pro.sky.ShelterTelegramBot.model.Client;
import pro.sky.ShelterTelegramBot.service.AttachmentService;
import pro.sky.ShelterTelegramBot.service.ClientStatusService;
import pro.sky.ShelterTelegramBot.service.VolunteerService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

@Service
public class TelegramFileService {

    @Value("${telegram.bot.token}")
    private String token;
    private Logger log = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final String FilePath = "ShelterTelegramBot/attachments";

    private final TelegramBot telegramBot;
    private final AttachmentService attachmentService;
    private final Send send;
    private final ClientStatusService clientStatusService;


    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    public TelegramFileService(TelegramBot telegramBot, ClientStatusService clientStatusService, AttachmentService attachmentService, Send send) {
        this.telegramBot = telegramBot;
        this.attachmentService = attachmentService;
        this.clientStatusService = clientStatusService;
        this.send = send;

    }

    /**
     * Получение  ссылки на файл с сервера телеграм и вызов
     * меода для сохранения файла на диск
     */
    public void getLocalPathTelegramFile(Update update) throws IOException {
        String fileId = getTelegramFileId(update);
        if (fileId == null) {
            //return null;
        }
        GetFileResponse getFileResponse = telegramBot.execute(new GetFile(fileId));
        File file = getFileResponse.file(); // com.pengrad.telegrambot.model.File
        FileApi fileApi = new FileApi(token);
        String fullFilePath = fileApi.getFullFilePath(file.filePath());
        Path path = Paths.get(saveFile(fullFilePath, update));
        if (update.message().caption().charAt(0) == '*') {
            send.sendPhoto(attachmentService.addAttachmentRepo(path.toFile(), update), update);
        } else {
            send.sendPhoto(attachmentService.addAttachmentPet(path.toFile(), update), update);
        }
        //return attachmentService.addAttachmentRepo(path.toFile(),update);
    }

    /**
     * Получение Id файла из события на сервере
     *
     * @return String fileId
     */
    public String getTelegramFileId(Update update) {
        if (update.message() != null &&
                update.message().document() != null &&
                update.message().document().fileId() != null) {
            return update.message().document().fileId();
        }
        if (update.message().photo() != null) {
            int index = update.message().photo().length - 1;
            PhotoSize[] photo = update.message().photo();
            return photo[index].fileId();
        }
        return null;
    }

    /**
     * Сохранение файла не диск
     *
     * @param urlPath путь до файла на сервере
     * @return String filePath - путь к файлу на диске
     */

    public String saveFile(String urlPath, Update update) throws IOException {
        // https://www.baeldung.com/java-download-file#using-nio
        String pathFile = getFullLocalPathFile(urlPath, update);
        log.debug("pathFile from saveFile,{}", pathFile);
        FileOutputStream fileOutputStream = null;
        try {
            URL url = new URL(urlPath);
            ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
            fileOutputStream = new FileOutputStream(pathFile);
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        } catch (IOException exception) {
            log.debug(exception.getMessage());
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        }

        return pathFile;
    }

    /**
     * Создание директорий и названия файла на диске
     *
     * @param urlPath путь до файла на сервере
     * @return filePath путь к файлу на диске
     */
    public String getFullLocalPathFile(String urlPath, Update update) {
        String pathFile = "";
        String fileExtension = getFileName(urlPath);
        int dotIndex = fileExtension.lastIndexOf(".");
        try {
            String separator = System.getProperties().getProperty("file.separator"); // берем какой слеш у системы
            pathFile = FilePath;
            java.io.File directory = new java.io.File(pathFile);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            pathFile = pathFile + separator + getFileName(urlPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.debug("LocalPathFile: {}", pathFile);
        return pathFile;
    }

    /**
     * Получение имени файла с сервера телеграм
     *
     * @param path путь до файла на сервере
     * @return fileName имя файла
     */
    public String getFileName(String path) {
        String separator = "/";
        if (path.startsWith("http")) {
            separator = "/";
        } else {
            separator = System.getProperties().getProperty("file.separator");
        }
        return path.substring(path.lastIndexOf(separator) + 1);
    }


}
