package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.Measurement;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.MeasurementService;

@RestController
@RequestMapping("/measurement")
public class MeasurementController {

    @Autowired
    MeasurementService measurementService;

    @PostMapping
    public Result addMeasurement(@RequestBody Measurement measurement){
        Result result = measurementService.addMeasurementService(measurement);
        return result;
    }

    @GetMapping
    public Page<Measurement> getMeasurementPages(@RequestParam int page){
        Page<Measurement> pages = measurementService.getPages(page);
        return pages;
    }

    @GetMapping("/{id}")
    public Measurement getMeasurement(@PathVariable Integer id){
        Measurement measurement = measurementService.getMeasurement(id);
        return measurement;
    }

    @PutMapping
    public Result editMeasurement(@PathVariable Integer id,@RequestBody Measurement measurement){
        Result result = measurementService.editMeasurement(id, measurement);
        return result;
    }

    @DeleteMapping
    public Result deleteMeasurement(@PathVariable Integer id){
        Result result = measurementService.deleteMeasurement(id);
        return result;
    }
}
