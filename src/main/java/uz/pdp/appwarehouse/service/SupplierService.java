package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Supplier;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.SupplierRepository;

import java.util.Optional;

@Service
public class SupplierService {
    @Autowired
    SupplierRepository supplierRepository;

    public Result addSupplier(Supplier supplier){
        boolean name = supplierRepository.existsByNameAndPhoneNumber(supplier.getName(), supplier.getPhoneNumber());
        if (name){
            return new Result("This Supplier is already exist !",false);
        }
        supplierRepository.save(supplier);
        return new Result("Successfully added !",true);
    }

    public Page<Supplier> getSuppliers(int page){
        Pageable pageable = PageRequest.of(page,10);
        return supplierRepository.findAll(pageable);
    }

    public Supplier getSupplier(Integer id){
        Optional<Supplier> optional = supplierRepository.findById(id);
        if (!optional.isPresent()) {
            return new Supplier();
        }
        return optional.get();
    }

    public Result editSupplier(Integer id,Supplier supplier){
        Optional<Supplier> byId = supplierRepository.findById(id);
        if (!byId.isPresent()){
            return new Result("Supplier id is not found !",false);
        }

        Supplier editing = byId.get();
        editing.setPhoneNumber(supplier.getPhoneNumber());
        editing.setName(supplier.getName());
        editing.setActive(supplier.isActive());
        supplierRepository.save(editing);
        return new Result("Successfully edited !",true);
    }

    public Result deleteSupplier(Integer id){
        Optional<Supplier> optional = supplierRepository.findById(id);
        if (!optional.isPresent()){
            return new Result("Supplier id is not found !",false);
        }
        supplierRepository.delete(optional.get());
        return new Result("Successfully deleted !",true);
    }
}
