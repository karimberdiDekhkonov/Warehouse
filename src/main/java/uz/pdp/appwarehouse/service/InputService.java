package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Currency;
import uz.pdp.appwarehouse.entity.Input;
import uz.pdp.appwarehouse.entity.Supplier;
import uz.pdp.appwarehouse.entity.Warehouse;
import uz.pdp.appwarehouse.payload.InputDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.CurrencyRepository;
import uz.pdp.appwarehouse.repository.InputRepository;
import uz.pdp.appwarehouse.repository.SupplierRepository;
import uz.pdp.appwarehouse.repository.WarehouseRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class InputService {

    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    SupplierRepository supplierRepository;
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    InputRepository inputRepository;

    public Page<Input> getInputs(int page){
        Pageable pageable = PageRequest.of(page,10);
        return inputRepository.findAll(pageable);
    }

    public Input getInput(Integer id){
        Optional<Input> optional = inputRepository.findById(id);
        if (!optional.isPresent()){
            return new Input();
        }
        return optional.get();
    }

    public Result addInput(InputDto inputDto){
        Optional<Currency> optionalCurrency = currencyRepository.findById(inputDto.getCurrencyId());
        if (!optionalCurrency.isPresent()){
            return new Result("Currency id is not found",false);
        }
        Optional<Supplier> optionalSupplier = supplierRepository.findById(inputDto.getSupplierId());
        if (!optionalSupplier.isPresent()){
            return new Result("Supplier id is not found",false);
        }
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(inputDto.getWarehouseId());
        if (!optionalWarehouse.isPresent()){
            return new Result("Warehouse id is not found",false);
        }
        Input input = new Input();
        input.setDate(inputDto.getDate());
        input.setCode(UUID.randomUUID().toString());
        input.setCurrency(optionalCurrency.get());
        input.setFactureNumber(inputDto.getFactureNumber());
        input.setSupplier(optionalSupplier.get());
        input.setWarehouse(optionalWarehouse.get());
        inputRepository.save(input);
        return new Result("Successfully saved !",true);
    }

    public Result editInput(Integer id,InputDto inputDto){
        Optional<Input> optionalInput = inputRepository.findById(id);
        if (!optionalInput.isPresent()){
            return new Result("Input id is not found",false);
        }
        Optional<Currency> optionalCurrency = currencyRepository.findById(inputDto.getCurrencyId());
        if (!optionalCurrency.isPresent()){
            return new Result("Currency id is not found",false);
        }
        Optional<Supplier> optionalSupplier = supplierRepository.findById(inputDto.getSupplierId());
        if (!optionalSupplier.isPresent()){
            return new Result("Supplier id is not found",false);
        }
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(inputDto.getWarehouseId());
        if (!optionalWarehouse.isPresent()){
            return new Result("Warehouse id is not found",false);
        }
        Input input = optionalInput.get();
        input.setDate(inputDto.getDate());
        input.setCode(UUID.randomUUID().toString());
        input.setCurrency(optionalCurrency.get());
        input.setFactureNumber(inputDto.getFactureNumber());
        input.setSupplier(optionalSupplier.get());
        input.setWarehouse(optionalWarehouse.get());
        inputRepository.save(input);
        return new Result("Successfully saved !",true);
    }

    public Result deleteInput(Integer id){
        Optional<Input> optional = inputRepository.findById(id);
        if (optional.isPresent()){
            inputRepository.delete(optional.get());
            return new Result("Successfully deleted !",true);
        }else return new Result("id is not found !",false);
    }


}
