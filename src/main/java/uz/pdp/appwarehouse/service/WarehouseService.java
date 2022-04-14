package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Warehouse;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.WarehouseRepository;

import java.util.Optional;

@Service
public class WarehouseService {
    @Autowired
    WarehouseRepository warehouseRepository;

    public Result addWarehouse(Warehouse warehouse){
        Optional<Warehouse> optional = warehouseRepository.findById(warehouse.getId());
        if (!optional.isPresent()){
            return new Result("Warehouse name is already exist !",false);
        }
        warehouseRepository.save(warehouse);
        return new Result("Successfully added !",true);
    }

    public Page<Warehouse> getWarehouses(int page){
        Pageable pageable = PageRequest.of(page,10);
        return warehouseRepository.findAll(pageable);
    }

    public Warehouse getWarehouse(Integer id){
        Optional<Warehouse> optional = warehouseRepository.findById(id);
        if (!optional.isPresent()){
            return new Warehouse();
        }
        return optional.get();
    }

    public Result editWarehouse(Integer id,Warehouse warehouse){
        Optional<Warehouse> optional = warehouseRepository.findById(id);
        if (!optional.isPresent()){
            return new Result("Warehouse id is not found !",false);
        }
        Warehouse editing = optional.get();
        editing.setActive(warehouse.isActive());
        editing.setName(warehouse.getName());
        warehouseRepository.save(editing);
        return new Result("Successfully edited !",true);
    }

    public Result deleteWarehouse(Integer id){
        Optional<Warehouse> optional = warehouseRepository.findById(id);
        if (!optional.isPresent()){
            return new Result("Warehouse id is not found !",false);
        }
        warehouseRepository.delete(optional.get());
        return new Result("Successfully deleted !",true);
    }
}
