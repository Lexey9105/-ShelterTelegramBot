//package pro.sky.ShelterTelegramBot.service;
//
//import org.junit.Assert;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import pro.sky.ShelterTelegramBot.model.Client;
//import pro.sky.ShelterTelegramBot.model.ClientStatus;
//import pro.sky.ShelterTelegramBot.repository.ClientStatusRepository;
//import pro.sky.ShelterTelegramBot.service.impl.ClientStatusServiceImpl;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class ClientStatusServiceImplTest {
//
//
//    @Mock
//    private ClientStatusRepository clientStatusRepository;
//
//    @InjectMocks
//    private ClientStatusServiceImpl clientStatusService;
//
//
//    @Test
//    public void addTest() throws IOException {
//        ClientStatus clientStatus = new ClientStatus("13333", 1,2);
//        when(clientStatusRepository.save(clientStatus)).thenReturn(clientStatus);
//        assertNotNull(clientStatusService.create(clientStatus));
//        assertEquals(clientStatus, clientStatusService.create(clientStatus));
//    }
//
//    @Test
//    public void delTest() throws IOException {
//        ClientStatus clientStatus = new ClientStatus("13333", 1,2);
//
//
//        when(clientStatusRepository.findById(1L)).thenReturn(Optional.of(clientStatus));
//
//
//        clientStatusService.delete(1L);
//
//
//        verify(clientStatusRepository, Mockito.times(1)).deleteById(1L);
//    }
//
//
//    @Test
//    public void getTest() throws IOException {
//        ClientStatus clientStatus = new ClientStatus("13333", 1,2);
//
//
//        when(clientStatusRepository.findById(1L)).thenReturn(Optional.of(clientStatus));
//        assertNotNull(clientStatusService.get(1L));
//        assertEquals(clientStatus, clientStatusService.get(1L));
//    }
//
//
//    @Test
//    void getAll() {
//        ClientStatus clientStatus = new ClientStatus("13333", 1,2);
//        ClientStatus clientStatus2 = new ClientStatus("13333", 1,2);
//
//
//        List<ClientStatus> clientsStatus = Arrays.asList(clientStatus, clientStatus2);
//
//        Collection<ClientStatus> result = Arrays.asList(clientStatus, clientStatus2);
//
//        Mockito.when(clientStatusRepository.findAll()).thenReturn(clientsStatus);
//
//
//        Assert.assertEquals(clientsStatus, clientStatusRepository.findAll());
//    }
//}
