package pro.sky.ShelterTelegramBot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.ShelterTelegramBot.model.Attachment;

import pro.sky.ShelterTelegramBot.service.AttachmentService;


import java.io.IOException;




@Controller
@RequestMapping("/attachments")
public class AttachmentController {

    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService){
        this.attachmentService=attachmentService;
    }


    @Operation(
            summary = "Загрузка файла в приложение",
            requestBody = @RequestBody(
                    description = "Файл для загрузки",
                    content = @Content(
                            mediaType = "MediaType.MULTIPART_FORM_DATA",
                            schema = @Schema(implementation = MultipartFile.class)
                    )
            )
    )
    @PostMapping(value = "/add")
    @ResponseBody
    public ResponseEntity<HttpStatus> uploadAttachment(
            @RequestPart(value = "file") MultipartFile file)
            throws IOException {
        Attachment attachment = attachmentService.addAttachment(file);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(
            summary = "Передача файла по ссылке",
            requestBody = @RequestBody(
                    description = "Ссылка для загрузки",
                    content = @Content(
                            mediaType ="HttpServletRequest request",
                            schema = @Schema(implementation = HttpServletRequest.class)
                    )
            )
    )
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
        System.out.println(contentType);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
