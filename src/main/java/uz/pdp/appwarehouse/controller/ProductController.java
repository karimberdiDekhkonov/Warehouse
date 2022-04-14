package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.Product;
import uz.pdp.appwarehouse.payload.ProductDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping
    public Result addProduct(@RequestBody ProductDto productDto){
        Result result = productService.addProduct(productDto);
        return result;
    }

    @GetMapping
    public Page<Product> getProducts(@RequestParam int page){
        Page<Product> products = productService.getProducts(page);
        return products;
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Integer id){
        Product product = productService.getProduct(id);
        return product;
    }

    @PutMapping
    public Result editProduct(@PathVariable Integer id,ProductDto productDto){
        Result result = productService.editProduct(id, productDto);
        return result;
    }

    @DeleteMapping
    public Result deleteProduct(@PathVariable Integer id){
        Result result = productService.deleteProduct(id);
        return result;
    }
}
