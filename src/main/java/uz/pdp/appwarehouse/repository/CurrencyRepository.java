package uz.pdp.appwarehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appwarehouse.entity.Currency;

public interface CurrencyRepository extends JpaRepository<Currency,Integer> {

    boolean findByName(String name);

}
