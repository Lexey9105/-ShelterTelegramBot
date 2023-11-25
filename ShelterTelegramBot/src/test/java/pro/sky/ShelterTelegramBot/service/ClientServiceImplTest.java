package pro.sky.ShelterTelegramBot.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import pro.sky.ShelterTelegramBot.model.Client;
import pro.sky.ShelterTelegramBot.repository.ClientRepository;
import pro.sky.ShelterTelegramBot.service.impl.ClientServiceImpl;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;



    @Test
    public void addTest() throws IOException {
        Client client=new Client("13333",20,9990001122L,"fff");
        when(clientRepository.save(client)).thenReturn(client);
        assertNotNull(clientService.create(client));
        assertEquals(client, clientService.create(client));
    }

    @Test
    public void delTest() throws IOException {
        Client client=new Client(1L,"13333",20,9990001122L,"fff");


       when(clientRepository.findById(1L)).thenReturn(Optional.of(client));


        clientService.delete(1L);


        Mockito.verify(clientRepository, Mockito.times(1)).deleteById(1L);
    }


    @Test
    public void getTest() throws IOException {
        Client client=new Client(1L,"13333",20,9990001122L,"fff");


        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        assertNotNull(clientService.get(1L));
        assertEquals(client, clientService.get(1L));
    }


    @Test
    void getAll() {
        Client client=new Client(1L,"13333",20,9990001122L,"fff");
        Client client2=new Client(2L,"1333333",24,999000122L,"fff");



        List<Client> clients = Arrays.asList(client, client2);

      Collection<Client> result = Arrays.asList(client, client2);

        Mockito.when(clientRepository.findAll()).thenReturn(clients);


        Assert.assertEquals(clients, clientRepository.findAll());
    }


}
