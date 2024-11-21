package com.veron.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String username;
    private String fullName;
    private String phoneNumber;
    private String password;
    private String confirmationPassword;
    private String role;
    private String enterprise;
    private String agency;
    private String country;

}
