package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import uz.pdp.appwarehouse.entity.Attachment;
import uz.pdp.appwarehouse.entity.Category;
import uz.pdp.appwarehouse.entity.Measurement;
import uz.pdp.appwarehouse.entity.Product;
import uz.pdp.appwarehouse.payload.ProductDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.AttachmentRepository;
import uz.pdp.appwarehouse.repository.CategoryRepository;
import uz.pdp.appwarehouse.repository.MeasurementRepository;
import uz.pdp.appwarehouse.repository.ProductRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    MeasurementRepository measurementRepository;
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductRepository productRepository;

    public Result addProduct(ProductDto productDto){
        boolean byCategoryAndName = productRepository.existsByCategoryAndName(productDto.getName(), productDto.getCategoryId());
        if (byCategoryAndName){
            return new Result("This item is already exist in this category",false);
        }
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if (!optionalCategory.isPresent()){
            return new Result("Category id is not found",false);
        }
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDto.getPhotoId());
        if (!optionalAttachment.isPresent()){
            return new Result("Photo id is not found",false);
        }
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(productDto.getMeasurementId());
        if (!optionalMeasurement.isPresent()){
            return new Result("Measurement id is not found",false);
        }
        Product product = new Product();
        product.setName(productDto.getName());
        product.setCode(UUID.randomUUID().toString());
        product.setCategory(optionalCategory.get());
        product.setMeasurement(optionalMeasurement.get());
        product.setPhoto(optionalAttachment.get());

        productRepository.save(product);
        return new Result("Successfully added",true);



    }

    public Page<Product> getProducts(@RequestParam int page){
        Pageable pageable = PageRequest.of(page,10);
        return productRepository.findAll(pageable);
    }

    public Product getProduct(Integer id){
        Optional<Product> optional = productRepository.findById(id);
        if (!optional.isPresent()){
            return new Product();
        }
        Product product = optional.get();
        return product;
    }

    public Result editProduct(Integer id,ProductDto productDto){
        Optional<Product> optional = productRepository.findById(id);
        if (!optional.isPresent()){
            return new Result("Product id is not found !",false);
        }
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(productDto.getMeasurementId());
        if (!optionalMeasurement.isPresent()){
            return new Result("Measurement id is not found",false);
        }
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if (!optionalCategory.isPresent()){
            return new Result("Category id is not found !",false);
        }
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDto.getPhotoId());
        if (!optionalAttachment.isPresent()) {
            return new Result("Photo id is not found !",false);
        }
        Product product = optional.get();
        product.setName(productDto.getName());
        product.setActive(productDto.isActive());
        product.setCategory(optionalCategory.get());
        product.setMeasurement(optionalMeasurement.get());
        product.setPhoto(optionalAttachment.get());
        productRepository.save(product);
        return new Result("Successfully edited",true);
    }

    public Result deleteProduct(Integer id){
        Optional<Product> optional = productRepository.findById(id);
        if (!optional.isPresent()){
            return new Result("Product id is not found !",false);
        }
        productRepository.delete(optional.get());
        return new Result("Successfully deleted !",true);
    }
}
