package pro.sky.ShelterTelegramBot.utils;

import org.springframework.beans.factory.annotation.Value;

import java.nio.file.Path;

public class appProperties {

    @Value("${attachments.dir.path}")
    private static Path attachmentsDir;

    private static String absolutePath = attachmentsDir.toAbsolutePath().toString();

    public static String getUploadPath() {
        return absolutePath;
    }

    public static Path getPath() {
        return attachmentsDir;
    }
}
