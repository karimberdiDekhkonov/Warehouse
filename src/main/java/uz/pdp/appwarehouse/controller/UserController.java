package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.User;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public Page<User> getUsers(@RequestParam int page){
        return userService.getUsers(page);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Integer id){
        return userService.getUser(id);
    }

    @PostMapping
    public Result addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @PutMapping
    public Result editUser(@PathVariable Integer id,@RequestBody User user){
        return userService.editUser(id,user);
    }

    @DeleteMapping
    public Result deleteUser(@PathVariable Integer id){
        return userService.deleteUser(id);
    }
}
