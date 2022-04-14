package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Currency;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.CurrencyRepository;

import java.util.Optional;

@Service
public class CurrencyService {
    @Autowired
    CurrencyRepository currencyRepository;

    public Result addCurrency(Currency currency){
        boolean byName = currencyRepository.findByName(currency.getName());
        if (byName) {
            return new Result("Currency name is already exist !",false);
        }
        Currency adding = new Currency();
        adding.setActive(currency.isActive());
        adding.setName(currency.getName());
        currencyRepository.save(adding);
        return new Result("Successfully added !",true);
    }

    public Page<Currency> getCurrencies(int page){
        Pageable pageable = PageRequest.of(page,10);
        return currencyRepository.findAll(pageable);
    }

    public Currency getCurrency(Integer id){
        Optional<Currency> optional = currencyRepository.findById(id);
        if (!optional.isPresent()){
            return new Currency();
        }
        return optional.get();
    }

    public Result editCurrency(Integer id,Currency currency){
        Optional<Currency> optional = currencyRepository.findById(id);
        if (!optional.isPresent()){
            return new Result("Currency id is not found !",false);
        }

        Currency editing = optional.get();
        editing.setName(currency.getName());
        editing.setActive(currency.isActive());
        currencyRepository.save(editing);
        return new Result("Successfully edited !",true);
    }

    public Result deleteCurrency(Integer id){
        Optional<Currency> optional = currencyRepository.findById(id);
        if (!optional.isPresent()){
            return new Result("Currency id is not found !",false);
        }
        currencyRepository.delete(optional.get());
        return new Result("Successfully deleted !",true);
    }
}
