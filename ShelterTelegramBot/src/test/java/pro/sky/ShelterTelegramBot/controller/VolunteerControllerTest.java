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
import pro.sky.ShelterTelegramBot.model.Volunteer;
import pro.sky.ShelterTelegramBot.repository.VolunteerRepository;
import pro.sky.ShelterTelegramBot.service.VolunteerService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VolunteerController.class)
public class VolunteerControllerTest {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private VolunteerRepository volunteerRepository;
    @MockBean
    private VolunteerService volunteerService;

    @InjectMocks
    private VolunteerController volunteerController;


    @Test
    public void createVolunteer() throws Exception {
        Long id = 1L;

        Volunteer volunteer = new Volunteer(id, "d", 22);

        JSONObject jsonVolunteer = new JSONObject();
        when(volunteerRepository.save(any(Volunteer.class))).thenReturn(volunteer);
        jsonVolunteer.put("id", id);
        jsonVolunteer.put("name", volunteer.getUserName());
        jsonVolunteer.put("age", volunteer.getStatus());
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/volunteer")
                        .content(jsonVolunteer.toString())
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk());

    }


    @Test
    public void removeVolunteer() throws Exception {
        Long id = 1L;
        Volunteer volunteer = new Volunteer(id, "d", 22);

        when(volunteerRepository.findById(id)).thenReturn(Optional.of(volunteer));


        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/volunteer/" + id)
                        .content(id.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getVolunteer() throws Exception {
        Long id = 1L;
        Volunteer volunteer = new Volunteer(id, "d", 22);

        when(volunteerRepository.findById(id)).thenReturn(Optional.of(volunteer));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/volunteer/" + id)
                        .content(id.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getVolunteerAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/volunteer")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
