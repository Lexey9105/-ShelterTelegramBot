package pro.sky.ShelterTelegramBot.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.sky.ShelterTelegramBot.model.Client;
import pro.sky.ShelterTelegramBot.model.Pet;
import pro.sky.ShelterTelegramBot.repository.PetRepository;
import pro.sky.ShelterTelegramBot.service.PetService;
import pro.sky.ShelterTelegramBot.service.ShelterService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PetController.class)
public class PetControllerTest {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private  PetRepository petRepository;
    @MockBean
    private  ShelterService shelterService;
    @MockBean
    private  PetService petService;
    @InjectMocks
    private PetController petController;

    @Test
    public void createPet() throws Exception {
        Long id = 1L;

        Pet pet=new Pet("кошка","name","gender");

        JSONObject jsonPet = new JSONObject();
        when(petRepository.save(any(Pet.class))).thenReturn(pet);
        jsonPet.put("id", id);
        jsonPet.put("petType", pet.getPetType());
        jsonPet.put("name", pet.getName());
        jsonPet.put("gender", pet.getGender());
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/pet")
                        .content(jsonPet.toString())
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk());

    }


    @Test
    public void removePet() throws Exception {
        Long id = 1L;
        Pet pet=new Pet("кошка","name","gender");

        when(petRepository.findById(id)).thenReturn(Optional.of(pet));


        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/pet/" + id)
                        .content(id.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getPet() throws Exception {
        Long id = 1L;
        Pet pet=new Pet("кошка","name","gender");

        when(petRepository.findById(id)).thenReturn(Optional.of(pet));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/pet/" + id)
                        .content(id.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getPetAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/pet")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
