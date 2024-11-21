package com.veron.controller.api;

import com.veron.dto.UserDto;
import com.veron.entity.Users;
import com.veron.services.interfaces.UsersInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users/")
@RequiredArgsConstructor
public class UsersApi {
    private final UsersInterfaces usersInterfaces;
 @GetMapping("get-all")
    public List<Users> getAllUsers(){
     return usersInterfaces.getAllUsers();
 }

 @PostMapping("create")
    public Map<String,String> createUser(@RequestBody UserDto userDto){
     return usersInterfaces.createUser(userDto);
 }

 @GetMapping("get-by-username/{username}")
    public Users getByUserName(@PathVariable String username){
     return usersInterfaces.getByUserName(username);
 }

    @GetMapping("get-by-cash/{cash}")
    public Users getBycash(@PathVariable String cash){
        return usersInterfaces.getBycash(cash);
    }


    @PutMapping("update-by-username")
    public Map<String,String> updateByUsername(@RequestParam String username,@RequestParam String fullName,@RequestParam String phoneNumber,@RequestParam String role,@RequestParam boolean isEnabled){
     return usersInterfaces.updateByUsername(username,fullName,phoneNumber,role,isEnabled);
 }

    @PutMapping("update-password")
    public Map<String,String> updatePassword(@RequestParam String username,@RequestParam String password,@RequestParam String repassword){
        return usersInterfaces.updatePassword(username,password,repassword);
    }

    @PutMapping("reinitialize-user")
    public Map<String,String> reinitialzePassword(@RequestParam String username){
     return usersInterfaces.reinitialzePassword(username);
    }

}
