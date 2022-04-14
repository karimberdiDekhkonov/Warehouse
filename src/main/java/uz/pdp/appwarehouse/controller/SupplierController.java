package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.Supplier;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.SupplierService;

@RestController
@RequestMapping("/supplier")
public class SupplierController {
    @Autowired
    SupplierService supplierService;

    @PostMapping
    public Result addSupplier(@RequestBody Supplier supplier){
        Result result = supplierService.addSupplier(supplier);
        return result;
    }

    @GetMapping
    public Page<Supplier> getSuppliers(int page){
        Page<Supplier> suppliers = supplierService.getSuppliers(page);
        return suppliers;
    }

    @GetMapping("/{id}")
    public Supplier getSupplier(@PathVariable Integer id){
        Supplier supplier = supplierService.getSupplier(id);
        return supplier;
    }

    @PutMapping
    public Result editSupplier(@PathVariable Integer id,@RequestBody Supplier supplier){
        Result result = supplierService.editSupplier(id, supplier);
        return result;
    }

    @DeleteMapping
    public Result deleteSupplier(@PathVariable Integer id){
        Result result = supplierService.deleteSupplier(id);
        return result;
    }
}
