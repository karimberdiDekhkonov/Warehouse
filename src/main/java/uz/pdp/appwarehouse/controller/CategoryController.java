package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.Category;
import uz.pdp.appwarehouse.payload.CategoryDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping
    public Result addCategory(@PathVariable CategoryDto categoryDto){
        Result result = categoryService.addCategory(categoryDto);
        return result;
    }

    @GetMapping
    public Page<Category> getCategories(int page){
        Page<Category> categories = categoryService.getCategories(page);
        return categories;
    }

    @GetMapping("/{id}")
    public Category getCategory(@PathVariable Integer id){
        Category category = categoryService.getCategory(id);
        return category;
    }

    @PutMapping
    public Result editCategory(@PathVariable Integer id,@RequestBody CategoryDto categoryDto){
        Result result = categoryService.editCategory(id, categoryDto);
        return result;
    }

    @DeleteMapping
    public Result deleteCategory(@PathVariable Integer id){
        Result result = categoryService.deleteCategory(id);
        return result;
    }
}
