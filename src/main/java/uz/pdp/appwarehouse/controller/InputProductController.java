package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.InputProduct;
import uz.pdp.appwarehouse.payload.InputProductDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.InputProductService;

@RestController
@RequestMapping("/inputProduct")
public class InputProductController {
    @Autowired
    InputProductService inputProductService;

    @GetMapping
    public Page<InputProduct> getInProducts(int page){
        return inputProductService.getInProducts(page);
    }

    @GetMapping("/{id}")
    public InputProduct getInProduct(@PathVariable Integer id){
        return inputProductService.getInProduct(id);
    }

    @PostMapping
    public Result addInProduct(@RequestBody InputProductDto inputProductDto){
        return inputProductService.addInProduct(inputProductDto);
    }

    @PutMapping
    public Result editInProduct(@PathVariable Integer id,@RequestBody InputProductDto inputProductDto){
        return inputProductService.editInProduct(id,inputProductDto);
    }

    @DeleteMapping
    public Result deleteInProduct(@PathVariable Integer id){
        return inputProductService.deleteInProduct(id);
    }
}
