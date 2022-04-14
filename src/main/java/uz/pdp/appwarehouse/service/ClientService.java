package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Client;
import uz.pdp.appwarehouse.entity.Supplier;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.ClientRepository;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public Result addClient(Client client){
        boolean name = clientRepository.existsByNameAndPhoneNumber(client.getName(), client.getPhoneNumber());
        if (name){
            return new Result("This Supplier is already exist !",false);
        }
        clientRepository.save(client);
        return new Result("Successfully added !",true);
    }

    public Page<Client> getClients(int page){
        Pageable pageable = PageRequest.of(page,10);
        return clientRepository.findAll(pageable);
    }

    public Client getClient(Integer id){
        Optional<Client> optional = clientRepository.findById(id);
        if (!optional.isPresent()) {
            return new Client();
        }
        return optional.get();
    }

    public Result editClient(Integer id,Client client){
        Optional<Client> byId = clientRepository.findById(id);
        if (!byId.isPresent()){
            return new Result("Client id is not found !",false);
        }

        Client editing = byId.get();
        editing.setPhoneNumber(client.getPhoneNumber());
        editing.setName(client.getName());
        editing.setActive(client.isActive());
        clientRepository.save(editing);
        return new Result("Successfully edited !",true);
    }

    public Result deleteClient(Integer id){
        Optional<Client> optional = clientRepository.findById(id);
        if (!optional.isPresent()){
            return new Result("Client id is not found !",false);
        }
        clientRepository.delete(optional.get());
        return new Result("Successfully deleted !",true);
    }
}
