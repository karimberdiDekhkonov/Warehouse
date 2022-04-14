package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.*;
import uz.pdp.appwarehouse.payload.InputDto;
import uz.pdp.appwarehouse.payload.OutputDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.ClientRepository;
import uz.pdp.appwarehouse.repository.CurrencyRepository;
import uz.pdp.appwarehouse.repository.OutputRepository;
import uz.pdp.appwarehouse.repository.WarehouseRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class OutputService {

    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    OutputRepository outputRepository;

    public Page<Output> getOutputs(int page){
        Pageable pageable = PageRequest.of(page,10);
        return outputRepository.findAll(pageable);
    }

    public Output getOutput(Integer id){
        Optional<Output> optional = outputRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }else return new Output();
    }

    public Result addOutput(OutputDto outputDto){
        Optional<Currency> optionalCurrency = currencyRepository.findById(outputDto.getCurrencyId());
        if (!optionalCurrency.isPresent()){
            return new Result("Currency id is not found",false);
        }
        Optional<Client> optionalClient = clientRepository.findById(outputDto.getClientId());
        if (!optionalClient.isPresent()){
            return new Result("Client id is not found",false);
        }
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(outputDto.getWarehouseId());
        if (!optionalWarehouse.isPresent()){
            return new Result("Warehouse id is not found",false);
        }
        Output output = new Output();
        output.setDate(outputDto.getDate());
        output.setCode(UUID.randomUUID().toString());
        output.setCurrency(optionalCurrency.get());
        output.setFactureNumber(outputDto.getFactureNumber());
        output.setClient(optionalClient.get());
        output.setWarehouse(optionalWarehouse.get());
        outputRepository.save(output);
        return new Result("Successfully saved !",true);
    }

    public Result editOutput(Integer id, OutputDto outputDto){
        Optional<Output> optionalOutput = outputRepository.findById(id);
        if (!optionalOutput.isPresent()){
            return new Result("Output id is not found",false);
        }
        Optional<Currency> optionalCurrency = currencyRepository.findById(outputDto.getCurrencyId());
        if (!optionalCurrency.isPresent()){
            return new Result("Currency id is not found",false);
        }
        Optional<Client> optionalClient = clientRepository.findById(outputDto.getClientId());
        if (!optionalClient.isPresent()){
            return new Result("Client id is not found",false);
        }
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(outputDto.getWarehouseId());
        if (!optionalWarehouse.isPresent()){
            return new Result("Warehouse id is not found",false);
        }

        Output output = optionalOutput.get();
        output.setDate(outputDto.getDate());
        output.setCode(UUID.randomUUID().toString());
        output.setCurrency(optionalCurrency.get());
        output.setFactureNumber(outputDto.getFactureNumber());
        output.setClient(optionalClient.get());
        output.setWarehouse(optionalWarehouse.get());
        outputRepository.save(output);
        return new Result("Successfully saved !",true);
    }

    public Result deleteOutput(Integer id){
        Optional<Output> optional = outputRepository.findById(id);
        if (optional.isPresent()){
            outputRepository.delete(optional.get());
            return new Result("Successfully deleted !",true);
        }else return new Result("id is not found !",false);
    }
}
