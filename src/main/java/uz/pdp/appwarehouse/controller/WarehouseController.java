package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.Warehouse;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.WarehouseService;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {
    @Autowired
    WarehouseService warehouseService;

    @GetMapping
    public Page<Warehouse> getWarehouses(int page){
        Page<Warehouse> warehouses = warehouseService.getWarehouses(page);
        return warehouses;
    }

    @GetMapping("/{id}")
    public Warehouse getWarehouse(@PathVariable Integer id){
        Warehouse warehouse = warehouseService.getWarehouse(id);
        return warehouse;
    }

    @PostMapping
    public Result addWarehouse(@RequestBody Warehouse warehouse){
        Result result = warehouseService.addWarehouse(warehouse);
        return result;
    }

    @PutMapping
    public Result editWarehouse(@PathVariable Integer id,@RequestBody Warehouse warehouse){
        Result result = warehouseService.editWarehouse(id, warehouse);
        return result;
    }

    @DeleteMapping
    public Result deleteWarehouse(@PathVariable Integer id){
        Result result = warehouseService.deleteWarehouse(id);
        return result;
    }
}
