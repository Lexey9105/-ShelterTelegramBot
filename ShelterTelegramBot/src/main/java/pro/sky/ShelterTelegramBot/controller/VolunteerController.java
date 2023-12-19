package pro.sky.ShelterTelegramBot.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.ShelterTelegramBot.model.Client;
import pro.sky.ShelterTelegramBot.model.Volunteer;
import pro.sky.ShelterTelegramBot.service.ClientService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import pro.sky.ShelterTelegramBot.service.VolunteerService;


import java.util.Collection;

@RestController
@RequestMapping("/volunteer")
public class VolunteerController {

    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }


    @Operation(
            summary = "Создание волонтера питомника",
            requestBody = @RequestBody(
                    description = "Данные создаваемого  волонтера питомника." +
                            "id переданный в теле будет игнорироваться, будет присвоен следующий id из БД. " +
                            "Все поля кроме id обязательны.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Volunteer.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Данные созданного  волонтера питомника",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class)
                            )

                    )
            }
    )
    @PostMapping()
    public ResponseEntity<Volunteer> createVolunteer(@org.springframework.web.bind.annotation.RequestBody Volunteer volunteer) {
        return ResponseEntity.ok(volunteerService.create(volunteer));
    }


    @Operation(
            summary = "Удаление волонтера питомника по id."
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Volunteer> deleteClient(
            @Parameter(description = "id удаляемого волонтера питомника")
            @PathVariable Long id) {

        return ResponseEntity.ok(volunteerService.delete(id));
    }


    @Operation(
            summary = "Поиск волонтера питомника по id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Информация о найденном волонтера питомника",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class)
                            )
                    )
            }
    )
    @GetMapping("{id}")
    public ResponseEntity<Volunteer> getVolunteer(
            //@Parameter(description = "Идентификатор клиента", example = "1")
            @PathVariable Long id) {

        return ResponseEntity.ok(volunteerService.get(id));
    }


    @Operation(
            summary = "Получение всех волонтеров из БД.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Все найденные волонтеры",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "клиенты не найдены"
                    )
            }
    )
    @GetMapping()
    public ResponseEntity<Collection<Volunteer>> findAll() {
        return ResponseEntity.ok(volunteerService.findAll());
    }
}

