package pro.sky.ShelterTelegramBot.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.ShelterTelegramBot.model.Client;
import pro.sky.ShelterTelegramBot.service.ClientService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;


import java.util.Collection;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @Operation(
            summary = "Создание клиента питомника",
            requestBody = @RequestBody(
                    description = "Данные создаваемого  клиента питомника." +
                            "id переданный в теле будет игнорироваться, будет присвоен следующий id из БД. " +
                            "Все поля кроме id обязательны.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Client.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Данные созданного  клиента питомника",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Client.class)
                            )

                    )
            }
    )
    @PostMapping()
    public ResponseEntity<Client> createClient(@org.springframework.web.bind.annotation.RequestBody Client client) {
        return ResponseEntity.ok(clientService.create(client));
    }


    @Operation(
            summary = "Удаление клиента питомника по id."
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Client> deleteClient(
            @Parameter(description = "id удаляемого клиента питомника")
            @PathVariable Long id) {

        return ResponseEntity.ok(clientService.delete(id));
    }


    @Operation(
            summary = "Поиск клиента питомника по id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Информация о найденном клиенте питомника",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Client.class)
                            )
                    )
            }
    )
    @GetMapping("{id}")
    public ResponseEntity<Client> getClient(
            //@Parameter(description = "Идентификатор клиента", example = "1")
            @PathVariable Long id) {

        return ResponseEntity.ok(clientService.get(id));
    }


    @Operation(
            summary = "Получение всех клиентов из БД.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Все найденные клиенты",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Client.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "клиенты не найдены"
                    )
            }
    )
    @GetMapping()
    public ResponseEntity<Collection<Client>> findAll() {
        return ResponseEntity.ok(clientService.findAll());
    }
}
