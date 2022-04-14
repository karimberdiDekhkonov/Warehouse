package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.Output;
import uz.pdp.appwarehouse.payload.OutputDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.OutputService;

@RestController
@RequestMapping("/output")
public class OutputController {
    @Autowired
    OutputService outputService;

    @GetMapping
    public Page<Output> getOutputs(int page){
        return outputService.getOutputs(page);
    }

    @GetMapping("/{id}")
    public Output getOutput(@PathVariable Integer id){
        return outputService.getOutput(id);
    }

    @PostMapping
    public Result addOutput(@RequestBody OutputDto outputDto){
        return outputService.addOutput(outputDto);
    }

    @PutMapping
    public Result editOutput(@PathVariable Integer id,@RequestBody OutputDto outputDto){
        return outputService.editOutput(id,outputDto);
    }

    @DeleteMapping
    public Result deleteOutput(@PathVariable Integer id){
        return outputService.deleteOutput(id);
    }
}
