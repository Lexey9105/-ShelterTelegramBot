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
import pro.sky.ShelterTelegramBot.model.Report;
import pro.sky.ShelterTelegramBot.repository.ClientRepository;
import pro.sky.ShelterTelegramBot.service.impl.ClientServiceImpl;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;


    @Test
    public void addTest() throws IOException {
        Client client = new Client("13333", 20, "9990001122", "fff");

        when(clientRepository.save(client)).thenReturn(client);
        assertNotNull(clientService.create(client));
        assertEquals(client, clientService.create(client));
    }

    @Test
    public void delTest() throws IOException {
        Client client = new Client(1L, "13333", 20, "9990001122", "fff");


        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));


        clientService.delete(1L);


        verify(clientRepository, Mockito.times(1)).deleteById(1L);
    }


    @Test
    public void getTest() throws IOException {
        Client client = new Client(1L, "13333", 20, "9990001122", "fff");


        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        assertNotNull(clientService.get(1L));
        assertEquals(client, clientService.get(1L));
    }


    @Test
    void getAll() {
        Client client = new Client(1L, "13333", 20, "9990001122", "fff");
        Client client2 = new Client(2L, "1333333", 24, "9990001122", "fff");


        List<Client> clients = Arrays.asList(client, client2);

        Collection<Client> result = Arrays.asList(client, client2);

        Mockito.when(clientRepository.findAll()).thenReturn(clients);


        Assert.assertEquals(clients, clientRepository.findAll());
    }

    @Test
    void getUserByNameTest() {
        Client client = new Client(1L, "13333", 20, "9990001122", "fff");
        String clientName= client.getName();
        List<Client> clients=new ArrayList<>();
        clients.add(client);

        Mockito.when(clientRepository.findAll()).thenReturn(clients);


        Assert.assertEquals(client, clientService.findByUserName(clientName));
    }



    @Test
    public void testUpdateWithClientStatus() {
        // Arrange
        Client client = new Client(1L, "13333", 20, "9990001122", "fff");
        ClientStatus clientStatus = new ClientStatus(1L,"PPP",0,0);

        when(clientRepository.save(client)).thenReturn(client);

        // Act
        Client result = clientService.updateWithClientStatus(client, clientStatus);

        // Assert
//        verify(clientStatus, Mockito.times(1)).setClient(client);
//        verify(client).setClientStatus(clientStatus);
//        verify(clientRepository, Mockito.times(1)).save(client);
        assertEquals(client, result);
    }
@Test
    public  void updateClientStatusTest(){
        Client client = new Client(1L, "13333", 20, "9990001122", "fff");
        ClientStatus clientStatus=new ClientStatus(1L,"Зареган",1,1);
        client.setClientStatus(clientStatus);
        when(clientRepository.save(client)).thenReturn(client);
        assertEquals(client, clientService.updateWithClientStatus(client,clientStatus));

    }
@Test
    public void createWithReport(){
        Client client = new Client(1L, "13333", 20, "9990001122", "fff");
        when(clientRepository.save(client)).thenReturn(client);
        assertEquals(client, clientService.createWithReport(client));
    }

}
