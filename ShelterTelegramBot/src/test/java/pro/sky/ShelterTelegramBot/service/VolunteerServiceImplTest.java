package pro.sky.ShelterTelegramBot.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.ShelterTelegramBot.model.Client;
import pro.sky.ShelterTelegramBot.model.Volunteer;
import pro.sky.ShelterTelegramBot.repository.VolunteerRepository;
import pro.sky.ShelterTelegramBot.service.impl.VolunteerServiceImpl;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VolunteerServiceImplTest {


    @Mock
    private VolunteerRepository volunteerRepository;

    @InjectMocks
    private VolunteerServiceImpl volunteerService;


    @Test
    public void addTest() throws IOException {
        Volunteer volunteer = new Volunteer("13333", 1);
        when(volunteerRepository.save(volunteer)).thenReturn(volunteer);
        assertNotNull(volunteerService.create(volunteer));
        assertEquals(volunteer, volunteerService.create(volunteer));
    }

    @Test
    public void delTest() throws IOException {
        Volunteer volunteer = new Volunteer("13333", 1);


        when(volunteerRepository.findById(1L)).thenReturn(Optional.of(volunteer));


        volunteerService.delete(1L);


        verify(volunteerRepository, Mockito.times(1)).deleteById(1L);
    }


    @Test
    public void getTest() throws IOException {
        Volunteer volunteer = new Volunteer("13333", 1);


        when(volunteerRepository.findById(1L)).thenReturn(Optional.of(volunteer));
        assertNotNull(volunteerService.get(1L));
        assertEquals(volunteer, volunteerService.get(1L));
    }


    @Test
    void getAll() {
        Volunteer volunteer = new Volunteer("13333", 1);
        Volunteer volunteer2 = new Volunteer("13333333", 2);


        List<Volunteer> volunteers = Arrays.asList(volunteer, volunteer2);

        Collection<Volunteer> result = Arrays.asList(volunteer, volunteer2);

        Mockito.when(volunteerRepository.findAll()).thenReturn(volunteers);


        Assert.assertEquals(volunteers, volunteerRepository.findAll());
    }

    @Test
    void findVolunteerByUserNameTest() {
        Volunteer volunteer = new Volunteer("13333", 1);


        Mockito.when(volunteerRepository.findVolunteerByUserName(volunteer.getUserName())).thenReturn(volunteer);


        Assert.assertEquals(volunteer, volunteerRepository.findVolunteerByUserName(volunteer.getUserName()));
    }

    @Test
    void findByStatusTest() {

    }


}
