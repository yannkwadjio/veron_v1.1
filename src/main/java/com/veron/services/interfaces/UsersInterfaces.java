package com.veron.services.interfaces;

import com.veron.dto.UserDto;
import com.veron.entity.Users;

import java.util.List;
import java.util.Map;

public interface UsersInterfaces {

    List<Users> getAllUsers();

    Map<String, String> createUser(UserDto userDto);

    Users getByUserName(String userName);

    Map<String, String> updateByUsername(String username,String fullName,String phoneNumber,String role,boolean isEnabled);

    Map<String, String> updatePassword(String username, String password, String repassword);

    Users getBycash(String cash);

    Map<String, String> reinitialzePassword(String username);
}
