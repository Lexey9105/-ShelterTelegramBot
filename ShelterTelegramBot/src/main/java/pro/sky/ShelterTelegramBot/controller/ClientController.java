package pro.sky.ShelterTelegramBot.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.ShelterTelegramBot.model.Client;
import pro.sky.ShelterTelegramBot.service.ClientService;
import pro.sky.ShelterTelegramBot.service.impl.ClientServiceImpl;

import java.util.Collection;

@RestController
@RequestMapping("/client")
public class ClientController {

private final ClientService clientService;

public ClientController (ClientService clientService){
    this.clientService=clientService;
}
    @PostMapping()
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client client1 =clientService.create(client);
        if (client1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(client1);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Client> deleteClient(@PathVariable Long id) {
        Client client = clientService.get(id);
        if (client == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(clientService.delete(id));
    }



    @GetMapping("{id}")
    public ResponseEntity<Client> getClient(@PathVariable Long id) {
        Client client = clientService.get(id);
        if (client == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(clientService.get(id));
    }


    @GetMapping()
    public ResponseEntity<Collection<Client>> findAll() {
        return ResponseEntity.ok(clientService.findAll());
    }

    @GetMapping("/by_id")
    public ResponseEntity<Object> getClientsById(){
        return  ResponseEntity.ok(clientService.getClientsById());
    }
}
