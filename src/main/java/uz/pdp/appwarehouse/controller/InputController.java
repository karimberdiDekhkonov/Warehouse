package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.Input;
import uz.pdp.appwarehouse.payload.InputDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.InputService;

@RestController
@RequestMapping("/input")
public class InputController {
    @Autowired
    InputService inputService;

    @GetMapping
    public Page<Input> getInputs(int page){
        return inputService.getInputs(page);
    }

    @GetMapping("/{id}")
    public Input getInput(@PathVariable Integer id){
        return inputService.getInput(id);
    }

    @PostMapping
    public Result addInput(@RequestBody InputDto inputDto){
        return inputService.addInput(inputDto);
    }

    @PutMapping
    public Result editInput(@PathVariable Integer id,@RequestBody InputDto inputDto){
        return inputService.editInput(id,inputDto);
    }

    @DeleteMapping
    public Result deleteInput(@PathVariable Integer id){
        return inputService.deleteInput(id);
    }
}
