package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.User;
import uz.pdp.appwarehouse.entity.Warehouse;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.UserRepository;
import uz.pdp.appwarehouse.repository.WarehouseRepository;

import java.util.*;

@Service
public class UserService {
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    UserRepository userRepository;

    public Page<User> getUsers(int page){
        Pageable pageable = PageRequest.of(page,10);
        return userRepository.findAll(pageable);
    }

    public User getUser(Integer id){
        Optional<User> optional = userRepository.findById(id);
        return optional.orElseGet(User::new);
    }

    public Result addUser(User user){

        List<Warehouse> warehouses = new ArrayList<>();
        for (Warehouse warehouse : warehouses) {
            Optional<Warehouse> optional = warehouseRepository.findById(warehouse.getId());
            optional.ifPresent(warehouses::add);
        }

        boolean exists = userRepository.existsByLastNameAndFirstNameAndPhoneNumber(user.getLastName(), user.getFirstName(), user.getPhoneNumber());
        if (exists){
            return new Result("This user is already exist !",false);
        }
        if (warehouses.isEmpty()){
            return new Result("WareHouse id is not found !",false);
        }
        User adding = new User();
        adding.setActive(user.isActive());
        adding.setCode(UUID.randomUUID().toString());
        adding.setFirstName(user.getFirstName());
        adding.setLastName(user.getLastName());
        adding.setPhoneNumber(user.getPhoneNumber());
        adding.setWarehouses((Set<Warehouse>) warehouses);
        userRepository.save(adding);
        return new Result("Successfully added !",true);
    }

    public Result editUser(Integer id,User user){
        Optional<User> optional = userRepository.findById(id);
        if (!optional.isPresent()){
            return new Result("User id is not found !",false);
        }
        boolean exists = userRepository.existsByLastNameAndFirstNameAndPhoneNumber(user.getLastName(), user.getFirstName(), user.getPhoneNumber());
        if (exists){
            return new Result("This user is already exist !",false);
        }
        User editing = optional.get();
        editing.setActive(user.isActive());
        editing.setCode(UUID.randomUUID().toString());
        editing.setFirstName(user.getFirstName());
        editing.setLastName(user.getLastName());
        editing.setPhoneNumber(user.getPhoneNumber());
        userRepository.save(editing);
        return new Result("Successfully added !",true);
    }

    public Result deleteUser(Integer id){
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()){
            userRepository.delete(optional.get());
            return new Result("Successfully deleted !",true);
        }
        return new Result("User id is not found !",false);
    }


}
