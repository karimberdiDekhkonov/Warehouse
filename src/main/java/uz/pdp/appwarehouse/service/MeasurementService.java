package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import uz.pdp.appwarehouse.entity.Measurement;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.MeasurementRepository;

import java.util.Optional;

@Service
public class MeasurementService {
    @Autowired
    MeasurementRepository measurementRepository;

    public Result addMeasurementService(Measurement measurement){
        boolean existsByName = measurementRepository.existsByName(measurement.getName());
        if (existsByName){
            return new Result("Measure id is not found in our system",false);
        }
        measurementRepository.save(measurement);
        return new Result("Successfully added",true);
    }

    public Page<Measurement> getPages(@RequestParam int page){
        Pageable pageable = PageRequest.of(page,10);
        Page<Measurement> measurementPage = measurementRepository.findAll(pageable);
        return measurementPage;
    }

    public Measurement getMeasurement(@PathVariable Integer id){
        Optional<Measurement> optional = measurementRepository.findById(id);
        if (optional.isPresent()){
            Measurement measurement = optional.get();
            return measurement;
        }
        else return new Measurement();
    }

    public Result editMeasurement(@PathVariable Integer id, @RequestBody Measurement measurement){
        Optional<Measurement> optional = measurementRepository.findById(id);
        if (optional.isPresent()){
            Measurement editing = optional.get();
            editing.setName(measurement.getName());
            return new Result("Successfully edited",true);
        }else return new Result("Measurement id is not found",false);
    }

    public Result deleteMeasurement(@PathVariable Integer id){
        Optional<Measurement> optional = measurementRepository.findById(id);
        if (optional.isPresent()){
            measurementRepository.delete(optional.get());
            return new Result("Successfully Deleted",true);
        }else return new Result("Id is not found",false);
    }
}
