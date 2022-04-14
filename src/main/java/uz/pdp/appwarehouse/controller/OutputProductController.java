package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.Output;
import uz.pdp.appwarehouse.entity.OutputProduct;
import uz.pdp.appwarehouse.payload.OutputProductDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.OutputProductService;

@RestController
@RequestMapping("/outputProduct")
public class OutputProductController {

    @Autowired
    OutputProductService outputProductService;

    @GetMapping
    public Page<OutputProduct> getOuProducts(int page){
        return outputProductService.getOuProducts(page);
    }

    @GetMapping("/{id}")
    public OutputProduct getOuProduct(@PathVariable Integer id){
        return outputProductService.getOuProduct(id);
    }

    @PostMapping
    public Result addOuProduct(@RequestBody OutputProductDto outputProductDto){
        return outputProductService.addOuProduct(outputProductDto);
    }

    @PutMapping
    public Result editOuProduct(@PathVariable Integer id, @RequestBody OutputProductDto outputProductDto){
        return outputProductService.editOuProduct(id,outputProductDto);
    }

    @DeleteMapping
    public Result deleteOuProduct(@PathVariable Integer id){
        return outputProductService.deleteOuProduct(id);
    }
}
