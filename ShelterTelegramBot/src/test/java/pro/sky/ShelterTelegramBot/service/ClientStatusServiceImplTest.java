package pro.sky.ShelterTelegramBot.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.ShelterTelegramBot.model.Client;
import pro.sky.ShelterTelegramBot.model.ClientStatus;
import pro.sky.ShelterTelegramBot.repository.ClientStatusRepository;
import pro.sky.ShelterTelegramBot.service.impl.ClientStatusServiceImpl;

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
public class ClientStatusServiceImplTest {


   @Mock
    private ClientStatusRepository clientStatusRepository;

   @InjectMocks
   private ClientStatusServiceImpl clientStatusService;


    @Test
    public void addTest() throws IOException {
        Long chatId=13333L;
        ClientStatus clientStatus = new ClientStatus(13333L,"Гость", 0,0);
        when(clientStatusRepository.save(clientStatus)).thenReturn(clientStatus);
       assertNotNull(clientStatusService.create(chatId));
        assertEquals(clientStatus, clientStatusService.create(chatId));
    }

    @Test
    public void delTest() throws IOException {
        Long chatId=13333L;
        ClientStatus clientStatus = new ClientStatus(13333L,"Гость", 0,0);


        when(clientStatusRepository.findById(1L)).thenReturn(Optional.of(clientStatus));


       clientStatusService.delete(1L);


        verify(clientStatusRepository, Mockito.times(1)).deleteById(1L);
    }


   @Test
    public void getTest() throws IOException {
        Long chatId=13333L;
        ClientStatus clientStatus = new ClientStatus(13333L,"Гость", 0,0);

        when(clientStatusRepository.findById(1L)).thenReturn(Optional.of(clientStatus));
        assertNotNull(clientStatusService.get(1L));
        assertEquals(clientStatus, clientStatusService.get(1L));
    }


    @Test
    void getAll() {
        ClientStatus clientStatus = new ClientStatus(13333L,"Гость", 0,0);
        ClientStatus clientStatus2 = new ClientStatus(1333L,"Гость", 0,0);

        List<ClientStatus> clientsStatus = Arrays.asList(clientStatus, clientStatus2);

       Collection<ClientStatus> result = Arrays.asList(clientStatus, clientStatus2);

        Mockito.when(clientStatusRepository.findAll()).thenReturn(clientsStatus);


        assertEquals(clientsStatus, clientStatusRepository.findAll());
    }
    @Test
    public void findChatIdTest(){
        Long chatId=13333L;
        ClientStatus clientStatus = new ClientStatus(13333L,"Гость", 0,0);

        Mockito.when(clientStatusRepository.findClientStatusByChatId(chatId)).thenReturn(clientStatus);
        assertEquals(clientStatus,clientStatusRepository.findClientStatusByChatId(chatId));
    }
    @Test
    public void clickTest(){
        Long chatId=13333L;
        int click=1;
        ClientStatus clientStatus = new ClientStatus(13333L,"Гость", 0,0);
        ClientStatus clientStatus2 = new ClientStatus(13333L,"Гость", 1,0);

        Mockito.when(clientStatusRepository.findClientStatusByChatId(chatId)).thenReturn(clientStatus);
        when(clientStatusRepository.save(clientStatus)).thenReturn(clientStatus);
        assertEquals(click,clientStatusService.clickCat(chatId,click));

    }

}
