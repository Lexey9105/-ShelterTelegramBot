package pro.sky.ShelterTelegramBot.controller;


import pro.sky.ShelterTelegramBot.model.Client;
import pro.sky.ShelterTelegramBot.repository.ClientRepository;
import pro.sky.ShelterTelegramBot.service.ClientService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ClientController.class)
public class ClientControllerTest {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private ClientRepository clientRepository;
    @MockBean
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;


    @Test
    public void createClient() throws Exception {
        Long id = 1L;

        Client client = new Client(id, "d", 22, "999", "p");

        JSONObject jsonClient = new JSONObject();
        when(clientRepository.save(any(Client.class))).thenReturn(client);
        jsonClient.put("id", id);
        jsonClient.put("name", client.getName());
        jsonClient.put("age", client.getAge());
        jsonClient.put("telephone", client.getTelephone());
        jsonClient.put("address", client.getAddress());
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/client")
                        .content(jsonClient.toString())
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk());

    }


    @Test
    public void removeClient() throws Exception {
        Long id = 1L;
        Client client = new Client(id, "d", 22, "999", "p");

        when(clientRepository.findById(id)).thenReturn(Optional.of(client));


        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/client/" + id)
                        .content(id.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getClient() throws Exception {
        Long id = 1L;
        Client client = new Client(id, "d", 22, "999", "p");

        when(clientRepository.findById(id)).thenReturn(Optional.of(client));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/client/" + id)
                        .content(id.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getClientAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/client")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
