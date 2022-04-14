package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Category;
import uz.pdp.appwarehouse.payload.CategoryDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.CategoryRepository;

import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public Result addCategory(CategoryDto categoryDto){
        Category category = new Category();
        category.setName(categoryDto.getName());
        if (categoryDto.getParentCategoryId()!= null){
            Optional<Category> optional = categoryRepository.findById(categoryDto.getParentCategoryId());
            if (!optional.isPresent()){
                return new Result("Category id is not found in our system",false);
            }
            category.setCategory(optional.get());
        }
        categoryRepository.save(category);
        return new Result("Successfully added",true);
    }

    public Page<Category> getCategories(int page){
        Pageable pageable = PageRequest.of(page,10);
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categories;
    }

    public Category getCategory(int id){
        Optional<Category> optional = categoryRepository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }else return new Category();
    }

    public Result editCategory(int id,CategoryDto categoryDto){
        Optional<Category> optional = categoryRepository.findById(id);
        if (!optional.isPresent()){
            return new Result("Id is not found in our system",false);
        }
        Category category = optional.get();
        category.setName(categoryDto.getName());
        category.setActive(categoryDto.isActive());
        if (categoryDto.getParentCategoryId()!=null){
            Optional<Category> optionalCategory = categoryRepository.findById(categoryDto.getParentCategoryId());
            if (!optionalCategory.isPresent()){
                return new Result("Parent category is not found",false);
            }
            category.setCategory(optionalCategory.get());
        }
        categoryRepository.save(category);
        return new Result("Successfully Edited",true);
    }

    public Result deleteCategory(int id){
        Optional<Category> optional = categoryRepository.findById(id);
        if (optional.isPresent()){
            Category category = optional.get();
            categoryRepository.delete(category);
            return new Result("Successfully deleted",true);
        }
        return new Result("Id is not found in our system",false);
    }
}
