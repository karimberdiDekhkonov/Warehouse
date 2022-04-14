package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.Currency;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.CurrencyService;

@RestController
@RequestMapping("/currency")
public class CurrencyController {
    @Autowired
    CurrencyService currencyService;

    @PostMapping
    public Result addCurrency(@RequestBody Currency currency){
        Result result = currencyService.addCurrency(currency);
        return result;
    }

    @GetMapping
    public Page<Currency> getCurrencies(int page){
        Page<Currency> currencies = currencyService.getCurrencies(page);
        return currencies;
    }

    @GetMapping("/{id}")
    public Currency getCurrency(@PathVariable Integer id){
        Currency currency = currencyService.getCurrency(id);
        return currency;
    }

    @PutMapping
    public Result editCurrency(@PathVariable Integer id,Currency currency){
        Result result = currencyService.editCurrency(id, currency);
        return result;
    }

    @DeleteMapping
    public Result deleteCurrency(@PathVariable Integer id){
        Result result = currencyService.deleteCurrency(id);
        return result;
    }
}
