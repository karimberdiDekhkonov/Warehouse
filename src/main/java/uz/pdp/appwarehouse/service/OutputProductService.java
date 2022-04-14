package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Output;
import uz.pdp.appwarehouse.entity.OutputProduct;
import uz.pdp.appwarehouse.entity.Product;
import uz.pdp.appwarehouse.payload.OutputProductDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.OutputProductRepository;
import uz.pdp.appwarehouse.repository.OutputRepository;
import uz.pdp.appwarehouse.repository.ProductRepository;

import java.util.Optional;

@Service
public class OutputProductService {

    @Autowired
    OutputRepository outputRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OutputProductRepository outputProductRepository;

    public Page<OutputProduct> getOuProducts(int page){
        Pageable pageable = PageRequest.of(page,10);
        return outputProductRepository.findAll(pageable);
    }

    public OutputProduct getOuProduct(Integer id){
        Optional<OutputProduct> optional = outputProductRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }else return new OutputProduct();
    }

    public Result addOuProduct(OutputProductDto outputProductDto){
        Optional<Output> optionalOutput = outputRepository.findById(outputProductDto.getOutputId());
        if (!optionalOutput.isPresent()){
            return new Result("Output id is not found",false);
        }
        Optional<Product> optionalProduct = productRepository.findById(outputProductDto.getProductId());
        if (!optionalProduct.isPresent()){
            return new Result("Product id is not found",false);
        }

        OutputProduct outputProduct = new OutputProduct();
        outputProduct.setOutput(optionalOutput.get());
        outputProduct.setProduct(optionalProduct.get());
        outputProduct.setAmount(outputProductDto.getAmount());
        outputProduct.setPrice(outputProductDto.getPrice());
        outputProductRepository.save(outputProduct);
        return new Result("Successfully added",true);
    }

    public Result editOuProduct(Integer id,OutputProductDto outputProductDto){
        Optional<OutputProduct> optionalOutputProduct = outputProductRepository.findById(id);
        if (!optionalOutputProduct.isPresent()){
            return new Result("OutputProduct id is not found",false);
        }
        Optional<Output> optionalOutput = outputRepository.findById(outputProductDto.getOutputId());
        if (!optionalOutput.isPresent()){
            return new Result("Output id is not found",false);
        }
        Optional<Product> optionalProduct = productRepository.findById(outputProductDto.getProductId());
        if (!optionalProduct.isPresent()){
            return new Result("Product id is not found",false);
        }

        OutputProduct outputProduct = optionalOutputProduct.get();
        outputProduct.setOutput(optionalOutput.get());
        outputProduct.setProduct(optionalProduct.get());
        outputProduct.setAmount(outputProductDto.getAmount());
        outputProduct.setPrice(outputProductDto.getPrice());
        outputProductRepository.save(outputProduct);
        return new Result("Successfully edited",true);
    }

    public Result deleteOuProduct(Integer id){
        Optional<OutputProduct> optional = outputProductRepository.findById(id);
        if (optional.isPresent()){
            outputProductRepository.delete(optional.get());
            return new Result("Successfully deleted",true);
        }else return new Result("Id is not found",false);
    }
}
