package pro.sky.ShelterTelegramBot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.ShelterTelegramBot.model.Client;
import pro.sky.ShelterTelegramBot.model.Pet;
import pro.sky.ShelterTelegramBot.model.Shelter;
import pro.sky.ShelterTelegramBot.service.PetService;

import java.util.Collection;

@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @Operation(
            summary = "Создание питомца",
            requestBody = @RequestBody(
                    description = "Данные создаваемого  питомца." +
                            "id переданный в теле будет игнорироваться, будет присвоен следующий id из БД. " +
                            "Все поля кроме id обязательны.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Pet.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Данные созданного питомца",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Pet.class)
                            )

                    )
            }
    )
    @PostMapping()
    public ResponseEntity<Pet> createPet(@org.springframework.web.bind.annotation.RequestBody Pet pet) {
        return ResponseEntity.ok(petService.create(pet));
    }

    @Operation(
            summary = "Удаление питомца по id."
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Pet> deletePet(
            @Parameter(description = "id удаляемого питомца")
            @PathVariable Long id) {

        return ResponseEntity.ok(petService.delete(id));
    }


    @Operation(
            summary = "Поиск питомца по id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Информация о найденном питомца",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Pet.class)
                            )
                    )
            }
    )
    @GetMapping("{id}")
    public ResponseEntity<Pet> getPet(
            //@Parameter(description = "Идентификатор питомца", example = "1")
            @PathVariable Long id) {

        return ResponseEntity.ok(petService.get(id));
    }

    @Operation(
            summary = "Получение всех питомцев из БД одного питомника.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Все найденные питомцев",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Pet.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "питомцы не найдены"
                    )
            }
    )
    @GetMapping()
    public ResponseEntity<Collection<Pet>> findAllbyShelter(String petType) {
        return ResponseEntity.ok(petService.findAllByPetType(petType));
    }
}
