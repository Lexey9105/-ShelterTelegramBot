package pro.sky.ShelterTelegramBot.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.ShelterTelegramBot.model.Attachment;
import pro.sky.ShelterTelegramBot.service.AttachmentService;
import pro.sky.ShelterTelegramBot.service.ClientService;
import pro.sky.ShelterTelegramBot.service.impl.AttachmentServiceImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/attachments")
public class AttachmentController {

    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService){
        this.attachmentService=attachmentService;
    }


    @PostMapping(value = "/add", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Map<String, String>> uploadAttachment(
            @RequestPart(value = "file") MultipartFile file)
            throws IOException {
        Attachment attachment = attachmentService.addAttachment(file);
        Map<String, String> attachmentStatus = new HashMap<>();
        attachmentStatus.put("status", "ok");
        attachmentStatus.put("attachId", attachment.getAttachId().toString());
        return ResponseEntity.ok(attachmentStatus);
    }

    /**
     * Получить ссылку на скачивание загруженного файла
     *
     * @param filename
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping("/get/{filename:.+}")
    public ResponseEntity<Resource> serveFile(
            @PathVariable String filename, HttpServletRequest request)
            throws IOException {
        Resource resource = attachmentService.loadFileAsResource(filename);
        String contentType;
        contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
