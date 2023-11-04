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


import java.util.Collection;

@RestController
@RequestMapping("/client")
public class ClientController {

private final ClientService clientService;

public ClientController (ClientService clientService){
    this.clientService=clientService;
}


    @Operation(
            summary = "Создание клиента питомника",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
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
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client client1 =clientService.create(client);
        if (client1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(client1);
    }



    @Operation(
            summary = "Удаление клиента питомника по id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Данные удаляемого клиента питомника.",
                            content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Client.class)
                    )
                    )
            }
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Client> deleteClient(
            @Parameter(description = "id удаляемого клиента питомника")
            @PathVariable Long id) {
        Client client = clientService.get(id);
        if (client == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
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
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "клиент с данным id не найден."
                    )
            }
    )
    @GetMapping("{id}")
    public ResponseEntity<Client> getClient(
            @Parameter(description = "Идентификатор клиента", example = "1")
            @PathVariable Long id) {
        Client client = clientService.get(id);
        if (client == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
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
