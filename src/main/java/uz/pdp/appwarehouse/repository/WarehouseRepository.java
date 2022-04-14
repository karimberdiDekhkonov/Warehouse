package uz.pdp.appwarehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appwarehouse.entity.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse,Integer> {
    boolean existsByName(String name);
}
