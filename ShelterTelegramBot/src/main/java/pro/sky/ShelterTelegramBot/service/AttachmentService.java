package pro.sky.ShelterTelegramBot.service;


import com.pengrad.telegrambot.model.Update;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.ShelterTelegramBot.model.Attachment;
import pro.sky.ShelterTelegramBot.model.Client;
import pro.sky.ShelterTelegramBot.model.ReportStatus;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public interface AttachmentService {


    Attachment addAttachment(MultipartFile file) throws IOException;
    String addAttachmentRepo(File file, Update update) throws IOException;
    Resource loadFileAsResource( String fileName) throws MalformedURLException;
    File loadFile (String fileName) throws IOException;

}
