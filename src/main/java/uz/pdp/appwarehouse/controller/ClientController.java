package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.Client;
import uz.pdp.appwarehouse.entity.Supplier;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.ClientService;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    ClientService clientService;


    @PostMapping
    public Result addClient(@RequestBody Client client){
        Result result = clientService.addClient(client);
        return result;
    }

    @GetMapping
    public Page<Client> getClient(int page){
        return clientService.getClients(page);
    }

    @GetMapping("/{id}")
    public Client getClient(@PathVariable Integer id){
       return clientService.getClient(id);
    }

    @PutMapping
    public Result editClient(@PathVariable Integer id,@RequestBody Client client){
        return clientService.editClient(id,client);
    }

    @DeleteMapping
    public Result deleteClient(@PathVariable Integer id){
        return clientService.deleteClient(id);
    }
}
