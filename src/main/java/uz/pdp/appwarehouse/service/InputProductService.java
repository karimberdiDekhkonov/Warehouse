package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Input;
import uz.pdp.appwarehouse.entity.InputProduct;
import uz.pdp.appwarehouse.entity.Product;
import uz.pdp.appwarehouse.payload.InputProductDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.InputProductRepository;
import uz.pdp.appwarehouse.repository.InputRepository;
import uz.pdp.appwarehouse.repository.ProductRepository;

import java.util.Optional;

@Service
public class InputProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    InputRepository inputRepository;
    @Autowired
    InputProductRepository inputProductRepository;


    public Page<InputProduct> getInProducts(int page){
        Pageable pageable = PageRequest.of(page,10);
        return inputProductRepository.findAll(pageable);
    }

    public InputProduct getInProduct(Integer id){
        Optional<InputProduct> optional = inputProductRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }else return new InputProduct();

    }

    public Result addInProduct(InputProductDto inputProductDto){
        Optional<Product> optionalProduct = productRepository.findById(inputProductDto.getProductId());
        if (!optionalProduct.isPresent()){
            return new Result("Product id is not found !",false);
        }
        Optional<Input> optionalInput = inputRepository.findById(inputProductDto.getInputId());
        if (!optionalInput.isPresent()){
            return new Result("Input id is not found !",false);
        }

        InputProduct product = new InputProduct();
        product.setProduct(optionalProduct.get());
        product.setInput(optionalInput.get());
        product.setAmount(inputProductDto.getAmount());
        product.setPrice(inputProductDto.getPrice());
        product.setExpireDate(inputProductDto.getExpireDate());
        inputProductRepository.save(product);
        return new Result("Successfully saved !",true);
    }

    public Result editInProduct(Integer id,InputProductDto inputProductDto){
        Optional<InputProduct> optional = inputProductRepository.findById(id);
        if (!optional.isPresent()){
            return new Result("InputProduct id is not found !",false);
        }
        Optional<Product> optionalProduct = productRepository.findById(inputProductDto.getProductId());
        if (!optionalProduct.isPresent()){
            return new Result("Product id is not found !",false);
        }
        Optional<Input> optionalInput = inputRepository.findById(inputProductDto.getInputId());
        if (!optionalInput.isPresent()){
            return new Result("Input id is not found !",false);
        }

        InputProduct product = optional.get();
        product.setProduct(optionalProduct.get());
        product.setInput(optionalInput.get());
        product.setAmount(inputProductDto.getAmount());
        product.setPrice(inputProductDto.getPrice());
        product.setExpireDate(inputProductDto.getExpireDate());
        inputProductRepository.save(product);
        return new Result("Successfully saved !",true);
    }

    public Result deleteInProduct(Integer id){
        Optional<InputProduct> optional = inputProductRepository.findById(id);
        if (optional.isPresent()){
            inputProductRepository.delete(optional.get());
            return new Result("Successfully deleted !",true);
        }
        else return new Result("InputProduct id is not found !",false);
    }

}
